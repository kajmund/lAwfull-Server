package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.ui.panels.addlegalresearchdialog.AddLegalResearchDialog;
import lawscraper.client.ui.panels.celltables.CellTableClickEvent;
import lawscraper.client.ui.panels.celltables.CellTableClickHandler;
import lawscraper.client.ui.panels.celltables.LegalResearchCellTable;
import lawscraper.shared.proxies.LegalResearchProxy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 11/9/12
 * Time: 11:48 AM
 */

public class LegalResearchBrowsePanel extends BrowsePanel implements HasClickHandlers {

    private static LegalResearchBrowsePanelUiBinder lRuiBinder = GWT.create(LegalResearchBrowsePanelUiBinder.class);

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addHandler(handler, ClickEvent.getType());
    }

    interface LegalResearchBrowsePanelUiBinder extends UiBinder<FlowPanel, LegalResearchBrowsePanel> {
    }

    private LegalResearchCellTable legalResearchTableForFlerps;
    private LegalResearchCellTable legalResearchTableForSearch;
    private AddLegalResearchDialog addLegalResearchDialog;

    private boolean saveButtonClicked;
    private boolean cancelButtonClicked;


    @UiField Button addLegalResearchButton;

    public LegalResearchBrowsePanel() {
    }

    @Override
    void initWidget() {
        initWidget(lRuiBinder.createAndBindUi(this));
    }

    @Override
    public void initPanel() {
        super.initPanel();

        legalResearchTableForFlerps = new LegalResearchCellTable();
        legalResearchTableForSearch = new LegalResearchCellTable();

        legalResearchTableForFlerps.setHeight("700px");
        legalResearchTableForSearch.setHeight("700px");

        setFlerpNames(
                Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                              "S", "T", "U", "V", "X", "Y", "Z", "Å", "Ä", "Ö"));

        for (String string : getFlerpNames()) {
            verticalTabPanel.add(new FlowPanel(), string);
        }

        legalResearchTableForFlerps.addChangeHandler(new CellTableClickHandler() {
            @Override
            public void onResultClick(CellTableClickEvent event) {
                resultTableClickAction(event);
            }
        });

        resultCellTableSearchContainer.add(legalResearchTableForSearch);
        setPanelTitleText("Rättsutredningar");
    }

    public void setLegalResearch(List<LegalResearchProxy> legalResearches, String searchQuery) {
        if (searchQuery.length() < 3) {
            FlowPanel tabContainer =
                    (FlowPanel) verticalTabPanel.getContainerWidget(searchQuery);

            if (tabContainer.getWidgetIndex(legalResearchTableForFlerps) == -1) {
                legalResearchTableForFlerps.setLegalResearch(legalResearches);
                tabContainer.add(legalResearchTableForFlerps);
            }
        } else {
            legalResearchTableForSearch.setLegalResearch(legalResearches);
        }
    }


    @UiHandler("addLegalResearchButton")
    void onAddLegalResearchButtonClick(final ClickEvent clickEvent) {
        addLegalResearchDialog = new AddLegalResearchDialog();

        addLegalResearchDialog.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setSaveButtonClicked();
                fireEvent(clickEvent);
            }
        });

        addLegalResearchDialog.show();
    }

    public boolean isSaveButtonClicked() {
        return saveButtonClicked;
    }

    public boolean isCancelButtonClicked() {
        return cancelButtonClicked;
    }

    private void setSaveButtonClicked() {
        saveButtonClicked = true;
        cancelButtonClicked = false;
    }

    private void setCancelButtonClicked() {
        cancelButtonClicked = true;
        saveButtonClicked = false;
    }

    public AddLegalResearchDialog getAddLegalResearchDialog() {
        return addLegalResearchDialog;
    }

}


