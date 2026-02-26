package finalproject.data;

import java.io.File;

public class AnnouncementGenerator {

    private static final String DEFAULT_IMAGE_PATH = "src/test/resources/images/images.jpeg";

    // Генерация объявления
    public static Announcement generateAd() {
        Announcement generateAd = new Announcement();
        generateAd.setName(RandomUtils.randomName());
        generateAd.setDescription(RandomUtils.randomDescription());
        generateAd.setPrice(RandomUtils.randomPrice());
        String absolutePath = new File(DEFAULT_IMAGE_PATH).getAbsolutePath();

        if (new File(absolutePath).exists()) {
            generateAd.setPhotoPath(absolutePath);
        } else {
            throw new RuntimeException("Файл изображения не найден по пути: " + absolutePath);
        }
        return generateAd;
    }
}