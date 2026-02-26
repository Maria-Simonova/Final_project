package finalproject.steps;

import finalproject.data.*;
import finalproject.pages.CreateAnnouncementPage;
import finalproject.pages.PersonalPage;
import finalproject.api.ApiClient;
import finalproject.pages.LoginPage;
import finalproject.pages.MainPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.ValidatableResponse;

public class CreateAnnouncementSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAnnouncementPage createAdPage = new CreateAnnouncementPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String accessToken;
    private Integer userId;
    private Announcement ad;
    private String savedEmail;
    private String savedPassword;


    @Given("Зарегистрированный")
    public void userIsRegistered() {
        user = UserGenerator.generateUser();
        ValidatableResponse registerResponse = apiClient.registerUser(user);
        savedEmail = user.getEmail();
        savedPassword = user.getPassword();
    }

    @And("Авторизованный пользователь")
    public void userIsLoggedIn() {
        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);
        loginPage.loginUser(registerUser);

        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        Hooks.setUserCredentials(accessToken, userId, apiClient);
    }

    @And("Открывает главную страницу")
    public void openStartPage() {
        mainPage.openMainPage();
        mainPage.clickCreateAnnouncementButton();
    }

    @When("Создает новое объявление")
    public void createNewAnnouncement() {
        ad = AnnouncementGenerator.generateAd();
        createAdPage.createAnnouncement(ad);
    }

    @Then("Объявление отображается в профиле пользователя")
    public void checkAnnouncementInProfile() {
        personalPage.openPersonalPage();
        personalPage.checkAnnouncementInProfile(ad.getName());
    }
}