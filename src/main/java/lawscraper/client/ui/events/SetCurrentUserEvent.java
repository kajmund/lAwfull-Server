package lawscraper.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import lawscraper.shared.proxies.UserProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 5/16/12
 * Time: 10:08 AM
 */
public class SetCurrentUserEvent extends GwtEvent<SetCurrentUserEventHandler> {
    public static Type<SetCurrentUserEventHandler> TYPE = new Type<SetCurrentUserEventHandler>();
    private final UserProxy userProxy;

    public SetCurrentUserEvent(UserProxy userProxy) {
        this.userProxy = userProxy;
    }

    @Override
    public Type<SetCurrentUserEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetCurrentUserEventHandler handler) {
        handler.onSetCurrentUser(this);
    }

    public UserProxy getUser() {
        return userProxy;
    }
}
