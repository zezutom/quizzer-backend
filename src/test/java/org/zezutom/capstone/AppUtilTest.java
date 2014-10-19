package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import org.junit.Test;
import org.zezutom.capstone.util.AppUtil;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AppUtilTest {

    @Test
    public void parseYear() {
        assertThat(AppUtil.parseYear("1970-01-01"), is(1970));
        assertThat(AppUtil.parseYear("2014-01-01"), is(2014));
        assertThat(AppUtil.parseYear("2001-12-31"), is(2001));
        assertNull(AppUtil.parseYear("31/12/2012"));
    }

    @Test
    public void parseRating() {
        assertThat(AppUtil.parseRating("6.2"), is(6.2d));
        assertThat(AppUtil.parseRating("10.12345"), is(10.12345d));
        assertThat(AppUtil.parseRating(".123"), is(0.123d));
        assertThat(AppUtil.parseRating("12345"), is(12345d));
        assertNull(AppUtil.parseRating("-4.3"));
    }

    @Test
    public void randomLong() {
        final int range = 100;
        final int value = AppUtil.randomInt(range);
        assertTrue(value >= 0 && value < range);
    }

    @Test
    public void getUsername() {
        assertNull(AppUtil.getUsername(null));
        assertThat(AppUtil.getUsername(createUser("user A")), is("user A"));
        assertThat(AppUtil.getUsername(createUser("user B")), is("user B"));
        assertThat(AppUtil.getUsername(createUser("user C")), is("user C"));
    }

    private User createUser(String username) {
        return new User("test@test.com", "", username);
    }
}
