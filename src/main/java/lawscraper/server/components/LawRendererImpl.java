package lawscraper.server.components;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO Document this.
 */

@Component
public class LawRendererImpl implements LawRenderer {
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
            if (part.getLawPartType() != LawDocumentPartType.PARAGRAPH &&
                    part.getLawPartType() != LawDocumentPartType.SECTION &&
                    part.getLawPartType() != LawDocumentPartType.SECTION_LIST_ITEM) {
                html += element("div    ", element("a", part.getTextElement().getText(), "href", "#" + part.getId()),
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
        if (part.isDeprecated()) {
            deprecatedString = "_deprecated";
        }
        return renderPart(part, "part_" + part.getLawPartType().name().toLowerCase() + deprecatedString);
    }

    private String renderPart(LawDocumentPart part, String cssClass) {
        String idStr = "";
        String title = "";

        if (part.getId() != null) {
            idStr = Long.toString(part.getId());
        }

        if (LawDocumentPartType.valueOf(part.getType()) == LawDocumentPartType.HEADING ||
                LawDocumentPartType.valueOf(part.getType()) == LawDocumentPartType.CHAPTER) {
            title = part.getTextElement().getText();
        }

        return element("div", part.getTextElement().getText() + renderParts(part.getSortedParts()), "class", cssClass,
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
        return law.getTitle() + " (" + law.getFsNumber() + ")";
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
