package lawscraper.client.ui.panels.bookmarkpanel;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 3/15/12
 * Time: 9:53 AM
 */
public class AnchorCell extends TextCell {
    public AnchorCell() {
        if (template == null) {
            template = GWT.create(Template.class);
        }
    }

    interface Template extends SafeHtmlTemplates {
        @Template("<a href=\"#{0}\">BookMark {0}</a>")
        SafeHtml anchor(String url);
    }

    private static Template template;

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        if (value != null) {
            sb.append(template.anchor(value));
        }
    }
}
