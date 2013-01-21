package lawscraper.server.entities.superclasses.Document;

import lawscraper.server.entities.superclasses.EntityBase;
import lawscraper.server.scrapers.Utilities;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/21/12
 * Time: 11:05 AM
 */
@Entity
@Table(name = "textElement")
public class TextElement extends EntityBase {
    String text = "";
    String hash = "";

    public TextElement(String text) {
        setText(text);
    }

    public TextElement() {
    }

    @Lob
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        String tmpHash = Utilities.SHA1(text);

        /* use text as hash if sha1 returns "" */
        if (tmpHash.equals("")) {
            tmpHash = text;
        }
        this.setHash(tmpHash);
    }

    @Index(name = "hashTextElementIndex")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
