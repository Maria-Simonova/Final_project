package finalproject.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;
import finalproject.data.*;
import finalproject.pages.*;
import finalproject.api.ApiClient;
import io.restassured.response.ValidatableResponse;

public class DeleteAnnouncementSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAnnouncementPage createAdPage = new CreateAnnouncementPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private Announcement ad;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;

    @Given("Пользователь авторизован и находится в личном кабинете")
    public void userIsLoggedInAndInPersonalProfile() {
        user = UserGenerator.generateUser();
        apiClient.registerUser(user);

        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

        MainPage.openMainPage();
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

    @And("У пользователя есть объявление предназначенное для удаления")
    public void userHasAnnouncementToDelete() {
        MainPage.openMainPage();
        mainPage.clickCreateAnnouncementButton();

        ad = AnnouncementGenerator.generateAd();
        createAdPage.createAnnouncement(ad);

        PersonalPage.openPersonalPage();
    }

    @When("Пользователь нажимает на кнопку удаления объявления")
    public void userTriesToDeleteAnnouncement() {
        if (personalPage.deleteButton().isDisplayed()) {
            personalPage.deleteButton().click();
        } else {
            throw new AssertionError("Кнопка удаления объявления отсутствует");
        }
    }

    @Then("Объявление удалено")
    public void announcementShouldBeDeleted() {
        PersonalPage.openPersonalPage();

        Assertions.assertFalse(
                personalPage.firstAdCard().isDisplayed(),
                String.format(
                        "Ошибка: объявление с названием \"%s\" не было удалено!",
                        ad.getName()
                )
        );
    }
}