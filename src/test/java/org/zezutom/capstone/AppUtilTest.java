package org.zezutom.capstone;

import org.junit.Test;
import org.zezutom.capstone.util.AppUtil;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * Created by tom on 14/10/2014.
 */
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
        final long range = 100L;
        final long value = AppUtil.randomLong(range);
        assertTrue(value > 0 && value <= range);
    }

}
