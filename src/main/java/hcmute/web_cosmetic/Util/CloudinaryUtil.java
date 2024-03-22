package hcmute.web_cosmetic.Util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {

    public static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "drncbsuo5",
            "api_key", "419211626138128",
            "api_secret", "616HgUjVrYJSj7beZaauCwSTAig"));

    public static void uploadImage(String base64Image,Long id) {
        try {
            byte[] imageBytes = Base64ImageUtil.getBytesFromBase64(base64Image);
            Map uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                    "public_id","brand_"+id,
                    "overwrite", true,
                    "format", "png",
                    "invalidate", true
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteImage(String imageUrl) {
        try {
            String publicId = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
