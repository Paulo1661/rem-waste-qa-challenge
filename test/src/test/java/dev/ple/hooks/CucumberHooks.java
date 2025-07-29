package dev.ple.hooks;

import io.cucumber.java.Before;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CucumberHooks {
    public final Actor actor = Actor.named("api-user").whoCan(CallAnApi.at("http://localhost:3000/api"));
    private final String authToken;

    public CucumberHooks() {
        String loginUser = """
            {
                      "email": "api@example.com",
                      "password": "4321"
            }
        """;
        actor.attemptsTo(
                Post.to("/auth/login")
                        .with(request ->
                                request.accept(ContentType.JSON)
                                        .contentType(ContentType.JSON)
                                        .body(loginUser))
        );
        actor.attemptsTo(Ensure.that(SerenityRest.lastResponse().getStatusCode()).isEqualTo(200));
        System.out.println(SerenityRest.lastResponse().getBody().prettyPrint());
        authToken = SerenityRest.lastResponse().getBody().jsonPath().getString("token");
    }

    @Before(value = "@UI and @EditUser")
    public void createUserForEdit() {
        String newUser = """
            {
                "name": "Jane",
                "email": "jane@example.com"
            }
        """;
        createUser(newUser);
    }

    @Before(value = "@UI and @DeleteUser")
    public void createUserForDelete() {
        String newUser = """
            {
                "name": "Rachid",
                "email": "rachid@example.com"
            }
        """;
        createUser(newUser);
    }

    private void createUser(String newUserJson) {
        actor.attemptsTo(
                Post.to("/users")
                        .with(
                                request ->
                                        request.accept(ContentType.JSON)
                                                .contentType(ContentType.JSON)
                                                .header("Authorization", "Bearer %s".formatted(authToken))
                                                .body(newUserJson)
                        )

        );
        actor.attemptsTo(Ensure.that(SerenityRest.lastResponse().getStatusCode()).isEqualTo(201));
    }

    @Before("@API and ( @ReadUsers or @UpdateUser or @DeleteUser )")
    //@Before("@ReadUser or @UpdateUser or @DeleteUser")
    public void cleanDBForAPITest() {
        actor.attemptsTo(
                Delete.from("/users")
                        .with(
                                request ->
                                        request.header("Authorization", "Bearer %s".formatted(authToken))
                        )

        );
        actor.attemptsTo(Ensure.that(SerenityRest.lastResponse().getStatusCode()).isEqualTo(200));
    }
}
