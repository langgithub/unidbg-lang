package com.jingdong.common.utils;

import android.app.Application;
import android.content.Context;

public class BitmapkitUtils {
    public static final String API_KEY = "XJgK2J9rXdmAH37ilm";
    private static final int RETRY_TIMES = 3;
    private static final String TAG = "BitmapkitUtils";

    /* renamed from: a  reason: collision with root package name */
    public static Application a;

    /* renamed from: b  reason: collision with root package name */
    private static boolean f5244b;
    public static boolean isBMPLoad;

    public static native String a(String... strArr);

    public static native byte[] encodeJni(byte[] bArr, boolean z);

    public static native String getSignFromJni(Context context, String str, String str2, String str3, String str4, String str5);

    public static native String getstring(String str);

}