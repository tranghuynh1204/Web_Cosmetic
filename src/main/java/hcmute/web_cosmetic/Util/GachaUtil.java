package hcmute.web_cosmetic.Util;

import java.time.LocalTime;

public class GachaUtil {

    public static int gachaNumber() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.getHour() * 3600 + currentTime.getMinute() * 60 + currentTime.getSecond();
    }
}
