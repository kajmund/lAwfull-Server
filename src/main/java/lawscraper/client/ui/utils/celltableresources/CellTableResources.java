package lawscraper.client.ui.utils.celltableresources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

/**
 * Created by johankarlsteen, IT Bolaget Per & Per AB

 * Date: 2011-02-25
 * Time: 13.15
 */
public interface CellTableResources extends CellTable.Resources {
    public static final CellTableResources INSTANCE = GWT.create(CellTableResources.class);

    /**
     * The background used for header cells.
     */
    @Override
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource cellTableHeaderBackground();

    /**
     * The background used for footer cells.
     */
    @Override
    @Source("cellTableHeaderBackground.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource cellTableFooterBackground();

    /**
     * The background used for selected cells.
     */
    @Override
    @Source("cellListSelectedBackground.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.Horizontal)
    ImageResource cellTableSelectedBackground();

    @ClientBundle.Source({"CellTable.css"})
    CellTableStyle cellTableStyle();

    public interface CellTableStyle extends CellTable.Style {

    }

    ;

};