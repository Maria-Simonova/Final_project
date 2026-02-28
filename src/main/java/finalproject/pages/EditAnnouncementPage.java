package finalproject.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import finalproject.data.Announcement;
import java.io.File;

public class EditAnnouncementPage {

    private SelenideElement nameInput() {return $("input[name='name']");}
    private SelenideElement descriptionText() {return $("textarea[name='description']");}
    private SelenideElement priceInput() {return $("input[name='price']");}
    private SelenideElement publishButton() {return $x("//button[text()='Опубликовать']");}
    public SelenideElement uploadPhotoButton() { return $("input[type=file]");}
    private SelenideElement titleLabel() {return $(".createListing_title__IFtFs");}

    @Step("Редактирование объявления")
    public MainPage editAnnouncement(Announcement generateAd) {
        uploadPhotoButton().uploadFile(new File(generateAd.getPhotoPath()));
        nameInput().setValue(generateAd.getName());
        descriptionText().setValue(generateAd.getDescription());
        priceInput().setValue(String.valueOf(generateAd.getPrice()));

        publishButton().click();
        return page(MainPage.class);
    }
    // Баг приложения: при редактировании открывается «Новое объявление» вместо формы редактирования. Тест падает из-за этого.
    @Step("Проверка заголовка редактируемого объявления")
    public EditAnnouncementPage shouldHaveTitle(String expectedTitle) {
        titleLabel().shouldBe(visible);
        String actualTitle = titleLabel().getText().trim();
        String errorMessage = String.format(
                "Неверное название объявления. Ожидалось: '%s', но получено: '%s'",
                expectedTitle,
                actualTitle
        );
        if (!actualTitle.equals(expectedTitle)) {
            throw new AssertionError(errorMessage);
        }
        return this;
    }
}