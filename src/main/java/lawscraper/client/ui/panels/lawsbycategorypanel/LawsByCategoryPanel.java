package lawscraper.client.ui.panels.lawsbycategorypanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import lawscraper.client.ui.panels.lawtreeitem.LawTreeItem;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawsByCategoryPanel extends Composite {
    private static LawsByCategoryPanelUiBinder uiBinder = GWT.create(LawsByCategoryPanelUiBinder.class);

    @UiField FlowPanel lawTableContainer;
    @UiField Tree lawGroups;
    private LawCategoryChangeHandler categoryChangeHandler;

    interface LawsByCategoryPanelUiBinder extends UiBinder<FlowPanel, LawsByCategoryPanel> {
    }

    public LawsByCategoryPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        setupLawTreeItems();
    }

    private void setupLawTreeItems() {
        lawGroups.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent event) {
                String lawKey;
                try {
                    lawKey = ((LawTreeItem) event.getSelectedItem()).getLawKey();
                } catch (Exception e) {
                    return;
                }
                categoryChangeHandler.onCategoryChange(new LawCategoryChangeEvent(lawKey));
            }
        });
    }

    public HandlerRegistration addChangeHandler(LawCategoryChangeHandler changeHandler) {
        this.categoryChangeHandler = changeHandler;
        return this.addHandler(changeHandler, LawCategoryChangeEvent.getType());
    }
}
