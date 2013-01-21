package lawscraper.server.scrapers;

import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.service.TextService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/22/12
 * Time: 9:17 AM
 */
public class TextServiceDummyImpl implements TextService {
    //Is SHA-1 collision a problem?
    Map<String, TextElement> hashTable = new HashMap<String, TextElement>();
    int duplication = 0;
    int duplicationCharacters = 0;
    
    @Override
    public TextElement getTextElement(TextElement te) {
        TextElement textElement = hashTable.get(te.getHash());
        if(textElement == null){
            hashTable.put(te.getHash(), te);
            textElement = te;
        }else {
            duplication++;
            duplicationCharacters+=textElement.getText().length();
        }

        return textElement;
    }

}
