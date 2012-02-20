package lawscraper.server.entities.law;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/23/12
 * Time: 3:23 PM
 */
public enum HeadlineType {
    HEADLINE("Huvudrubrik"),
    SUBHEADLINE("Underrubrik");

    private String headlineType;

    HeadlineType(String headlineType) {
        this.headlineType = headlineType;
    }
    public String getHeadlineType() {
        return headlineType;
    }
}
