package org.hbrs.se2.project.coll.util;

import java.time.LocalDate;
import java.util.Arrays;

public class Utils {

    /**
     * Nützliche Methdode zur Erweiterung eines bestehendes Arrays
     * Oma hätte gesagt, so eine Methode 'fällt nicht durch' ;-)
     *
     * https://stackoverflow.com/questions/2843366/how-to-add-new-elements-to-an-array
     */
    public static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;

    }

    public static boolean stringIsEmptyOrNull(String value) {
        return value == null || value.equals("");
    }

    public static String convertToGermanDateFormat(LocalDate date) {
        String dateString = "";
        dateString += date.getDayOfMonth() +"." +  date.getMonthValue() + "."+  date.getYear();
        return dateString;
    }
}
