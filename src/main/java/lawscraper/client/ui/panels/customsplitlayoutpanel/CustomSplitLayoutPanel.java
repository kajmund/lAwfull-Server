package lawscraper.client.ui.panels.customsplitlayoutpanel;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 11/10/12
 * Time: 11:02 AM
 */

import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class CustomSplitLayoutPanel extends SplitLayoutPanel {

    public void setSplitPosition(Widget widgetBeforeTheSplitter, double
            size, boolean animate) {

        LayoutData layout = (LayoutData)
                widgetBeforeTheSplitter.getLayoutData();
        layout.oldSize = layout.size;
        layout.size = size;
        if (animate) {
            animate(500);
        } else {
            forceLayout();
        }
    }
}