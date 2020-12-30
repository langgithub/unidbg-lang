package com.lang.platform.xhs;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: SecurityUtil */
public class w {

    public static void main(String[] args) throws Exception {
        // 手机号加密
        System.out.println(w.a("[[\"15775691981\",\"15775691981\"]]","8C3264A3E83D0134D5427EC216FC93D4"));
    }

    /* renamed from: a  reason: collision with root package name */
    private static byte[] f22146a = null;

    /* renamed from: b  reason: collision with root package name */
    private static byte[] f22147b = {1, 2, 3, 4, 5, 6, 7, 8};

    /* renamed from: c  reason: collision with root package name */
    private static String f22148c = "abcdef1234567890";

    public static String a(String str, String str2) throws Exception {
        if (f22146a == null) {
            f22146a = f22148c.getBytes("UTF-8");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(f22146a);
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, ivParameterSpec);
        return b.a(instance.doFinal(str.getBytes()));
    }

    public static String a(String str, String str2, String str3) throws Exception {
        byte[] bytes = str3.getBytes("UTF-8");
        byte[] a2 = b.a(str);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, secretKeySpec, ivParameterSpec);
        return new String(instance.doFinal(a2));
    }
}