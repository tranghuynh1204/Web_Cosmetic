package banana_cosmetic.common.util;

import java.util.Base64;

public class Base64ImageUtil {
    public static byte[] getBytes(String base64Image) {
        String base64String = base64Image.split(",")[1];
        return Base64.getDecoder().decode(base64String);
    }
}
