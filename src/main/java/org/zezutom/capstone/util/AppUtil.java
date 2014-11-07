package org.zezutom.capstone.util;

import com.google.appengine.api.users.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.zezutom.capstone.domain.AuditableEntity;

import java.util.Random;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    public static final String API_VERSION = "v1";

    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static final String RATING_REGEX = "^\\d*\\.?\\d+$";

    private static final Random RANDOM = new Random();

    private AppUtil() {}

    public static String sanitize(String input) {
        return Jsoup.clean(input, Whitelist.basic());
    }

    public static void audit(User user, AuditableEntity entity) {
        entity.setUsername(getUsername(user));
    }

    public static String getUsername(User user) {
        return user.getEmail();
    }

    public static int randomInt(int range) {
        return RANDOM.nextInt(range);
    }

    private static boolean validate(String value, String regex) {
        return value != null && value.matches(regex);
    }


}
