package test.com.github.evanquan.parsely.util;

import com.github.evanquan.parsely.util.FuncUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FuncUtilsTest {

    @Test
    public void nullabesEquals_empty_null_false() {
        assertFalse(FuncUtils.nullablesEqual("", null));
    }

    @Test
    public void nullabesEquals_null_empty_false() {
        assertFalse(FuncUtils.nullablesEqual(null, ""));
    }

    @Test
    public void nullablesEqual_empty_a_true() {
        assertFalse(FuncUtils.nullablesEqual("", "a"));
    }

    @Test
    public void nullablesEqual_empty_empty_true() {
        assertTrue(FuncUtils.nullablesEqual("", ""));
    }

    @Test
    public void nullablesEqual_null_null_true() {
        assertTrue(FuncUtils.nullablesEqual(null, null));
    }

}
