package intern.server.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class CloundinaryUtils {

    public static String extractPublicId(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();

            String[] parts = path.split("/");
            if (parts.length > 0) {
                String publicIdWithExtension = parts[parts.length - 1];
                int dotIndex = publicIdWithExtension.lastIndexOf(".");
                if (dotIndex != -1) {
                    return publicIdWithExtension.substring(0, dotIndex);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
