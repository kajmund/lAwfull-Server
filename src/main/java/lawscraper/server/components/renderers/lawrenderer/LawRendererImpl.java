package lawscraper.server.components.renderers.lawrenderer;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.shared.DocumentPartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO Document this.
 */

@Component
public class LawRendererImpl implements LawRenderer {

    RepositoryBase<TextElement> textRepository;
    RepositoryBase<LawDocumentPart> lawPartRepository;

    @Autowired
    public LawRendererImpl(RepositoryBase<TextElement> textRepository,
                           RepositoryBase<LawDocumentPart> lawPartRepository
                          ) {
        this.textRepository = textRepository;
        this.lawPartRepository = lawPartRepository;

        this.lawPartRepository.setEntityClass(LawDocumentPart.class);
        this.textRepository.setEntityClass(TextElement.class);
    }

    public LawRendererImpl() {

    }

    @Override
    public String renderToHtml(Law law) {
        return element("div", renderMeta(law) + renderTableOfContents(law)
                + element("div", renderParts(law.getSortedParts()), "class", "lawBody"),
                       "class", "law");
    }

    private String renderTableOfContents(Law law) {
        return element("div", renderTableOfContentsLawPart(law.getSortedParts()),
                       "class", "lawTableOfContents"
                      );
    }

    private String renderTableOfContentsLawPart(List<LawDocumentPart> parts) {
        String html = "";

        for (LawDocumentPart part : parts) {
            String text = part.getTextElement().getText();

            if (part.getLawPartType() != DocumentPartType.PARAGRAPH &&
                    part.getLawPartType() != DocumentPartType.SECTION &&
                    part.getLawPartType() != DocumentPartType.SECTION_LIST_ITEM) {
                html += element("div    ", element("lawTocItem", text, "href", "#" + part.getId()),
                                "class",
                                "TOCElement_" + part.getType()) + renderTableOfContentsLawPart(
                        part.getSortedParts());
            }
        }
        return html;
    }


    private String renderParts(List<LawDocumentPart> children) {
        StringBuilder sb = new StringBuilder();
        for (LawDocumentPart child : children) {
            sb.append(renderPart(child));
        }
        return sb.toString();
    }

    private String renderPart(LawDocumentPart part) {
        String deprecatedString = "";
        /*
        if (part.isDeprecated()) {
            deprecatedString = "_deprecated";
        }
        */
        return renderPart(part, "part_" + part.getLawPartType().name().toLowerCase() + deprecatedString);
    }

    private String renderPart(LawDocumentPart part, String cssClass) {
        String idStr = "";
        String title = "";

        String text = part.getTextElement().getText();
        if (part.getId() != null) {
            idStr = Long.toString(part.getId());
        }

        if (DocumentPartType.valueOf(part.getType()) == DocumentPartType.HEADING ||
                DocumentPartType.valueOf(part.getType()) == DocumentPartType.CHAPTER) {
            title = text;
        }

        part = lawPartRepository.findOne(part.getId());
        return element("div", text + renderParts(part.getSortedParts()), "class", cssClass,
                       "id", idStr, "title", title);
    }

    private String renderMeta(Law law) {
        return element("div", element("div", getLawTitle(law), "class", "lawTitle") +
                getMetaPart("Departement", law.getCreator())
                + getMetaPart("Utf&auml;rdad", law.getReleaseDate())
                + getMetaPart("K&auml;lla", law.getPublisher())
                + getMetaPart("Senast h&auml;mtad", law.getLatestFetchFromGov()),
                       "class", "lawMeta"
                      );
    }

    private String getMetaPart(String title, String content) {
        return element("div",
                       element("div", title, "class", "metaTitle") +
                               element("div", content, "class", "metaText"),
                       "class", "metaHeading");
    }

    private String getLawTitle(Law law) {
        return law.getTitle() + " (" + law.getDocumentKey() + ")";
    }

    private String element(String elementName, String content, String... attrKeysAndValues) {
        StringBuilder attributes = new StringBuilder();
        if (attrKeysAndValues.length % 2 == 0) {
            for (int i = 0; i < attrKeysAndValues.length; i++) {
                String keyOrValue = attrKeysAndValues[i];
                if (i % 2 == 0) {
                    attributes.append(" ").append(keyOrValue).append("=\"");
                } else {
                    attributes.append(keyOrValue).append("\"");
                }
            }
        }
        return "<" + elementName + attributes.toString() + ">" + content + "</" + elementName + ">";
    }
}
