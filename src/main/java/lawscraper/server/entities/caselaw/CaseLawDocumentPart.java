package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.shared.DocumentPartType;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/13/12
 * Time: 5:40 PM
 */
@Entity
@Table(name = "caseLawDocumentPart")
@org.hibernate.annotations.Table(appliesTo = "caseLawDocumentPart", indexes = {@Index(name="caseLawDocIndex", columnNames = {"documentKey"})})

public class CaseLawDocumentPart extends DocumentPart {
    private CaseLaw belongsToCaseLaw;
    private TextElement textElement = new TextElement();
    Set<CaseLawDocumentPart> parts = new HashSet<CaseLawDocumentPart>();
    private DocumentPartType partType;

    public CaseLawDocumentPart() {
        this.setPartType(DocumentPartType.CASELAW_SECTION);
    }

    @ManyToOne
    public CaseLaw getBelongsToCaseLaw() {
        return belongsToCaseLaw;
    }

    public void setBelongsToCaseLaw(CaseLaw belongsToCaseLaw) {
        this.belongsToCaseLaw = belongsToCaseLaw;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    @OneToMany(mappedBy = "belongsToCaseLaw",cascade = CascadeType.ALL)
    public Set<CaseLawDocumentPart> getParts() {
        return parts;
    }

    public void setParts(Set<CaseLawDocumentPart> parts) {
        this.parts = parts;
    }

    @Transient
    public List<CaseLawDocumentPart> getSortedParts() {
        ArrayList<CaseLawDocumentPart> caseLawDocumentParts = new ArrayList<CaseLawDocumentPart>(getParts());
        Collections.sort(caseLawDocumentParts, new Comparator<CaseLawDocumentPart>() {
            @Override
            public int compare(CaseLawDocumentPart o, CaseLawDocumentPart o1) {
                return o.getListOrder() - o1.getListOrder();
            }
        });
        return caseLawDocumentParts;
    }

    @Column(nullable = false, length = 20)
    public DocumentPartType getPartType() {
        return partType;
    }

    public void setPartType(DocumentPartType partType) {
        this.partType = partType;
    }

}
