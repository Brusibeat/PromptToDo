package org.academiadecodigo.hashtronauts.server.utils;

import java.util.zip.CRC32;

public class Utils {

    public static String getCRC32(String title){
        CRC32 crc32 = new CRC32();
        crc32.update(title.getBytes());

        return Long.toHexString( crc32.getValue() );
    }

    public static String padRight(String str, int n){
        return String.format("%1$-" + n + "s", str);
    }
}
