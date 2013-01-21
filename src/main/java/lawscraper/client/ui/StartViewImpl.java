package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ClientFactory;
import lawscraper.client.ClientFactoryImpl;
import lawscraper.client.place.DocumentPlace;
import lawscraper.client.ui.loginrequest.LoginRequest;
import lawscraper.client.ui.panels.browsepanels.*;
import lawscraper.client.ui.panels.customsplitlayoutpanel.CustomSplitLayoutPanel;
import lawscraper.client.ui.panels.documentpanel.DocumentPanel;
import lawscraper.client.ui.panels.documentsearchpanel.DocumentResultElement;
import lawscraper.client.ui.panels.documentsearchpanel.DocumentSearchPanel;
import lawscraper.client.ui.panels.dynamictabpanel.DynamicTabPanel;
import lawscraper.client.ui.panels.events.PanelChangeEvent;
import lawscraper.client.ui.panels.events.PanelChangeHandler;
import lawscraper.client.ui.panels.lawpanel.LawPanel;
import lawscraper.client.ui.panels.lawsbycategorypanel.LawCategoryChangeEvent;
import lawscraper.client.ui.panels.lawsbycategorypanel.LawsByCategoryPanel;
import lawscraper.shared.DocumentListItem;
import lawscraper.shared.proxies.*;

import java.util.Date;
import java.util.List;


public class StartViewImpl extends Composite implements StartView {
    private static StartViewImplUiBinder uiBinder = GWT.create(StartViewImplUiBinder.class);
    private ClientFactory clientFactory;

    interface StartViewImplUiBinder extends UiBinder<Widget, StartViewImpl> {
    }

    @UiField Button scrapeLawButton;
    @UiField FlowPanel container;
    @UiField FormPanel loginForm;
    @UiField DeckPanel loginDeckPanel;
    @UiField Label userName;
    @UiField PasswordTextBox passwordTextBox;
    @UiField TextBox userNameTextBox;
    @UiField FlowPanel loginContainer;
    @UiField Button scrapeCaseLawButton;
    @UiField ToggleButton lawsCategoriesButton;
    @UiField FlowPanel leftContainer;
    @UiField FlowPanel mainContainer;
    @UiField ToggleButton browseLawsMenuButton;
    @UiField LawsByCategoryPanel lawsByCategoryPanel;
    @UiField LawCasesBrowsePanel lawCasesPanel;
    @UiField ToggleButton browseLawCasesMenuButton;
    @UiField ToggleButton browseLegalResearchMenuButton;
    @UiField FlowPanel leftMenuContainer;

    @UiField(provided = true)
    SplitLayoutPanel splitLayoutPanel = new CustomSplitLayoutPanel();
    @UiField FlowPanel pageSection;
    @UiField FlowPanel header;
    @UiField DeckPanel leftContainerDeckPanel;
    @UiField LawsBrowsePanel lawPanel;
    @UiField LegalResearchBrowsePanel legalResearchBrowsePanel;
    @UiField Button searchButton;
    @UiField FlowPanel dynamicTabPanelContainer;
    @UiField DynamicTabPanel dynamicTabPanelDeckPanel;

    private Presenter presenter;

    public StartViewImpl(ClientFactoryImpl clientFactory) {
        this.clientFactory = clientFactory;
        initWidget(uiBinder.createAndBindUi(this));

        loginDeckPanel.showWidget(0);

        splitLayoutPanel.getElement().getStyle().setPosition(Style.Position.STATIC);
        leftContainerDeckPanel.showWidget(0);

        //splitLayoutPanel.add(dynamicTabPanelDeckPanel);
        dynamicTabPanelContainer.add(dynamicTabPanelDeckPanel.getTabFlerpContainer());
        //   dynamicTabDeckPanelContainer.add(dynamicTabPanelDeckPanel);
    }

    @Override
    public void setUserLoggedIn(UserProxy user) {
        if (user != null) {
            userName.setText(user.getUserName());
            loginDeckPanel.showWidget(1);
        } else {
            loginDeckPanel.showWidget(0);
        }
    }

    @Override
    public void setLegalResearch(List<LegalResearchProxy> response, String query) {
        legalResearchBrowsePanel.setLegalResearch(response, query);
    }

    @Override
    public void setCaseLaws(List<DocumentListItem> response) {
        lawCasesPanel.setLawCases(response);
    }

    @UiHandler("scrapeLawButton")
    void onClickScrapeLawButton(ClickEvent e) {
        presenter.scrapeLaw();
    }

    @UiHandler("scrapeCaseLawButton")
    void onClickScrapeCaseLawButton(ClickEvent e) {
        presenter.scrapeCaseLaw();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.checkCurrentUser();
    }

    @Override
    public void setLaws(List<LawProxy> laws, String searchQuery) {
        lawPanel.setLaws(laws, searchQuery);
    }

    @Override
    public boolean selectDocumentTabIfExists(String key) {
        return dynamicTabPanelDeckPanel.selectFlerpIfExists(key);
    }

    @Override
    public void addDocument(HTMLProxy result) {

        LawPanel lawPanel = new LawPanel();
        lawPanel.setLawAsHTML(result.getHtml());
        dynamicTabPanelDeckPanel.add(lawPanel, result.getName(), result.getLawKey());
    }

    @Override
    public FlowPanel getMainContainer() {
        return container;

    }

    @UiHandler("passwordTextBox")
    void onEnterPress(KeyUpEvent e) {
        if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            if (userNameTextBox.getText().length() > 0 && passwordTextBox.getText().length() > 0) {
                authenticate(userNameTextBox.getText(), passwordTextBox.getText());
            }
        }
    }

    @UiHandler("lawsCategoriesButton")
    void lawExplorerPressed(ClickEvent e) {

        if (lawsCategoriesButton.isDown()) {
            leftContainerDeckPanel.showWidget(0);
            leftContainerDeckPanel.setVisible(true);
        } else {
            leftContainerDeckPanel.setVisible(false);
        }

        setAllLeftMenuButtonsUp(lawsCategoriesButton);
    }

    @UiHandler("searchButton")
    void onClickSearchButton(ClickEvent e) {
        DocumentPanel widget = new DocumentPanel();
        DocumentSearchPanel documentSearchPanel = new DocumentSearchPanel();
        documentSearchPanel.addPanelChangeHandler(new PanelChangeHandler() {
            @Override
            public void onPanelChange(PanelChangeEvent event) {
                if (event.getPanelChangeType() == PanelChangeEvent.PanelChangeType.openReference) {
                    DocumentResultElement docResult = (DocumentResultElement) event.getObject();
                    //Window.alert(docResult.getDocumentId() + " reference");
                } else if (event.getPanelChangeType() == PanelChangeEvent.PanelChangeType.openDescription) {
                    DocumentResultElement docResult = (DocumentResultElement) event.getObject();
                    //Window.alert(docResult.getDocumentId() + " description");
                    presenter.getDocumentDescription(docResult);
                }
            }
        });

        widget.setDocumentWidget(documentSearchPanel);
        dynamicTabPanelDeckPanel.add(widget, "Sökning...", new Date().toString());
    }

    private void setAllLeftMenuButtonsUp(ToggleButton exceptThisOne) {
        for (int i = 0; i < leftMenuContainer.getWidgetCount(); i++) {
            Widget widget = leftMenuContainer.getWidget(i);
            if (widget.getClass().getName().endsWith("ToggleButton")) {
                ToggleButton toggleButton = (ToggleButton) widget;
                if (exceptThisOne != null && !exceptThisOne.equals(toggleButton)) {
                    toggleButton.setDown(false);
                }
            }
        }
        handleLeftSplitter();
    }

    @UiHandler("browseLawsMenuButton")
    void searchMenuButtonPressed(ClickEvent e) {
        if (browseLawsMenuButton.isDown()) {
            leftContainerDeckPanel.showWidget(1);
            leftContainerDeckPanel.setVisible(true);
        } else {
            leftContainerDeckPanel.setVisible(false);
        }

        setAllLeftMenuButtonsUp(browseLawsMenuButton);
    }

    @UiHandler("browseLawCasesMenuButton")
    void lawCasesByYearPressedTest(ClickEvent clickEvent) {
        if (browseLawCasesMenuButton.isDown()) {
            leftContainerDeckPanel.showWidget(2);
            leftContainerDeckPanel.setVisible(true);
        } else {
            leftContainerDeckPanel.setVisible(false);
        }
        setAllLeftMenuButtonsUp(browseLawCasesMenuButton);
    }

    @UiHandler("browseLegalResearchMenuButton")
    void browseLegalResearchMenuButtonPressed(ClickEvent e) {
        if (browseLegalResearchMenuButton.isDown()) {
            leftContainerDeckPanel.showWidget(3);
            leftContainerDeckPanel.setVisible(true);
        } else {
            leftContainerDeckPanel.setVisible(false);
        }

        setAllLeftMenuButtonsUp(browseLegalResearchMenuButton);
    }


    private void handleLeftSplitter() {
        CustomSplitLayoutPanel customSplitLayoutPanel = (CustomSplitLayoutPanel) splitLayoutPanel;
        if (!anyToggleButtonIsDown()) {
            customSplitLayoutPanel.setSplitPosition(leftContainer, 0, true);
        } else {
            customSplitLayoutPanel.setSplitPosition(leftContainer, 300, true);
        }

    }

    @UiHandler("lawPanel")
    void searchResultChange(BrowsePanelChangeEvent browsePanelChangeEvent) {
        presenter.searchLaws(browsePanelChangeEvent.getQuery());
    }

    @UiHandler("lawPanel")
    void searchResultChange(ResultDocumentClickEvent resultDocumentClickEvent) {
        presenter.goTo(new DocumentPlace(resultDocumentClickEvent.getLawKey()));
    }

    /*
        @UiHandler("caseLawPanel")
        public void searchCaseLawResultChange(ResultDocumentClickEvent resultDocumentClickEvent) {
            presenter.goTo(new DocumentPlace(resultDocumentClickEvent.getLawKey()));
        }
    */
    @UiHandler("lawsByCategoryPanel")
    public void onLawCategoryChange(LawCategoryChangeEvent changeEvent) {
        presenter.goTo(new DocumentPlace(changeEvent.getLawKey()));
    }

    @UiHandler("lawCasesPanel")
    void onLawCaseCategoryChange(BrowsePanelChangeEvent changeEvent) {
        if (changeEvent.isSearchRequest()) {
            presenter.searchCaseLaws(changeEvent.getQuery());
        } else {
            String[] querySplitted = changeEvent.getQuery().split(":");
            presenter.getCaseLawsByYearAndCourt(querySplitted[1], querySplitted[0]);
        }
    }

    @UiHandler("legalResearchBrowsePanel")
    void onBrowseLegalResearchPanelChange(BrowsePanelChangeEvent changeEvent) {
        if (changeEvent.isSearchRequest()) {
            //todo: switch to other eventhandler
            presenter.searchLegalResearch(changeEvent.getQuery());
        } else {
            presenter.getLegalResearch(changeEvent.getQuery());
        }
    }

    @UiHandler("legalResearchBrowsePanel")
    void onBrowseLegalResearchPanelClick(ClickEvent clickEvent) {
        LegalResearchBrowsePanel browsePanel = (LegalResearchBrowsePanel) clickEvent.getSource();
        if (browsePanel.isSaveButtonClicked()) {
            presenter.addLegalResearch(browsePanel.getAddLegalResearchDialog().getName().getText(),
                                       browsePanel.getAddLegalResearchDialog().getName().getText());
        }
    }

    @UiHandler("lawCasesPanel")
    void searchCaseLawResultChange(ResultDocumentClickEvent resultDocumentClickEvent) {
        presenter.goTo(new DocumentPlace(resultDocumentClickEvent.getLawKey()));
    }


    private void authenticate(String userName, String passWord) {
        new LoginRequest(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() != Response.SC_OK) {
                    onError(request, new RequestException(response.getStatusText() + ":\n" + response.getText()));
                    return;
                }
                if (response.getText().contains("Access Denied")) {
                    Window.alert("Felaktigt lösenord eller användarnamn.");
                } else {
                    presenter.checkCurrentUser();
                }
            }

            public void onError(Request request, Throwable throwable) {
                Window.alert(throwable.getMessage());
            }
        }, userName, passWord);

    }

    private boolean anyToggleButtonIsDown() {
        for (int i = 0; i < leftMenuContainer.getWidgetCount(); i++) {
            Widget widget = leftMenuContainer.getWidget(i);
            if (widget.getClass().getName().endsWith("ToggleButton")) {
                ToggleButton toggleButton = (ToggleButton) widget;
                if (toggleButton.isDown()) {
                    return true;
                }
            }
        }
        return false;

    }
}
