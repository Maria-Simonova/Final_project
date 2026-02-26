package finalproject.pages;

import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import java.time.Duration;

public class MainPage {
    public static final String MAIN_PAGE_URL = "https://qa-desk.stand.praktikum-services.ru/";

    static SelenideElement loginAndRegisterButton() { return $x("//button[text()='Вход и регистрация']"); }
    private SelenideElement profileButton() { return $("button.circleSmall"); }
    private SelenideElement userNameLabel() { return $(".profileText.name"); }
    private SelenideElement logoutButton() { return $x("//button[text()='Выйти']"); }
    private SelenideElement createAnnouncementButton() { return $x("//button[text()='Разместить объявление']"); }

    @Step("Открытие главной страницы")
    public static MainPage openMainPage() {
        open(MAIN_PAGE_URL);
        return new MainPage();
    }

    @Step("Получение имени пользователя")
    public String getUserName() {
        return userNameLabel().getText();
    }

    @Step("Переход к форме авторизации")
    public LoginPage clickLoginAndRegisterButton() {
        loginAndRegisterButton().shouldBe(visible).click();
        return page(LoginPage.class);
    }

    @Step("Выход из системы")
    public MainPage clickLogoutButton() {
        SelenideElement logout = logoutButton();
        SelenideElement login = loginAndRegisterButton();

        logout.shouldBe(visible, Duration.ofSeconds(10)).shouldBe(enabled);
        logout.click();
        login.shouldBe(visible, Duration.ofSeconds(10));

        return page(MainPage.class);
    }

    @Step("Проверка успешной авторизации")
    public MainPage succsessAuthorized() {
        profileButton().shouldBe(visible, Duration.ofSeconds(15));
        userNameLabel().shouldBe(visible);
        logoutButton().shouldBe(visible);
        return this;
    }

    public PersonalPage clickProfileButton() {
        profileButton().click();
        return page(PersonalPage.class);
    }

    @Step("Открытие страницы создания объявления")
    public CreateAnnouncementPage clickCreateAnnouncementButton() {
        createAnnouncementButton().shouldBe(visible, Duration.ofSeconds(15)).click();
        return page(CreateAnnouncementPage.class);
    }
}