package lawscraper.server.service;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 11:07 AM
 */
public class HTMLWrapper {
    private String html;

    public HTMLWrapper() {
    }

    public HTMLWrapper(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
