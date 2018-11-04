package org.academiadecodigo.hashtronauts.server.utils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

public class Utils {

    public static String getCRC32(String title){
        CRC32 crc32 = new CRC32();
        crc32.update(title.getBytes());

        return Long.toHexString( crc32.getValue() );
    }

    public static String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm");

        return dateFormat.format(date).toString();
    }
}
