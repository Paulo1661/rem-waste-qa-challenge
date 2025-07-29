package dev.ple.stepDefinitions;

import dev.ple.model.User;
import dev.ple.screenplay.tasks.api.Authenticate;
import dev.ple.utils.SerenityConf;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class UserAPIStepDefinitions {

    private final SerenityConf conf = SerenityConf.getInstance();
    private final Logger LOGGER = LoggerFactory.getLogger(UserAPIStepDefinitions.class);
    private String authToken;

    @Given("{word} is authenticated")
    public void actorIsAuthenticated(String actorName) {
        Actor actor = theActorCalled(actorName);
        actor.attemptsTo(Authenticate.withCredentials("paul@example.com", "1234"));
        authToken = SerenityRest.lastResponse().getBody().jsonPath().getString("token");
    }

    @When("{word} sends a POST request to {string} with JSON body:")
    public void actorSendsAPOSTRequestToWithJSONBody(String actorName, String authResource, String jsonBody) {
        if (authResource.contains("login"))
            theActorCalled(actorName).attemptsTo(
                    Post.to(authResource)
                            .with(request ->
                                    request.accept(ContentType.JSON)
                                            .contentType(ContentType.JSON)
                                            .body(jsonBody))
            );
        else
            theActorCalled(actorName).attemptsTo(
                    Post.to(authResource)
                            .with(request ->
                                    request.accept(ContentType.JSON)
                                            .contentType(ContentType.JSON)
                                            .header("Authorization", "Bearer %s".formatted(authToken))
                                            .body(jsonBody))
            );
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(SerenityRest.lastResponse().getStatusCode()).isEqualTo(statusCode));
    }

    @Then("the response should contain a field {string}")
    public void theResponseShouldContainAField(String fieldName) {
        Response response = SerenityRest.lastResponse();
        theActorInTheSpotlight().attemptsTo(Ensure.that(response.getBody().jsonPath().getString(fieldName)).isNotBlank());
    }

    @Then("the response should contain a message with value {string}")
    public void theResponseShouldContainAMessageWithValue(String message) {
        Response response = SerenityRest.lastResponse();
        String messageBody = response.getBody().jsonPath().getString("message");
        theActorInTheSpotlight().attemptsTo(Ensure.that(messageBody).isNotBlank());
        theActorInTheSpotlight().attemptsTo(Ensure.that(messageBody).isEqualTo(message));
    }


    @Then("the response should contain the created/updated item")
    public void theResponseShouldContainTheCreatedItem(Map<String, String> user) {
        Response response = SerenityRest.lastResponse();
        theActorInTheSpotlight().attemptsTo(Ensure.that(response.getBody().jsonPath().getString("name")).isEqualTo(user.get("name")));
        theActorInTheSpotlight().attemptsTo(Ensure.that(response.getBody().jsonPath().getString("email")).isEqualTo(user.get("email")));
    }

    @Given("{word} created these users")
    public void actorCreatedUsers(String actorName, List<Map<String, String>> users) {
        List<User> usersToRemember = new ArrayList<>();
        for (Map<String, String> user : users) {
            User userToRemember = new User(user.get("name"), user.get("email"));
            createUser(userToRemember);
            String userId = SerenityRest.lastResponse().getBody().jsonPath().getString("id");
            userToRemember.setId(userId);
            usersToRemember.add(userToRemember);
        }
        theActorCalled(actorName).remember("createdUsers", usersToRemember);
    }

    @When("{word} sends a GET request to {string}")
    public void actorSendsAGETRequestTo(String actorName, String resource) {
        theActorCalled(actorName).attemptsTo(
                Get.resource(resource)
                        .with(
                                req -> req.accept(ContentType.JSON)
                                        .header("Authorization", "Bearer %s".formatted(authToken))
                        )
        );
    }

    @Then("the response should contain the list of users")
    public void theResponseShouldContainTheListOfUsers() {
        List<User> users = theActorInTheSpotlight().recall("createdUsers");
        List<User> createdUsers = SerenityRest.lastResponse().getBody().jsonPath().getList("", User.class);
        theActorInTheSpotlight().attemptsTo(Ensure.that(users).containsExactlyInAnyOrderElementsFrom(createdUsers));
    }

    @When("{word} sends a PUT request to {string} with JSON body:")
    public void actorSendsAPUTRequestToWithJSONBody(String actorName, String resource, String jsonBody) {
        List<User> users = theActorInTheSpotlight().recall("createdUsers");
        theActorCalled(actorName).attemptsTo(Ensure.that(users).hasSizeGreaterThan(1));
        theActorInTheSpotlight().attemptsTo(
                Put.to(resource.replace("{createdItemId}",users.getFirst().getId()))
                        .with(
                                req -> req.contentType(ContentType.JSON)
                                        .header("Authorization", "Bearer %s".formatted(authToken))
                                        .body(jsonBody)
                        )
        );
    }

    @When("{word} sends a DELETE request to {string}")
    public void actorSendsADELETERequestTo(String actorName, String resource) {
        List<User> users = theActorInTheSpotlight().recall("createdUsers");
        theActorCalled(actorName).attemptsTo(Ensure.that(users).hasSizeGreaterThan(1));
        theActorInTheSpotlight().attemptsTo(
                Delete.from(resource.replace("{createdItemId}",users.getFirst().getId()))
                        .with(
                                req -> req.contentType(ContentType.JSON)
                                        .header("Authorization", "Bearer %s".formatted(authToken))
                        )
        );
    }

    private void createUser(User newUser) {
        theActorInTheSpotlight().attemptsTo(
                Post.to("/users")
                        .with(
                                request ->
                                        request.accept(ContentType.JSON)
                                                .contentType(ContentType.JSON)
                                                .header("Authorization", "Bearer %s".formatted(authToken))
                                                .body(newUser)
                        )

        );
        theActorInTheSpotlight().attemptsTo(Ensure.that(SerenityRest.lastResponse().getStatusCode()).isEqualTo(201));
    }
}
