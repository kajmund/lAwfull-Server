package lawscraper.client.ui.panels.documentsearchpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import lawscraper.client.ui.panels.celltables.LawCellTable;
import lawscraper.client.ui.panels.events.HasPanelChangeHandler;
import lawscraper.client.ui.panels.events.PanelChangeEvent;
import lawscraper.client.ui.panels.events.PanelChangeHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/24/12
 * Time: 9:17 PM
 */
public class DocumentResultElement extends Composite implements HasPanelChangeHandler {
    private static DocumentResultElementUiBinder uiBinder = GWT.create(DocumentResultElementUiBinder.class);
    @UiField LawCellTable lawCellTable;
    @UiField DisclosurePanel referencePanel;
    @UiField Label court;
    @UiField Label date;
    @UiField Label source;
    @UiField Label title;
    @UiField Label type;
    @UiField DisclosurePanel descriptionPanel;
    @UiField Label description;

    private String documentId;
    private PanelChangeHandler handler;

    public void setDescription(String description) {
        this.description.setText(description);
    }

    interface DocumentResultElementUiBinder extends UiBinder<FlowPanel, DocumentResultElement> {
    }

    public DocumentResultElement() {
        initWidget(uiBinder.createAndBindUi(this));
        descriptionPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
            @Override
            public void onOpen(OpenEvent<DisclosurePanel> event) {
                handler.onPanelChange(new PanelChangeEvent(this, PanelChangeEvent.PanelChangeType.openDescription));
            }
        });

        referencePanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
            @Override
            public void onOpen(OpenEvent<DisclosurePanel> event) {
                handler.onPanelChange(new PanelChangeEvent(this, PanelChangeEvent.PanelChangeType.openReference));
            }
        });
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    @Override
    public void addPanelChangeHandler(PanelChangeHandler handler) {
        this.handler = handler;
    }
}
