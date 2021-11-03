package com.scoperetail.fusion.util;

public class TestUtil {

    private static int GTIN_SIZE = 14;

    public static String getGtinFromUpc(String upc)
    {
        if (upc == null)
        {
            return null;
        }
        return padUpcToGtin14(upc + checkDigit(upc));
    }

    public static char checkDigit(String upc) {
        int sum = 0;
        for (int i = 0, position = upc.length(); i < upc.length(); i++, position--) {
            int n = upc.charAt(i) - '0';
            sum += n + (n + n) * (position & 1);
        }
        return (char) ('0' + ((10 - (sum % 10)) % 10));
    }

    public static String padUpcToGtin14(String upc)
    {
        if (upc.length() >= GTIN_SIZE)
        {
            return upc;
        }

        String s =  "00000000000000".substring(0, (GTIN_SIZE - upc.length())) + upc;
        return s;
    }
}
