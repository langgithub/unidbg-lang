package com.lang.platform.xhs;

import java.io.Closeable;
import java.io.IOException;

@Deprecated
/* compiled from: IOUtil */
public class i {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}