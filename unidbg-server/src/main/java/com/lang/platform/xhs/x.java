package com.lang.platform.xhs;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/* compiled from: StringUtil */
public class x {

    /* renamed from: a  reason: collision with root package name */
    private static int f22149a = 4096;

    /* renamed from: b  reason: collision with root package name */
    private static final byte[] f22150b = new byte[0];

    /* renamed from: c  reason: collision with root package name */
    private static final char[] f22151c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(InputStream inputStream) {
        return new String(b(inputStream));
    }

    private static byte[] b(InputStream inputStream) {
        if (inputStream == null) {
            return f22150b;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[f22149a];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException e2) {
                Log.e("StringUtil", e2.getMessage());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }


    private static void b(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            parentFile.mkdir();
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b2 = bArr[i];
            int i2 = i * 2;
            cArr[i2] = f22151c[(b2 >>> 4) & 15];
            cArr[i2 + 1] = f22151c[b2 & 15];
        }
        return new String(cArr);
    }


    public static String b(int i) {
        if (i > 99999) {
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String format = decimalFormat.format(((double) i) / 1000.0d);
            if (format.endsWith(".0")) {
                format = format.substring(0, format.length() - 2);
            }
            return format + "k";
        }
        return i + "";
    }

    public static CharSequence a(TextView textView, CharSequence charSequence, int i, String str, boolean z) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        if (!z && !a(textView, charSequence, i)) {
            return charSequence;
        }
        float measureText = textView.getPaint().measureText(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int i2 = 1;
        while (i2 <= i) {
            CharSequence ellipsize = TextUtils.ellipsize(charSequence.subSequence(spannableStringBuilder.length(), charSequence.length()), textView.getPaint(), i2 == i ? ((float) textView.getWidth()) - measureText : (float) textView.getWidth(), TextUtils.TruncateAt.END);
            if (!TextUtils.isEmpty(ellipsize)) {
                if (i2 != i) {
                    spannableStringBuilder.append(charSequence.subSequence(spannableStringBuilder.length(), ellipsize.length() - 5));
                } else {
                    spannableStringBuilder.append(ellipsize);
                }
            }
            i2++;
        }
        if (charSequence instanceof Spanned) {
            TextUtils.copySpansFrom((Spanned) charSequence, 0, spannableStringBuilder.length(), null, spannableStringBuilder, 0);
        }
        if (spannableStringBuilder.toString().endsWith("…") || z) {
            return spannableStringBuilder.append((CharSequence) str);
        }
        return charSequence;
    }

    public static boolean a(TextView textView, CharSequence charSequence, int i) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        if (((float) (textView.getMeasuredWidth() * i)) < textView.getPaint().measureText(charSequence.toString())) {
            return true;
        }
        return false;
    }

    public static Boolean b(String str) {
        return Boolean.valueOf(!TextUtils.isEmpty(str) && (str.matches("[\\d.]+") || str.matches("[\\d]+")));
    }
}