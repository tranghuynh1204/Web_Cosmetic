package banana_cosmetic.common.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {

    public static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "bananacosmetic",
            "api_key", "419211626138128",
            "api_secret", "616HgUjVrYJSj7beZaauCwSTAig"));

    public static void uploadImage(String base64Image, String id) {
        try {
            byte[] imageBytes = Base64ImageUtil.getBytes(base64Image);
            cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                    "public_id", id,
                    "overwrite", true,
                    "format", "png",
                    "invalidate", true
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String uploadImage(String base64Image) {
        try {
            byte[] imageBytes = Base64ImageUtil.getBytes(base64Image);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                    "format", "png",
                    "invalidate", true
            ));
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
