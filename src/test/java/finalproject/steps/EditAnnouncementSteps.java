package finalproject.steps;

import finalproject.data.*;
import finalproject.pages.*;
import finalproject.api.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.ValidatableResponse;

public class EditAnnouncementSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAnnouncementPage createAdPage = new CreateAnnouncementPage();
    private final EditAnnouncementPage editAdPage = new EditAnnouncementPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private Announcement originalAd;
    private Announcement editedAd;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;
    private String createdAdTitle;

    @Given("Пользователь авторизовался и зашел на портал")
    public void userRegisterAndAuthEnterProfile() {
        user = UserGenerator.generateUser();
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

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

        mainPage.openMainPage();
        mainPage.clickCreateAnnouncementButton();
    }

    @And("У пользователя есть ранее созданное объявление")
    public void userHaveAd() {
        originalAd = AnnouncementGenerator.generateAd();
        createAdPage.createAnnouncement(originalAd);

        createdAdTitle = originalAd.getName();

        personalPage.openPersonalPage();
    }

    @When("Пользователь открывает объявление для редактирования")
    public void openAdForEdit() {
        personalPage.clickEditFirstAnnouncement();
        editAdPage.shouldHaveTitle(createdAdTitle);
    }

    @And("Пользователь вносит изменения в объявление")
    public void userEditAd() {
        editedAd = AnnouncementGenerator.generateAd();
        editAdPage.editAnnouncement(editedAd);
    }

    @Then("Внесенные изменения сохраняются")
    public void userEditsSaved() {
        personalPage.openPersonalPage();
        personalPage.checkAnnouncementInProfile(editedAd.getName());
    }
}