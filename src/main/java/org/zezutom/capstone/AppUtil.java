package org.zezutom.capstone;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.http.MediaType;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");

    private AppUtil() {}

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.basic());
    }

}
