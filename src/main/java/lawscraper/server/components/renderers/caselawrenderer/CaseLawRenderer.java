package lawscraper.server.components.renderers.caselawrenderer;

import lawscraper.server.entities.caselaw.CaseLaw;

/**
 * Renders a law into a HTML fragment.
 */
public interface CaseLawRenderer {
    
    String renderToHtml(CaseLaw law);
    
}
