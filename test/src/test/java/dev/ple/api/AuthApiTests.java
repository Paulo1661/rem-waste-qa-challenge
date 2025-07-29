package dev.ple.api;

import dev.ple.screenplay.tasks.api.Authenticate;
import io.restassured.response.Response;
//import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;

//@ExtendWith(SerenityJUnit5Extension.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthApiTests {
    private static final String BASE_URL = "http://localhost:3000/api";
    private static final Actor actor = Actor.named("api-tester");


//    @BeforeAll
//    static void prepare() {
//        actor.whoCan(CallAnApi.at(BASE_URL));
//    }

//    @Test
//    @Order(1)
//    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully() {
        //When
        actor.attemptsTo(Authenticate.withCredentials("paul@example.com", "1234"));

        //Then
        Response response = SerenityRest.lastResponse();
        actor.attemptsTo(Ensure.that(response.getStatusCode()).isEqualTo(200));
        actor.attemptsTo(Ensure.that(response.getBody().jsonPath().getString("token")).isNotBlank());
    }

//    @Test
//    @Order(2)
    void shouldFailLoginWithInvalidPassword() {
        //When
        actor.attemptsTo(Authenticate.withCredentials("paul@example.com", "wrongpass"));

        //Then
        Response response = SerenityRest.lastResponse();
        actor.attemptsTo(Ensure.that(response.getStatusCode()).isEqualTo(401));
        actor.attemptsTo(Ensure.that(response.getBody().jsonPath().getString("Invalid credentials")).isNotBlank());
    }
}
