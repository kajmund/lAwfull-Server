package lawscraper.server.entities.law;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/21/12
 * Time: 9:58 AM
 */

@NodeEntity
public class LawDocumentPart extends DocumentPart {
    Law deprecatedByLaw;
    Law replacesLaw;
    String key;
    TextElement textElement = new TextElement();
    List<LawDocumentPart> children = new ArrayList<LawDocumentPart>();
    LawDocumentPart parent;
    LawDocumentPart nextVersion;
    LawDocumentPart previousVersion;
    LawDocumentPart transitionalProvision;
    private boolean deprecated;

    @RelatedTo(elementClass = Law.class, direction = Direction.OUTGOING)
    public Law getReplacesLaw() {
        return replacesLaw;
    }

    public void setReplacesLaw(Law replacesLaw) {
        this.replacesLaw = replacesLaw;
    }

    @RelatedTo(elementClass = TextElement.class, direction = Direction.OUTGOING)
    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    @RelatedTo(elementClass = Law.class, direction = Direction.OUTGOING)
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

    @RelatedTo(elementClass = LawDocumentPart.class, direction = Direction.OUTGOING)
    public List<LawDocumentPart> getChildren() {
        return children;
    }

    public void setChildren(List<LawDocumentPart> children) {
        this.children = children;
    }

    @RelatedTo(elementClass = LawDocumentPart.class, direction = Direction.INCOMING)
    public LawDocumentPart getParent() {
        return parent;
    }

    public void setParent(LawDocumentPart parent) {
        this.parent = parent;
    }

    @RelatedTo(elementClass = LawDocumentPart.class, direction = Direction.INCOMING)
    public LawDocumentPart getNextVersion() {
        return nextVersion;
    }

    public void setNextVersion(LawDocumentPart nextVersion) {
        this.nextVersion = nextVersion;
    }

    @RelatedTo(elementClass = LawDocumentPart.class, direction = Direction.OUTGOING)
    public LawDocumentPart getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(LawDocumentPart previousVersion) {
        this.previousVersion = previousVersion;
    }

    @RelatedTo(elementClass = LawDocumentPart.class, direction = Direction.OUTGOING)
    public LawDocumentPart getTransitionalProvision() {
        return transitionalProvision;
    }

    public void setTransitionalProvision(LawDocumentPart transitionalProvision) {
        this.transitionalProvision = transitionalProvision;
    }

    public void addText(String string){
        this.textElement = new TextElement();
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

    public void addDocumentPartChild(LawDocumentPart lawDocumentPart) {
        getChildren().add(lawDocumentPart);
        lawDocumentPart.setParent(this);
        lawDocumentPart.setOrder(getChildren().size());
    }
}
