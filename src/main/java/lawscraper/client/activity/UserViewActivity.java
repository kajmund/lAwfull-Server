package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import lawscraper.client.ClientFactory;
import lawscraper.client.place.UserPlace;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.UserView;
import lawscraper.client.ui.panels.userspanel.UsersPanel;
import lawscraper.shared.UserRequestFactory;
import lawscraper.shared.proxies.UserProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:49 PM
 */
public class UserViewActivity extends AbstractActivity implements UserView.Presenter {
    final UserRequestFactory requests = GWT.create(UserRequestFactory.class);
    private ClientFactory clientFactory;
    private UserView userView;
    private UserPlace place;

    public UserViewActivity(UserPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }


    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        requests.initialize(eventBus);
        StartView startView = clientFactory.getStartView();
        panel.setWidget(startView.asWidget());

        UserView userView = clientFactory.getUserView();
        userView.setPresenter(this);
        this.userView = userView;
        startView.getMainContainer().clear();
        startView.getMainContainer().add(userView);
        getUsers(userView.getUsersPanel());
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

    @Override
    public void getUsers(final UsersPanel usersPanel) {
        UserRequestFactory.UserRequest context = requests.userRequest();
        context.findAll().fire(new Receiver<List<UserProxy>>() {

            @Override
            public void onSuccess(List<UserProxy> response) {
                usersPanel.setUsers(response);
            }

            @Override
            public void onFailure(ServerFailure error) {
                if (error.getExceptionType().equals("org.springframework.security.access.AccessDeniedException")) {
                    //todo:setup general exception handler
                    Window.alert("Du har inte access");
                }
            }
        });

    }

    @Override
    public void addUser(String eMail, String userName, String password) {
        UserRequestFactory.UserRequest context = requests.userRequest();
        UserProxy newUser = context.create(UserProxy.class);
        newUser.seteMail(eMail);
        newUser.setUserName(userName);
        newUser.setPassword(password);

        context.addUser(newUser).fire(new Receiver<Long>() {
            @Override
            public void onSuccess(Long response) {
                //todo implement log
                System.out.println("Added user");
                getUsers(userView.getUsersPanel());
            }
        });
    }

    @Override
    public UsersPanel getUserPanel() {
        return userView.getUsersPanel();
    }
}
