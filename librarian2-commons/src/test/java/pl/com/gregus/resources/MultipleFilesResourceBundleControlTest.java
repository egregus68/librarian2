/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Grzegorz Guściora
 */
public class MultipleFilesResourceBundleControlTest {
    
    public MultipleFilesResourceBundleControlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of newBundle method, of class MultipleFilesResourceBundleControl.
     */
    @Test
    public void testNewBundle() throws Exception {
        System.out.println("newBundle");
        String baseName = "messages";
        Locale locale = Locale.ROOT;
        String format = "";
        ClassLoader loader = null;
        boolean reload = false;
        MultipleFilesResourceBundleControl instance = new MultipleFilesResourceBundleControl("properties");
        ResourceBundle result = instance.newBundle(baseName, locale, format, loader, reload);
        assertTrue(result.containsKey("test"));
        assertEquals(result.getString("test"), "TEST text");
    }
    
    @Test
    public void testNewBundleFalse() throws Exception {
        System.out.println("newBundleFalse");
        String baseName = "messages_test";
        Locale locale = Locale.ROOT;
        String format = "";
        ClassLoader loader = null;
        boolean reload = false;
        MultipleFilesResourceBundleControl instance = new MultipleFilesResourceBundleControl("properties");
        ResourceBundle result = instance.newBundle(baseName, locale, format, loader, reload);
        assertNull(result);
    }
}