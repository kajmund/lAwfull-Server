package lawscraper.client.ui.panels.bookmarkpanel;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 3/10/12
 * Time: 5:26 PM
 */
public class BookMark {
    int id;
    String name;

    public BookMark(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
