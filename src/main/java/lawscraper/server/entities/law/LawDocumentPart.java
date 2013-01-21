package lawscraper.server.entities.law;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.shared.DocumentPartType;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/21/12
 * Time: 9:58 AM
 */
@Entity
@Table(name = "lawDocumentPart")
public class LawDocumentPart extends DocumentPart {
    private Law belongsToLaw;
    private TextElement textElement = new TextElement();
    private Set<LawDocumentPart> childParts;
    private LawDocumentPart parent;

    LawDocumentPart transitionalProvision;
    private boolean deprecated;

    public void setChildParts(Set<LawDocumentPart> parts) {
        this.childParts = parts;
    }

    public void setParent(LawDocumentPart parent) {
        this.parent = parent;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<LawDocumentPart> getChildParts() {
        return childParts;
    }

    @Transient
    public List<LawDocumentPart> getSortedParts() {
        ArrayList<LawDocumentPart> lawDocumentParts = new ArrayList<LawDocumentPart>(getChildParts());
        Collections.sort(lawDocumentParts, new Comparator<LawDocumentPart>() {
            @Override
            public int compare(LawDocumentPart o, LawDocumentPart o1) {
                return o.getListOrder() - o1.getListOrder();
            }
        });
        return lawDocumentParts;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public LawDocumentPart getParent() {
        return parent;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public LawDocumentPart getTransitionalProvision() {
        return transitionalProvision;
    }

    public void setTransitionalProvision(LawDocumentPart transitionalProvision) {
        this.transitionalProvision = transitionalProvision;
    }

    public void addText(String string) {
        String encodedString = "";
        try {
            encodedString = new String(string.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        textElement.setText(encodedString);
    }

    public void setDeprecated(boolean b) {
        this.deprecated = b;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setLawPartType(DocumentPartType type) {
        setType(type.name());
    }

    @Transient
    public DocumentPartType getLawPartType() {
        return DocumentPartType.valueOf(getType());
    }

    public void addDocumentPartChild(LawDocumentPart subPart) {
        if (childParts == null) {
            childParts = new HashSet<LawDocumentPart>();
        }
        subPart.setParent(this);
        subPart.setListOrder(childParts.size());
        childParts.add(subPart);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Law getBelongsToLaw() {
        return belongsToLaw;
    }

    public void setBelongsToLaw(Law belongsToLaw) {
        this.belongsToLaw = belongsToLaw;
    }
}
