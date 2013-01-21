package lawscraper.shared;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/1/13
 * Time: 9:23 PM
 */

public class DocumentRequest {
    Range range;
    String documentKey;
    String query;

    Map<DocumentPartType, Boolean> types = ALLTYPES();

    static public HashMap<DocumentPartType, Boolean> ALLTYPES() {
        HashMap<DocumentPartType, Boolean> ret = new HashMap<DocumentPartType, Boolean>();
        ret.put(DocumentPartType.CASELAW, true);
        ret.put(DocumentPartType.LAW, true);
        ret.put(DocumentPartType.DIVIDER, true);
        ret.put(DocumentPartType.HEADING, true);
        ret.put(DocumentPartType.CHAPTER, true);
        ret.put(DocumentPartType.PARAGRAPH, true);
        ret.put(DocumentPartType.SECTION, true);
        ret.put(DocumentPartType.SECTION_LIST_ITEM, true);
        ret.put(DocumentPartType.SUB_HEADING, true);
        ret.put(DocumentPartType.PARAGRAPH_DEPRECATED, true);
        ret.put(DocumentPartType.CASELAW, true);
        ret.put(DocumentPartType.CASELAW_SECTION, true);
        ret.put(DocumentPartType.PROPOSITION, true);
        ret.put(DocumentPartType.SUBJECT, true);
        return ret;
    }

    public DocumentRequest(Range range, String documentKey, String query) {
        this.range = range;
        this.documentKey = documentKey;
        this.query = query;
    }

    public DocumentRequest(int from, int to, String query) {
        this(new Range(from, to), null, query);
    }

    public DocumentRequest(String key) {
        this(null, key, null);
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Map<DocumentPartType, Boolean> getTypes() {
        return types;
    }

    public void setTypes(Map<DocumentPartType, Boolean> types) {
        this.types = types;
    }

}
