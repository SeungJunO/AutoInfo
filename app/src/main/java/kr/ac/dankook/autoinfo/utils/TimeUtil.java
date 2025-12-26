// app/src/main/java/kr/ac/dankook/autoinfo/utils/TimeUtil.java
package kr.ac.dankook.autoinfo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static String nowString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
    public static long nowMillis(){ return System.currentTimeMillis(); }
}