package org.zezutom.quizzer.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Random;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    public static final String API_VERSION = "v3";

    private static final Random RANDOM = new Random();

    private AppUtil() {}

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.basic());
    }

    public static String getUserId() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return a == null ? null : ((OAuth2Authentication) a).getOAuth2Request().getClientId();
    }

    public static int randomInt(int range) {
        return RANDOM.nextInt(range);
    }
}
