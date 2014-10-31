package org.zezutom.capstone.util;

import com.google.appengine.api.users.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

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

    public static Integer parseYear(String date) {
        date = sanitize(date);
        if (!validate(date, DATE_REGEX)) return null;
        else return Integer.valueOf(date.trim().split("-")[0]);
    }

    public static Double parseRating(String rating) {
        rating = sanitize(rating);
        if (!validate(rating, RATING_REGEX)) return null;
        else return Double.parseDouble(rating);
    }

    public static int randomInt(int range) {
        return RANDOM.nextInt(range);
    }

    public static String getUsername(User user) {
        return user == null ? null : user.getUserId();
    }

    private static boolean validate(String value, String regex) {
        return value != null && value.matches(regex);
    }


}
