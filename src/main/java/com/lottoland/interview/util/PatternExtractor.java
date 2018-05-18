package com.lottoland.interview.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternExtractor {

    public static String extract(String message, String pattern) {

        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        } else
            return null;
    }
}
