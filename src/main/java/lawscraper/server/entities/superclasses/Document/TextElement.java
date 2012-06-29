package lawscraper.server.entities.superclasses.Document;

import lawscraper.server.entities.superclasses.EntityBase;
import lawscraper.server.scrapers.Utilities;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/21/12
 * Time: 11:05 AM
 */

public class TextElement extends EntityBase {
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "search")
    String text = "";

    @Indexed
    String hash = "";

    public TextElement(String text) {
        setText(text);
    }

    public TextElement() {

    }


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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
