package com.anxpp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {
    public static byte[] read(InputStream is) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = is.read(b)) != -1) {
            out.write(b, 0, len);
        }
        is.close();
        return out.toByteArray();
    }
}
