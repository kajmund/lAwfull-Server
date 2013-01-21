package lawscraper.client.ui.panels.legalresearchpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Date: 11/15/12
 * Time: 8:55 AM
 */
public class LegalResearchPanel extends Composite {
    interface LegalResearchPanelUiBinder extends UiBinder<FlowPanel, LegalResearchPanel> {
    }

    interface Style extends CssResource {
        String mainContainer();
    }

    private static LegalResearchPanelUiBinder uiBinder = GWT.create(LegalResearchPanelUiBinder.class);
    @UiField FlowPanel resultCellTableDocumentContainer;

    public LegalResearchPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
