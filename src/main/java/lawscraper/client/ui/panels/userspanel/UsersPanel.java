package lawscraper.client.ui.panels.userspanel;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.ui.UserView;
import lawscraper.client.ui.panels.adduserdialog.AddUserDialog;
import lawscraper.shared.proxies.UserProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class UsersPanel extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<UserProxy> table;
    private List<UserProxy> users = new ArrayList<UserProxy>();
    @UiField FlowPanel usersContainer;
    @UiField Button addUserButton;
    private UserView.Presenter listener;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, UsersPanel> {
    }

    public UsersPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        usersContainer.add(table);

    }

    public void initPanel() {
        table = new CellTable<UserProxy>();
        Column<UserProxy, String> eMail = addEMailColumn(table);
        Column<UserProxy, String> nameColumn = addNameColumn(table);
        Column<UserProxy, String> userRoleColumn = addUserRoleColumn(table);
        Column<UserProxy, String> passWordColumn = addPasswordColumn(table);

        table.setColumnWidth(eMail, 2, Style.Unit.PCT);
        table.setColumnWidth(userRoleColumn, 2, Style.Unit.PCT);
        table.setColumnWidth(nameColumn, 2, Style.Unit.PCT);

    }

    public void clearView() {
        this.users.clear();
        updateTableData();
    }

    public void setUsers(List<UserProxy> users) {
        clearView();
        this.users = users;
        updateTableData();
    }

    private Column<UserProxy, String> addNameColumn(CellTable<UserProxy> table) {
        Column<UserProxy, String> nameColumn = getNameColumn(new ClickableTextCell());
        table.addColumn(nameColumn, "Användarnamn");
        return nameColumn;
    }

    private Column<UserProxy, String> addUserRoleColumn(CellTable<UserProxy> table) {
        Column<UserProxy, String> passWord = getUserRoleColumn(new ClickableTextCell());
        table.addColumn(passWord, "Användarroll");
        return passWord;
    }

    private Column<UserProxy, String> addEMailColumn(CellTable<UserProxy> table) {
        Column<UserProxy, String> mailAddr = getMailAddrColumn(new ClickableTextCell());
        table.addColumn(mailAddr, "E-Post");
        return mailAddr;
    }

    private Column<UserProxy, String> addPasswordColumn(CellTable<UserProxy> table) {
        Column<UserProxy, String> passWord = getPassWordColumn(new ClickableTextCell());
        table.addColumn(passWord, "Lösenord");
        return passWord;
    }

    private Column<UserProxy, String> getUserRoleColumn(ClickableTextCell clickableTextCell) {
        Column<UserProxy, String> column = new Column<UserProxy, String>(clickableTextCell) {
            @Override
            public String getValue(UserProxy object) {
                return "användarroll";
            }
        };
/*
        column.setFieldUpdater(new FieldUpdater<LawProxy, String>() {
            @Override
            public void update(int index, LawProxy object, String value) {
                listener.goTo(new DocumentPlace(object.getId()));
            }
        });
*/
        return column;

    }


    private Column<UserProxy, String> getNameColumn(final AbstractCell nameCell) {
        Column<UserProxy, String> column = new Column<UserProxy, String>(nameCell) {
            @Override
            public String getValue(UserProxy object) {
                return object.getUserName();
            }
        };
/*
        column.setFieldUpdater(new FieldUpdater<LawProxy, String>() {
            @Override
            public void update(int index, LawProxy object, String value) {
                listener.goTo(new DocumentPlace(object.getId()));
            }
        });
*/
        return column;
    }

    private Column<UserProxy, String> getMailAddrColumn(final AbstractCell nameCell) {
        Column<UserProxy, String> column = new Column<UserProxy, String>(nameCell) {
            @Override
            public String getValue(UserProxy object) {
                return object.geteMail();
            }
        };

        column.setFieldUpdater(new FieldUpdater<UserProxy, String>() {
            @Override
            public void update(int index, UserProxy object, String value) {
                //listener.goTo(new DocumentPlace(object.getId()));
            }
        });

        return column;
    }

    private Column<UserProxy, String> getPassWordColumn(final AbstractCell nameCell) {
        Column<UserProxy, String> column = new Column<UserProxy, String>(nameCell) {
            @Override
            public String getValue(UserProxy object) {
                return object.getPassword();
            }
        };

        column.setFieldUpdater(new FieldUpdater<UserProxy, String>() {
            @Override
            public void update(int index, UserProxy object, String value) {
                //listener.goTo(new DocumentPlace(object.getId()));
            }
        });

        return column;
    }

    public UserView.Presenter getListener() {
        return listener;
    }

    public void setListener(UserView.Presenter listener) {
        this.listener = listener;
    }

    @UiHandler("addUserButton")
    public void addUserButton(ClickEvent e) {
        new AddUserDialog(listener);

    }

    public void updateTableData() {
        table.setRowCount(0);
        table.setRowCount(users.size(), true);
        table.setRowData(users);
        table.redraw();
    }
}
