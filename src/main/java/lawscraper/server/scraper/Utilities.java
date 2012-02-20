package lawscraper.server.scraper;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/15/12
 * Time: 9:49 PM
 */
public class Utilities {
    public static String trimText(String text){
        text = text.replace("\t", "").replace("\n", "");
        if(onlyContainsSpaces(text)){
            return "";
        }
        return text.trim();
    }

    private static boolean onlyContainsSpaces(String string) {
        int spaces = 0;
        for (int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == ' '){
                spaces++;
            }
        }
        if(spaces == string.length()){
            return true;
        }
        return false;
    }
}
