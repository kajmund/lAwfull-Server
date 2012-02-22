package lawscraper.server.service;

import lawscraper.server.entities.law.Law;

/**
 * Renders a law into a HTML fragment.
 */
public interface LawRenderer {
    
    String renderToHtml(Law law);
    
}
