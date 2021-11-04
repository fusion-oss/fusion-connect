package com.scoperetail.fusion.util;

/*-
 * *****
 * fusion-connect
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

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
