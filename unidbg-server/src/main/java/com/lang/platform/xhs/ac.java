package com.lang.platform.xhs;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;

/* compiled from: URLEncoder */
public class ac {

    /* renamed from: a  reason: collision with root package name */
    static BitSet f22125a = new BitSet(256);

    static {
        for (int i = 97; i <= 122; i++) {
            f22125a.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            f22125a.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            f22125a.set(i3);
        }
        f22125a.set(32);
        f22125a.set(45);
        f22125a.set(95);
        f22125a.set(46);
    }

    public static String a(String str, String str2) throws UnsupportedEncodingException {
        BitSet bitSet;
        int i;
        char charAt;
        StringBuffer stringBuffer = new StringBuffer(str.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        if (str2 == null) {
            throw new NullPointerException("charsetName");
        }
        try {
            Charset forName = Charset.forName(str2);
            int i2 = 0;
            boolean z = false;
            while (i2 < str.length()) {
                char charAt2 = str.charAt(i2);
                if (f22125a.get(charAt2)) {
                    if (charAt2 == ' ') {
                        charAt2 = '+';
                        z = true;
                    }
                    stringBuffer.append((char) charAt2);
                    i2++;
                } else {
                    do {
                        charArrayWriter.write(charAt2);
                        if (charAt2 >= 55296 && charAt2 <= 56319 && (i = i2 + 1) < str.length() && (charAt = str.charAt(i)) >= 56320 && charAt <= 57343) {
                            charArrayWriter.write(charAt);
                            i2 = i;
                        }
                        i2++;
                        if (i2 >= str.length()) {
                            break;
                        }
                        bitSet = f22125a;
                        charAt2 = str.charAt(i2);
                    } while (!bitSet.get(charAt2));
                    charArrayWriter.flush();
                    byte[] bytes = new String(charArrayWriter.toCharArray()).getBytes(forName);
                    for (int i3 = 0; i3 < bytes.length; i3++) {
                        stringBuffer.append('%');
                        char forDigit = Character.forDigit((bytes[i3] >> 4) & 15, 16);
                        if (Character.isLetter(forDigit)) {
                            forDigit = (char) (forDigit - ' ');
                        }
                        stringBuffer.append(forDigit);
                        char forDigit2 = Character.forDigit(bytes[i3] & 15, 16);
                        if (Character.isLetter(forDigit2)) {
                            forDigit2 = (char) (forDigit2 - ' ');
                        }
                        stringBuffer.append(forDigit2);
                    }
                    charArrayWriter.reset();
                    z = true;
                }
            }
            if (z) {
                return stringBuffer.toString();
            }
            return str;
        } catch (IllegalCharsetNameException unused) {
            throw new UnsupportedEncodingException(str2);
        } catch (UnsupportedCharsetException unused2) {
            throw new UnsupportedEncodingException(str2);
        }
    }
}