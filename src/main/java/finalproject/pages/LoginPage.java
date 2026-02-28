package finalproject.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.page;
import io.qameta.allure.Step;
import finalproject.data.User;
import com.codeborne.selenide.SelenideElement;

public class LoginPage {
    public static final String LOGIN_PAGE_URL = "https://qa-desk.stand.praktikum-services.ru/login";

    private SelenideElement emailInput() { return $("input[name='email']"); }
    private SelenideElement passwordInput() { return $("input[name='password']"); }
    private SelenideElement loginButton() { return $x("//button[text()='Войти']"); }
    static SelenideElement registerButton() { return $x("//button[text()='Нет аккаунта']"); }


    @Step("Авторизация пользователя")
    public MainPage loginUser(User user) {
        emailInput().setValue(user.getEmail());
        passwordInput().setValue(user.getPassword());
        loginButton().click();
        return page(MainPage.class);
    }

}