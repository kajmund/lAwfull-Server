package lawscraper.client.ui.panels.lawtablayoutpanel;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/12/12
 * Time: 10:38 AM
 */
public class LawTabLayoutPanel extends TabLayoutPanel {
    private final FlowPanel tabBar = new FlowPanel();

    public LawTabLayoutPanel(double height, Style.Unit unit) {
        super(height, unit);
        tabBar.getElement().getStyle().setWidth(100, Style.Unit.PCT);
    }


    public void setBarUnit(String barUnit) {
    }

    public void setBarHeight(String barHeight) {
    }
}

