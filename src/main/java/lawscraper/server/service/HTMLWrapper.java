package lawscraper.server.service;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 11:07 AM
 */
public class HTMLWrapper {
    private String html;
    private String name;
    private String lawKey;

    public HTMLWrapper() {
    }

    public HTMLWrapper(String name, String lawKey, String html) {
        this.name = name;
        this.html = html;
        this.lawKey = lawKey;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLawKey() {
        return lawKey;
    }

    public void setLawKey(String lawKey) {
        this.lawKey = lawKey;
    }
}
