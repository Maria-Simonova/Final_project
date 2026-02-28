package finalproject.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import finalproject.data.Announcement;
import java.io.File;
import java.time.Duration;

public class CreateAnnouncementPage {

    private SelenideElement nameInput() {return $("input[name='name']");}
    private SelenideElement descriptionText() {return $("textarea[name='description']");}
    private SelenideElement priceInput() {return $("input[name='price']");}
    private SelenideElement publishButton() {return $x("//button[text()='Опубликовать']");}
    public SelenideElement uploadPhotoButton() { return $("input[type=file]");}

    @Step("Создание объявления")
    public MainPage createAnnouncement(Announcement generateAd) {
        uploadPhotoButton().uploadFile(new File(generateAd.getPhotoPath()));
        nameInput().setValue(generateAd.getName());
        descriptionText().setValue(generateAd.getDescription());
        priceInput().setValue(String.valueOf(generateAd.getPrice()));

        publishButton().shouldBe(visible, Duration.ofSeconds(10)).click();
        return page(MainPage.class);
    }
}