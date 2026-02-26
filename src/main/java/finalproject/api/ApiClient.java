package finalproject.api;

import finalproject.data.User;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

import static io.restassured.filter.log.LogDetail.ALL;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import java.lang.RuntimeException;


public class ApiClient {
    private static final String BASE_URL = "https://qa-desk.stand.praktikum-services.ru";
    private static final String REGISTER_URL = "/api/signup";
    public static final String LOGIN_URL = "/api/signin";
    private static final String DELETE_USER_URL = "/api/delete-user/";

    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    public ApiClient() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(ALL)
                .build();

        this.responseSpec = new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }

    @Step("Регистрация пользователя")
    public ValidatableResponse registerUser(User user) {
        return given()
                .spec(requestSpec)
                .body(user)
                .post(REGISTER_URL)
                .then()
                .spec(responseSpec);
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
                .spec(requestSpec)
                .body(user)
                .post(LOGIN_URL)
                .then()
                .spec(responseSpec);
    }

    @Step("Получение accessToken")
    public String getAccessToken(ValidatableResponse response) {
        return response.extract().path("token.access_token");
    }

    @Step("Получение id пользователя")
    public Integer getUserId(ValidatableResponse response) {
        return response.extract().path("user.id");
    }

    @Step("Удаление пользователя по токену и id")
    public Response deleteUser(String accessToken, Integer userId) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .post(DELETE_USER_URL + userId)
                .then()
                .extract()
                .response();
    }

    @Step("Удаление пользователя после тестов")
    public void deleteUsersAfterTests(String accessToken, Integer userId) {

        if (accessToken != null && userId != null) {
            try {
                Response response = deleteUser(accessToken, userId);

                if (response.statusCode() == 200) {
                    System.out.println("Пользователь успешно удален");
                } else if (response.statusCode() == 401) {
                    System.out.println("Ошибка при удалении пользователя 401: " + response.jsonPath().getString("message"));
                }

            } catch (Exception e) {
                throw new RuntimeException("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }
}