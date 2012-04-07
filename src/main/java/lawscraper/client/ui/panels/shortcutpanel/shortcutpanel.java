package lawscraper.client.ui.panels.shortcutpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;


/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 3/11/12
 * Time: 7:16 PM
 */
public class ShortCutPanel extends Composite {
    private static ShortCutPanelUiBinder uiBinder = GWT.create(ShortCutPanelUiBinder.class);
    @UiField Label shortCutLabel;
    private String text;

    public void setText(String text) {
        this.text = text;
        shortCutLabel.setText(text);
    }

    public String getText() {
        return text;
    }

    interface ShortCutPanelUiBinder extends UiBinder<FlowPanel, ShortCutPanel> {
    }

    ShortCutPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
