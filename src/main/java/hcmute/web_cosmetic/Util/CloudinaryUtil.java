package hcmute.web_cosmetic.Util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class CloudinaryUtil {

    public static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "drncbsuo5",
            "api_key", "419211626138128",
            "api_secret", "616HgUjVrYJSj7beZaauCwSTAig"));

    public static String uploadImage(String base64Image,Long id) {
        try {
            byte[] imageBytes = Base64ImageUtil.getBytesFromBase64(base64Image);
            String tempFilePath = "image.png";
            saveBytesToFile(imageBytes, tempFilePath);

            Map uploadResult = cloudinary.uploader().upload(tempFilePath, ObjectUtils.asMap(
                    "public_id","brand_"+id,
                    "overwrite", true // Đè lên nếu public ID đã tồn tại
            ));

            String imageUrl = (String) uploadResult.get("url");

            deleteTempFile(tempFilePath);

            return imageUrl.substring(imageUrl.indexOf("upload/") + 7, imageUrl.lastIndexOf('.'));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveBytesToFile(byte[] bytes, String filePath) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bis);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);

        Files.write(Paths.get(filePath), bos.toByteArray());
    }

    private static void deleteTempFile(String filePath) throws IOException {
        Files.delete(Paths.get(filePath));
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
