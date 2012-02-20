package lawscraper.client.ui.lawdisplay.sectiondisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.server.entities.law.Section;

public class SectionDisplay extends Composite {
    private static StartViewImplUiBinder uiBinder = GWT.create(StartViewImplUiBinder.class);

    interface StartViewImplUiBinder extends UiBinder<Widget, SectionDisplay> {
    }

    @UiField HTMLPanel section;
    private String name;

    public SectionDisplay() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setSection(Section section) {
        
    }
}
