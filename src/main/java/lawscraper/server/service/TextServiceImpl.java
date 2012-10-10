package lawscraper.server.service;

import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.repositories.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/22/12
 * Time: 10:11 AM
 */

@Service("textService")
public class TextServiceImpl implements TextService {
    RepositoryBase<TextElement> repository = null;

    @Autowired
    public TextServiceImpl(@Qualifier("repositoryBaseImpl") RepositoryBase<TextElement> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TextElement getTextElement(TextElement te) {

        //Discontinued - findbypropertyvalue always returns null and the method is very slow
        //TextElement textElement = repository.findByPropertyValue("hash", te.getHash());
        TextElement textElement = null;
        if (te == null) {
            textElement = repository.save(te);
        }
        return textElement;
    }

}
