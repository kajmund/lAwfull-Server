package lawscraper.server.entities.law;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/21/12
 * Time: 9:58 AM
 */
public class LawDocumentPart extends DocumentPart {
    @RelatedTo
    Law deprecatedByLaw;
    @RelatedTo
    Law replacesLaw;
    String key;
    @RelatedTo
    TextElement textElement = new TextElement();
    @RelatedTo(type = "HAS_SUB_PART")
    @Fetch
    Collection<LawDocumentPart> parts = new HashSet<LawDocumentPart>();
    @RelatedTo(direction = Direction.INCOMING, type = "HAS_SUB_PART")
    LawDocumentPart parent;
    @RelatedTo(direction = Direction.INCOMING, type = "PREVIOUS_VERSION")
    LawDocumentPart nextVersion;
    @RelatedTo(type = "PREVIOUS_VERSION")
    LawDocumentPart previousVersion;
    @RelatedTo
    LawDocumentPart transitionalProvision;
    private boolean deprecated;

    public Law getReplacesLaw() {
        return replacesLaw;
    }

    public void setReplacesLaw(Law replacesLaw) {
        this.replacesLaw = replacesLaw;
    }

    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    public Law getDeprecatedByLaw() {
        return deprecatedByLaw;
    }

    public void setDeprecatedByLaw(Law deprecatedByLaw) {
        this.deprecatedByLaw = deprecatedByLaw;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Collection<LawDocumentPart> getParts() {
        return parts;     
    }

    public List<LawDocumentPart> getSortedParts() {
        ArrayList<LawDocumentPart> lawDocumentParts = new ArrayList<LawDocumentPart>(getParts());
        Collections.sort(lawDocumentParts, new Comparator<LawDocumentPart>() {
            @Override
            public int compare(LawDocumentPart o, LawDocumentPart o1) {
                return o.getOrder() - o1.getOrder();
            }
        });
        return lawDocumentParts;
    }

    public LawDocumentPart getParent() {
        //return parentRel.getParent();
        return null;
    }

    public LawDocumentPart getNextVersion() {
        return nextVersion;
    }

    public void setNextVersion(LawDocumentPart nextVersion) {
        this.nextVersion = nextVersion;
    }

    public LawDocumentPart getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(LawDocumentPart previousVersion) {
        this.previousVersion = previousVersion;
    }

    public LawDocumentPart getTransitionalProvision() {
        return transitionalProvision;
    }

    public void setTransitionalProvision(LawDocumentPart transitionalProvision) {
        this.transitionalProvision = transitionalProvision;
    }

    public void addText(String string) {
        textElement.setText(string);
    }

    public void setDeprecated(boolean b) {
        this.deprecated = b;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setLawPartType(LawDocumentPartType type) {
        setType(type.name());
    }

    public LawDocumentPartType getLawPartType() {
        return LawDocumentPartType.valueOf(getType());
    }

    public void addDocumentPartChild(LawDocumentPart subPart) {
        subPart.setOrder(parts.size());
        parts.add(subPart);
    }

}
