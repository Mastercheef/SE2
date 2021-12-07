package org.hbrs.se2.project.coll.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void appendLength() {
        Integer[] numberArray = {1, 2, 3};
        Integer[] numberArrayAppended = Utils.append(numberArray, 4);
        assertEquals(numberArray.length + 1, numberArrayAppended.length);
    }

    @Test
    void appendCheckObjectAppended() {

        Integer[] numberArray = {1, 2, 3};
        Integer[] numberArrayAppended = Utils.append(numberArray, 4);
        assertEquals(4, numberArrayAppended[numberArrayAppended.length - 1]);

    }

    @Test
    void stringIsEmpt() {
        String emptyString = "";
        String nullString = null;
        assertTrue(Utils.StringIsEmptyOrNull(emptyString));

    }
    @Test
    void stringIsNull() {
        String nullString = null;
        assertNull(nullString);
    }

    @Test
    void convertToGermanDateFormat() {
        LocalDate date1 = LocalDate.of(2001, 7, 9);
        assertEquals("9.7.2001", Utils.convertToGermanDateFormat(date1));
    }
}