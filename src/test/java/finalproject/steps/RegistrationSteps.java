package finalproject.steps;

import finalproject.api.*;
import finalproject.data.*;
import finalproject.pages.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;

public class RegistrationSteps {
    private final MainPage mainPage = new MainPage();
    private final RegistrationPage registrationPage = new RegistrationPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String accessToken;
    private String savedEmail;
    private Integer userId;

    @Given("Создание тестовых данных пользователя")
    public void createTestUserData() {
        user = UserGenerator.generateUser();
    }

    @Given("Пользователь уже был ранее зарегистрирован")
    public void getExistingTestUserData() {
        user = UserGenerator.generateUser();

        ValidatableResponse registerResponse = apiClient.registerUser(user);

        accessToken = apiClient.getAccessToken(registerResponse);
        userId = apiClient.getUserId(registerResponse);
        savedEmail = user.getEmail();

        Hooks.setUserCredentials(accessToken, userId, apiClient);
    }

    @When("Пользователь открывает главную страницу")
    public void openMainPage() {
        MainPage.openMainPage();
    }

    @When("Пользователь переходит на форму регистрации")
    public void openRegistrationPage() {
        registrationPage.openRegisterForm();
    }

    @When("Пользователь заполняет форму уникальным Email")
    public void fillRegistrationForm() {
        registrationPage.fillRegisterForm(user);
    }

    @When("Пользователь создает аккаунт")
    public void submitRegistration() {
        registrationPage.submitRegistration();

        ValidatableResponse response = apiClient.loginUser(user);
        accessToken = apiClient.getAccessToken(response);
        userId = apiClient.getUserId(response);
    }

    @When("Пользователь заполняет форму с ранее использованным Email")
    public void tryRegisterAgain() {
        User newUser = new User();
        newUser.setEmail(savedEmail);
        String newPassword = RandomUtils.randomPassword();
        newUser.setPassword(newPassword);
        newUser.setSubmitPassword(newPassword);

        registrationPage.fillRegisterForm(newUser);
    }

    @Then("Пользователь успешно регистрируется")
    public void checkSuccessfulRegistration() {
        registrationPage.isDefaultNameUserVisible();
    }

    @Then("Появляется сообщение об ошибке")
    public void checkErrorRegistration() {
        registrationPage.isErrorMessageVisible();
    }
}