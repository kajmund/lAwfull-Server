package lawscraper.server.service;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;

import java.util.List;

/**
 * TODO Document this.
 */
public class LawRendererImpl implements LawRenderer {
    @Override
    public String renderToHtml(Law law) {
        return element("table", element("tbody", renderMeta(law) + renderParts(law.getChildren())));
    }

    private String renderParts(List<LawDocumentPart> children) {
        return renderParts(children, new LawRenderContext());
    }

    private String renderParts(List<LawDocumentPart> children, LawRenderContext context) {
        StringBuilder sb = new StringBuilder();
        for (LawDocumentPart child : children) {
            sb.append(renderPart(child, context));
        }
        return sb.toString();
    }

    private String renderPart(LawDocumentPart part, LawRenderContext context) {
        switch (part.getLawPartType()) {
            case DIVIDER:
                return renderDivider(part, context);
            case CHAPTER:
                return renderChapter(part, context);
            case DEPRECATED:
                return renderDeprecated(part, context);
            case HEADING:
                return renderHeader(part, context);
            case PARAGRAPH:
                return renderParagraph(part, context);
            case SECTION:
                return renderSection(part, context);
            case SECTION_LIST_ITEM:
                return renderSectionListItem(part);
        }
        return null;
    }

    private String renderSectionListItem(LawDocumentPart part) {
        return element("li", part.getTextElement().getText());
    }

    private String renderSection(LawDocumentPart part, LawRenderContext context) {
        String numberedList = "";
        if (!part.getChildren().isEmpty()) {
            numberedList = element("ul", renderChildren(part, context), "class", "part_section_list_item");
        }
        return element("span", part.getTextElement().getText(), "class", "part_section") + numberedList;
    }

    private String renderDeprecated(LawDocumentPart part, LawRenderContext context) {
        return renderTopLevelPart(part, "part_deprecated") + renderChildren(part, context);
    }

    private String renderChapter(LawDocumentPart part, LawRenderContext context) {
        return renderTopLevelPart(part, "part_chapter") + renderChildren(part, context);
    }

    private String renderDivider(LawDocumentPart part, LawRenderContext lawRenderContext) {
        return renderTopLevelPart(part, "part_divider") + renderChildren(part, lawRenderContext);
    }

    private String renderHeader(LawDocumentPart part, LawRenderContext context) {
        context.increaseHeaderLevel();
        String childContent = renderChildren(part, context);
        context.decreaseHeaderLevel();
        return element("tr", element("td",
                element("h" + (2 + context.getHeaderLevel()), part.getTextElement().getText(),
                        "class", "part_heading"))) + childContent;
    }

    private String renderParagraph(LawDocumentPart part, LawRenderContext lawRenderContext) {
        return element("tr", element("td", element("p",
                element("span", part.getTextElement().getText(), "class", "part_paragraph") +
                        (part.getChildren().isEmpty() ? "" : renderPart(part.getChildren().get(0), lawRenderContext)))
                + (part.getChildren().size() > 1 ?
                renderParts(part.getChildren().subList(1, part.getChildren().size()))
                : "")
        ));
    }

    private String renderTopLevelPart(LawDocumentPart part, String cssClass) {
        return element("tr", element("td", element("h2", part.getTextElement().getText(), "class", cssClass)));
    }

    private String renderChildren(LawDocumentPart part, LawRenderContext context) {
        context.increaseLevel();
        String childContents = renderParts(part.getChildren(), context);
        context.decreaseLevel();
        return childContents;
    }

    private String renderMeta(Law law) {
        return element("tr", element("td", element("h1", getLawTitle(law), "class", "lawTitle") +
                element("dl", getMetaPart("Departement", law.getCreator())
                        + getMetaPart("Utfärdad", law.getReleaseDate())
                        + getMetaPart("Källa", law.getPublisher())
                        + getMetaPart("Senast hämtad", law.getLatestFetchFromGov())
                )));
    }

    private String getMetaPart(String title, String content) {
        return element("dt", title, "class", "metaTitle") + element("dd", content, "class", "metaText");
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
