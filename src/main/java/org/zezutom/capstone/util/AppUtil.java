package org.zezutom.capstone.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tom on 05/10/2014.
 */
public class AppUtil {

    public static final String API_VERSION = "v1";

    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static final String RATING_REGEX = "^\\d*\\.?\\d+$";

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");

    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

    public static final String ANDROID_CLIENT_ID = "98684777677-b01sr36gsj0ej7pvvsndopepa01mg6du.apps.googleusercontent.com";

    public static final String ANDROID_AUDIENCE = ANDROID_CLIENT_ID;

    public static final String HTTP_POST = "post";

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

    public static long randomLong(Long range) {
        return (long)(RANDOM.nextDouble() * range) + 1;
    }

    private static boolean validate(String value, String regex) {
        return value != null && value.matches(regex);
    }

}
