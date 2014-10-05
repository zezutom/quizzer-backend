package org.zezutom.capstone;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    private AppUtil() {}

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.basic());
    }
}
