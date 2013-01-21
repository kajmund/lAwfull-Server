package lawscraper.server.scrapers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 1/15/12
 * Time: 9:49 PM
 */
public class Utilities {
    public static String trimText(String text) {
        text = text.replace("\t", "").replace("\n", "");
        if (onlyContainsSpaces(text)) {
            return "";
        }
        return text.trim();
    }

    private static boolean onlyContainsSpaces(String string) {
        int spaces = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                spaces++;
            }
        }
        if (spaces == string.length()) {
            return true;
        }
        return false;
    }

    public static String SHA1(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        md.update(text.getBytes(), 0, text.length());
        byte[] sha1hash = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < sha1hash.length; i++) {
            hexString.append(Integer.toHexString(0xFF & sha1hash[i]));
        }

        return hexString.toString();
    }
}
