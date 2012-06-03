package lawscraper.server.service;

import lawscraper.server.components.LawRenderer;
import lawscraper.server.entities.law.Law;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/21/12
 * Time: 6:29 PM
 */

@Service("lawExporter")
public class LawExporterServiceImpl implements LawExporterService {


    LawService lawService = null;
    LawRenderer lawRenderer = null;

    @Autowired
    public LawExporterServiceImpl(LawService lawService, LawRenderer lawRenderer) {
        this.lawService = lawService;
        this.lawRenderer = lawRenderer;
    }

    @Override
    public void exportAllLawsToFiles() {
        for (Law law : lawService.findAll()) {
            exportLawToFile(law);
        }
    }

    private void exportLawToFile(Law law) {
        System.out.println("Exporting law: " + law.getTitle());
        String buffer = lawRenderer.renderToHtml(law);
        try {
            // Create file
            FileWriter fstream = new FileWriter("/tmp/lawexport/" + law.getFsNumber() + ".html");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(buffer);
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void main(String args[]) {
        this.exportAllLawsToFiles();
    }
}
