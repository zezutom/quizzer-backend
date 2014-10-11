package org.zezutom.capstone;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.http.MediaType;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");

    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

    public static final String ANDROID_CLIENT_ID = "98684777677-b01sr36gsj0ej7pvvsndopepa01mg6du.apps.googleusercontent.com";

    public static final String ANDROID_AUDIENCE = ANDROID_CLIENT_ID;

    public static final String HTTP_POST = "post";

    private AppUtil() {}

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.basic());
    }

}
