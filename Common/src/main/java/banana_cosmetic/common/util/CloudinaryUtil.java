package banana_cosmetic.common.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;

public class CloudinaryUtil {

    public static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "bananacosmetic",
            "api_key", "419211626138128",
            "api_secret", "616HgUjVrYJSj7beZaauCwSTAig"));

    public static void uploadImage(String base64Image,Long id) {
        try {
            byte[] imageBytes = Base64ImageUtil.getBytes(base64Image);
            cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
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
