package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import lawscraper.client.ui.panels.userspanel.UsersPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/25/12
 * Time: 10:07 AM
 */
public interface UserView extends IsWidget {
    void setPresenter(Presenter listener);

    UsersPanel getUsersPanel();

    public interface Presenter {
        void goTo(Place place);

        void getUsers(UsersPanel usersPanel);

        void addUser(String eMail, String userName, String password);

        UsersPanel getUserPanel();
    }
}
