package finalproject.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import finalproject.api.ApiClient;
import finalproject.data.User;
import finalproject.data.UserGenerator;
import finalproject.pages.LoginPage;
import finalproject.pages.MainPage;
import io.restassured.response.ValidatableResponse;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;

    @Given("Пользователь зарегистрирован")
    public void userIsRegistered() {
        user = UserGenerator.generateUser();
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        savedEmail = user.getEmail();
        savedPassword = user.getPassword();
    }

    @When("Пользователь открывает страницу входа")
    public void openLoginPage() {
        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
    }

    @And("Пользователь вводит свои учетные данные")
    public void enterCorrectCredentials() {
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);

        loginPage.loginUser(registerUser);

        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        Hooks.setUserCredentials(accessToken, userId, apiClient);
    }

    @Then("Пользователь успешно авторизуется")
    public void userSuccessfullyLoggedIn() {
        mainPage.succsessAuthorized();
    }
}