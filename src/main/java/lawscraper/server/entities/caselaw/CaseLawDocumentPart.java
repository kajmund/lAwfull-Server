package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.shared.DocumentPartType;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/13/12
 * Time: 5:40 PM
 */
public class CaseLawDocumentPart extends DocumentPart {
    @RelatedTo
    CaseLaw belongsToCaseLaw;

    @Fetch
    @RelatedTo(direction = Direction.INCOMING, type = "TEXT_ELEMENT")
    TextElement textElement = new TextElement();

    @Fetch
    @RelatedTo(elementClass = CaseLawDocumentPart.class, type = "HAS_SUB_PART")
    Set<CaseLawDocumentPart> parts = new HashSet<CaseLawDocumentPart>();
    private DocumentPartType partType;


    public CaseLaw getBelongsToCaseLaw() {
        return belongsToCaseLaw;
    }

    public void setBelongsToCaseLaw(CaseLaw belongsToCaseLaw) {
        this.belongsToCaseLaw = belongsToCaseLaw;
    }

    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    public Set<CaseLawDocumentPart> getParts() {
        return parts;
    }

    public void setParts(Set<CaseLawDocumentPart> parts) {
        this.parts = parts;
    }

    public List<CaseLawDocumentPart> getSortedParts() {
        ArrayList<CaseLawDocumentPart> caseLawDocumentParts = new ArrayList<CaseLawDocumentPart>(getParts());
        Collections.sort(caseLawDocumentParts, new Comparator<CaseLawDocumentPart>() {
            @Override
            public int compare(CaseLawDocumentPart o, CaseLawDocumentPart o1) {
                return o.getOrder() - o1.getOrder();
            }
        });
        return caseLawDocumentParts;
    }

    public DocumentPartType getPartType() {
        return partType;
    }

    public void setPartType(DocumentPartType partType) {
        this.partType = partType;
    }


}
