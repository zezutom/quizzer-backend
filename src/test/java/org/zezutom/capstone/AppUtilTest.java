package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import org.junit.Test;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.util.AppUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AppUtilTest {

    @Test
    public void randomInt() {
        final int range = 100;
        final int value = AppUtil.randomInt(range);
        assertTrue(value >= 0 && value < range);
    }

    @Test
    public void audit() {
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();

        assertNull(gameResult.getUsername());
        AppUtil.audit(user, gameResult);
        assertThat(gameResult.getUsername(), is(AppUtil.getUsername(user)));

    }

    @Test
    public void getUsername() {
        final User user = TestUtil.createUser();
        assertNotNull(AppUtil.getUsername(user));
    }
}
