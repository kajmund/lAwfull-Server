package lawscraper.server.service;

import lawscraper.server.components.renderers.lawrenderer.LawRenderer;
import lawscraper.server.entities.law.Law;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 10:49 AM
 */
public class LawWrapper {
    Law law;
    LawRenderer lawRenderer;

    public LawWrapper() {
    }

    public LawWrapper(Law law, LawRenderer lawRenderer) {
        this.law = law;
        this.lawRenderer = lawRenderer;
    }

    public Long getId() {
        return law.getId();
    }

    public String getTitle() {
        return law.getTitle();
    }
}
