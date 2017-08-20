package com.example.aadithyavarma.weatheria;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesterClassTest {

    TesterClass testerClass;

    /**
     * Initialize the tester class object before executing;
     */
    @Before
    public void setTesterClass(){
        testerClass = new TesterClass();
    }

    /**
     * Validation for correct location name;
     * @throws Exception
     */
    @Test
    public void validLocation() throws Exception {
        assertTrue(testerClass.isValidLocation("New York"));
    }

    /**
     * Validation for incorrect location name;
     * @throws Exception
     */
    @Test
    public void invalidLocation() throws Exception {
        assertFalse(testerClass.isValidLocation("ff11"));
    }

    /**
     * Validation for correct name in database storing;
     * @throws Exception
     */
    @Test
    public void validName() throws Exception {
        assertTrue(testerClass.isValidName("ff f"));
    }

    /**
     * Validation for incorrect name in database storing;
     * @throws Exception
     */
    @Test
    public void invalidName() throws Exception {
        assertFalse(testerClass.isValidName("ff11"));
    }
}