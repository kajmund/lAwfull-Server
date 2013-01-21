package lawscraper.shared;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/1/13
 * Time: 9:25 PM
 */
public class DocumentResponse<T> {
    private DocumentRequest documentRequest;
    private List<T> documentListItems;
    private Range range;
    private int length;

    public DocumentResponse(DocumentRequest documentRequest, List<T> documentListItems, Range range, int length) {
        this.documentRequest = documentRequest;
        this.documentListItems = documentListItems;
        this.range = range;
        this.length = length;
    }

    public DocumentRequest getDocumentRequest() {
        return documentRequest;
    }

    public void setDocumentRequest(DocumentRequest documentRequest) {
        this.documentRequest = documentRequest;
    }

    public List<T> getDocumentListItems() {
        return documentListItems;
    }

    public void setDocumentListItems(List<T> documentListItems) {
        this.documentListItems = documentListItems;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

}
