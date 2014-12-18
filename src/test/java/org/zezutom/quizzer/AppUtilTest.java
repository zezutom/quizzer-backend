package org.zezutom.quizzer;

import com.google.appengine.api.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zezutom.quizzer.util.AppUtil;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

public class AppUtilTest {

    @Before
    public void setUp() {
        TestUtil.login();
    }

    @After
    public void tearDown() {
        TestUtil.logout();
    }

    @Test
    public void randomInt() {
        final int range = 100;
        final int value = AppUtil.randomInt(range);
        assertTrue(value >= 0 && value < range);
    }

    @Test
    public void getUsername() {
        final User user = TestUtil.createUser();
        assertThat(AppUtil.getUserId(), is(user.getEmail()));
    }

    @Test
    public void sanitize() {
        final String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        final String sanitized = "<p><a href=\"http://example.com/\" rel=\"nofollow\">Link</a></p>";
        assertThat(AppUtil.sanitize(unsafe), is(sanitized));
    }
}
