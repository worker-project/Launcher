package com.noideaindustry.jui;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class JuiUtils {
    public static double bytesFormater(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) return bytes;
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return (Math.round(value / 1024.0));
    }
}
