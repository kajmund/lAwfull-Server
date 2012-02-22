package lawscraper.server.service;

import lawscraper.server.entities.law.Law;

/**
 * TODO Document this.
 */
public class LawRendererImpl implements LawRenderer {
    @Override
    public String renderToHtml(Law law) {
        return element("table", element("tbody", getMeta(law)));
    }

    private String getMeta(Law law) {
        return element("tr", element("td", element("h1", getLawTitle(law)) +
                element("dl", getMetaPart("Departement", law.getCreator())
                        + getMetaPart("Utfärdad", law.getReleaseDate())
                        + getMetaPart("Källa", law.getPublisher())
                        + getMetaPart("Senast hämtad", law.getLatestFetchFromGov())
                )));
    }

    private String getMetaPart(String title, String content) {
        return element("dt", title) + element("dd", content);
    }

    private String getLawTitle(Law law) {
        return law.getTitle() + " (" + law.getFsNumber() + ")";
    }

    private String element(String elementName, String content) {
        return "<" + elementName + ">" + content + "</" + elementName + ">";
    }
}
