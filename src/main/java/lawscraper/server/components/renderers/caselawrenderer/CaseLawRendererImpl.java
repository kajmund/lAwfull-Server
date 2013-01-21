package lawscraper.server.components.renderers.caselawrenderer;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.caselaw.CaseLawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.shared.DocumentPartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO Document this.
 */

@Component
public class CaseLawRendererImpl implements CaseLawRenderer {


    private RepositoryBase<CaseLawDocumentPart> caseLawPartRepository;

    @Autowired
    public CaseLawRendererImpl(RepositoryBase<CaseLawDocumentPart> caseLawPartRepository) {
        this.caseLawPartRepository = caseLawPartRepository;
        this.caseLawPartRepository.setEntityClass(CaseLawDocumentPart.class);
    }


    @Override
    public String renderToHtml(CaseLaw caseLaw) {
        return element("div", renderMeta(caseLaw)
                +  renderDescription(caseLaw) + element("div", renderParts(caseLaw.getSortedParts()), "class", "lawBody"),
                       "class", "law");
    }

    /*
    private String renderTableOfContents(Law law) {
        return element("div", renderTableOfContentsLawPart(law.getSortedParts()),
                       "class", "lawTableOfContents"
                      );
    }
    */

    private String renderTableOfContentsLawPart(List<LawDocumentPart> parts) {
        String html = "";

        for (LawDocumentPart part : parts) {
            String text = part.getTextElement().getText();

            if (part.getLawPartType() != DocumentPartType.PARAGRAPH &&
                    part.getLawPartType() != DocumentPartType.SECTION &&
                    part.getLawPartType() != DocumentPartType.SECTION_LIST_ITEM) {
                html += element("div    ", element("a", text, "href", "#" + part.getId()),
                                "class",
                                "TOCElement_" + part.getType()) + renderTableOfContentsLawPart(
                        part.getSortedParts());
            }
        }
        return html;
    }


    private String renderParts(List<CaseLawDocumentPart> children) {
        StringBuilder sb = new StringBuilder();
        for (CaseLawDocumentPart child : children) {
            if (child != null) {
                sb.append(renderPart(child));
            }
        }
        return sb.toString();
    }

    private String renderPart(CaseLawDocumentPart part) {
        /*
        if (part.getPartType() != null) {
            return renderPart(part, "part_" + part.getPartType().name().toLowerCase());
        } else {
            return renderPart(part, "part_caselaw");
        }
        */
        return renderPart(part, "part_section");
    }

    private String renderPart(CaseLawDocumentPart part, String cssClass) {
        String idStr = "";
        String title = "";

        String text = part.getTextElement().getText();
        if (part.getId() != null) {
            idStr = Long.toString(part.getId());
        }

        if (part.getType() != null) {
            if (DocumentPartType.valueOf(part.getType()) == DocumentPartType.HEADING ||
                    DocumentPartType.valueOf(part.getType()) == DocumentPartType.CHAPTER) {
                title = text;
            }
        }

        part = caseLawPartRepository.findOne(part.getId());
        return element("div", text + renderParts(part.getSortedParts()), "class", cssClass,
                       "id", idStr, "title", title);
    }

    private String renderDescription(CaseLaw caseLaw) {
        return element("div", getCaseLawDescription(caseLaw), "class", "caseLawIntroDescription");
    }

    private String renderMeta(CaseLaw caseLaw) {
        return element("div", element("div", getCaseLawDescription(caseLaw), "class", "caseLawDescription") +
                getMetaPart("Departement", caseLaw.getCreator())
                + getMetaPart("Utf&auml;rdad", caseLaw.getDecisionDate())
                + getMetaPart("Identitet", caseLaw.getCaseIdentifier())
                + getMetaPart("Fallsnummer", caseLaw.getCaseNumber())
                + getMetaPart("Sidnummer", caseLaw.getPageNumber())
                + getMetaPart("Publikationsdatum", caseLaw.getPublicationYear())
                + getMetaPart("Relation", caseLaw.getRelation())
                + getMetaPart("K&auml;lla", caseLaw.getPublisher()),
                       "class", "lawMeta"
                      );
    }

    private String getMetaPart(String title, String content) {
        return element("div",
                       element("div", title, "class", "metaTitle") +
                               element("div", content, "class", "metaText"),
                       "class", "metaHeading");
    }

    private String getCaseLawDescription(CaseLaw caseLaw) {
        return caseLaw.getDescription();
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
