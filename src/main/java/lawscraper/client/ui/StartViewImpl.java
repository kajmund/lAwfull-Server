package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import lawscraper.client.place.UserPlace;
import lawscraper.client.ui.loginrequest.LoginRequest;
import lawscraper.client.ui.panels.addlegalresearchdialog.AddLegalResearchDialog;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedFlowPanel;
import lawscraper.client.ui.panels.shortsearchresultpanel.ShortSearchResultPanel;
import lawscraper.shared.proxies.LawProxy;
import lawscraper.shared.proxies.LegalResearchProxy;
import lawscraper.shared.proxies.UserProxy;

import java.util.List;


public class StartViewImpl extends Composite implements StartView {
    private static StartViewImplUiBinder uiBinder = GWT.create(StartViewImplUiBinder.class);
    private ClientFactory clientFactory;

    interface StartViewImplUiBinder extends UiBinder<Widget, StartViewImpl> {
    }

    @UiField Button scrapeButton;
    @UiField FlowPanel container;
    @UiField TextBox searchTextBox;
    @UiField Anchor addLegalResearch;
    @UiField Tree adminTree;
    @UiField FormPanel loginForm;
    @UiField DeckPanel loginDeckPanel;
    @UiField Label userName;
    @UiField PasswordTextBox passwordTextBox;
    @UiField TextBox userNameTextBox;
    @UiField FlowPanel loginContainer;
    @UiField RoleBasedFlowPanel adminTreeContainer;
    @UiField RoleBasedFlowPanel legalResearchContainer;
    @UiField Tree legalResearchTree;
    @UiField ListBox legalResearchListBox;
    @UiField RoleBasedFlowPanel legalResearchListBoxContainer;

    ShortSearchResultPanel searchResultPanel;
    private Presenter presenter;

    public StartViewImpl(ClientFactoryImpl clientFactory) {
        this.clientFactory = clientFactory;
        initWidget(uiBinder.createAndBindUi(this));
        adminTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent event) {
                presenter.goTo(new UserPlace());
            }
        });
        loginDeckPanel.showWidget(0);
        setupSearchResultPanel();
        this.clientFactory.getRoleBasedWidgetHandler().addRoleBaseWidget(adminTreeContainer, StartViewImpl.class);
        this.clientFactory.getRoleBasedWidgetHandler().addRoleBaseWidget(legalResearchContainer, StartViewImpl.class);
        this.clientFactory.getRoleBasedWidgetHandler().addRoleBaseWidget(legalResearchListBoxContainer, StartViewImpl.class);

    }

    //extract to own panel
    private void setupSearchResultPanel() {
        searchResultPanel = new ShortSearchResultPanel();
        searchResultPanel.setAnimationEnabled(true);
        searchResultPanel.getElement().getStyle().setMarginRight(80, Style.Unit.PCT);
        searchResultPanel.getElement().getStyle().setMarginTop(81, Style.Unit.PX);
        searchResultPanel.getElement().getStyle().setRight(0, Style.Unit.PX);
        searchResultPanel.hide();
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
    public void setLegalResearch(List<LegalResearchProxy> response) {
        legalResearchTree.clear();
        legalResearchListBox.clear();

        for (LegalResearchProxy legalResearchProxy : response) {
            TreeItem treeItem = new TreeItem();
            treeItem.setHTML(legalResearchProxy.getTitle());
            legalResearchTree.addItem(treeItem);
            legalResearchListBox.addItem(legalResearchProxy.getTitle(), String.valueOf(legalResearchProxy.getId()));

        }

    }

    @UiHandler("legalResearchListBox")
    void onChangeLegalResearchListBox(ChangeEvent e) {
        int selectedIndex = legalResearchListBox.getSelectedIndex();
        presenter.changeActiveLegalResearch(Long.decode(legalResearchListBox.getValue(selectedIndex)));
    }

    @UiHandler("addLegalResearch")
    void onClickAddLegalResearch(ClickEvent e) {
        new AddLegalResearchDialog(presenter);
    }

    @UiHandler("scrapeButton")
    void onClickScrapeButton(ClickEvent e) {
        presenter.scrapeLaw();
    }

    @Override
    public void setName(String helloName) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.searchResultPanel.setPresenter(presenter);
        presenter.checkCurrentUser();
    }

    @Override
    public void setLaws(List<LawProxy> laws) {
        searchResultPanel.setLaws(laws);
    }

    @Override
    public FlowPanel getMainContainer() {
        return container;

    }

    @UiHandler("passwordTextBox")
    public void onEnterPress(KeyUpEvent e) {
        if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            if (userNameTextBox.getText().length() > 0 && passwordTextBox.getText().length() > 0) {
                authenticate(userNameTextBox.getText(), passwordTextBox.getText());
            }
        }
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

    @UiHandler("searchTextBox")
    public void onChangeTextBox(KeyUpEvent e) {
        if (searchTextBox.getText().length() > 3) {
            searchResultPanel.show();
            presenter.searchLaws(searchTextBox.getText());
        } else {
            searchResultPanel.hide();
        }
    }
}
