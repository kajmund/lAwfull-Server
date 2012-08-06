package lawscraper.client.ui.panels.lawtreeitem;

import com.google.gwt.user.client.ui.TreeItem;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/2/12
 * Time: 11:46 PM
 */
public class LawTreeItem extends TreeItem {

    private String lawKey = "";

    public LawTreeItem() {

    }

    public void setLawKey(String lawKey) {
        this.lawKey = lawKey;
    }

    public String getLawKey() {
        return lawKey;
    }
}
