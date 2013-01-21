package lawscraper.server.entities.superclasses.Document;

import lawscraper.server.entities.superclasses.EntityBase;
import lawscraper.shared.DocumentPartType;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/21/12
 * Time: 9:48 AM
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "documentPart")
/*
@org.hibernate.annotations.Table(appliesTo = "documentPart",
                                 indexes = {@Index(name = "docIndex", columnNames = {"documentKey"})})
*/
public class DocumentPart extends EntityBase {
    private Integer listOrder = 0;
    private String type;
    private String key;
    private String title = "";
    private String description;
    private List<DocumentPart> documentReferenceList = new ArrayList<DocumentPart>();

    public DocumentPart(
            String title, String documentKey, DocumentPartType documentPartType) {
        setTitle(title);
        setKey(documentKey);
        setType(documentPartType.toString());
    }

    public DocumentPart() {
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addDocumentReference(DocumentPart documentPart) {
        documentPart.getDocumentReferenceList().add(this);
        this.getDocumentReferenceList().add(documentPart);
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    public List<DocumentPart> getDocumentReferenceList() {
        return documentReferenceList;
    }

    public void setDocumentReferenceList(List<DocumentPart> documentReferenceList) {
        this.documentReferenceList = documentReferenceList;
    }

    @Index(name = "titleIndex")
    @Column(nullable = false, length = 2048)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false, unique = true)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        if (listOrder == null) {
            this.listOrder = 0;
        } else {
            this.listOrder = listOrder;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
