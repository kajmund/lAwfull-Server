package lawscraper.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/16/12
 * Time: 9:33 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/applicationContext-security.xml"})
@TransactionConfiguration(defaultRollback = true)
public class LawExporterServiceTest {

    @Autowired
    LawExporterService lawExporterService;

    @Test
    public void testExporter() {
        lawExporterService.exportAllLawsToFiles();
    }
}
