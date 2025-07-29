package dev.ple.stepDefinitions;

import dev.ple.screenplay.questions.TheUserList;
import dev.ple.screenplay.tasks.CreateAUser;
import dev.ple.screenplay.tasks.LoginToTheApplication;
import dev.ple.utils.SerenityConf;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static dev.ple.screenplay.html.LoginPage.*;
import static dev.ple.screenplay.html.UsersPage.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;

public class UserCRUDStepDefinitions {

    private final SerenityConf conf = SerenityConf.getInstance();
    private final Logger LOGGER = LoggerFactory.getLogger(UserCRUDStepDefinitions.class);

    @Given("{word} has opened the login page")
    public void actorHasOpenedTheLoginPage(String actorName) {
        Actor actor = theActorCalled(actorName);

        String url = conf.getVariable("application.url");
        LOGGER.info("Opening the login page at url {}", url);
        actor.attemptsTo(Open.url(url));
        actorShouldSeeThatTheLoginPageIsVisible(actor);
    }

    @When("{word} login with her credentials")
    public void actorLoginWithCredentials(String actorName) {
        Actor actor = theActorCalled(actorName);

        String email = conf.getVariable("application.ADMIN.login");
        String password = conf.getVariable("application.ADMIN.password");

        actor.attemptsTo(LoginToTheApplication.with().theUsername(email).and().thePassword(password));
    }

    @Then("{word} should see that the users page is visible")
    public void actorShouldSeeThatTheUsersPageIsVisible(String actorName) {
        Actor actor = theActorCalled(actorName);
        actor.attemptsTo(Ensure.that(TheWebPage.currentUrl()).endsWith("/users"));
        actor.attemptsTo(Ensure.that(Text.of("//h1//strong")).isEqualTo("Paul"));
        actor.attemptsTo(Ensure.that(NAME_INPUT).isDisplayed());
        actor.attemptsTo(Ensure.that(EMAIL_INPUT).isDisplayed());
        actor.attemptsTo(Ensure.that(CREATE_OR_UPDATE_USER_BUTTON).isDisplayed());
    }

    @When("{word} login with invalid credentials")
    public void actorLoginWithInvalidCredentials(String actorName) {
        Actor actor = theActorCalled(actorName);
        String email = "invalid@email.com";
        String password = "$2025£";
        actor.attemptsTo(LoginToTheApplication.with().theUsername(email).and().thePassword(password));
        actor.remember("invalidCredentials", new HashMap<String, String>(){{put("email", email);put("password", password);}});
    }

    @Then("{word} should see that the login page is still visible")
    public void actorShouldSeeThatTheLoginPageIsStillVisible(String actorName) {
        Actor actor = theActorCalled(actorName);
        actorShouldSeeThatTheLoginPageIsVisible(actor);
        Map<String,String> invalidCred = actor.recall("invalidCredentials");
        actor.attemptsTo(Ensure.that(USERNAME_INPUT).hasValue(invalidCred.get("email")));
        actor.attemptsTo(Ensure.that(PASSWORD_INPUT).hasValue(invalidCred.get("password")));
    }

    @Then("an the error message {string} is visible")
    public void anTheErrorMessageIsVisible(String errorMessage) {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(Ensure.that(LOGIN_ERROR_MESSAGE).isDisplayed());
        actor.attemptsTo(Ensure.that(LOGIN_ERROR_MESSAGE).hasText(errorMessage));
    }

    private void actorShouldSeeThatTheLoginPageIsVisible(Actor actor) {
        actor.attemptsTo(Ensure.that(USERNAME_INPUT).isDisplayed());
        actor.attemptsTo(Ensure.that(PASSWORD_INPUT).isDisplayed());
        actor.attemptsTo(Ensure.that(LOGIN_SUBMIT_BUTTON).isDisplayed());
    }

    @Given("{word} has logged in the application")
    public void actorHasLoggedInTheApplication(String actorName) {
        Actor actor = theActorCalled(actorName);
        actorHasOpenedTheLoginPage(actorName);
        actorLoginWithCredentials(actorName);
    }

    @When("{word} adds a user with these details")
    public void actorAddsAUserWithTheseDetails(String actorName,Map<String,String> userDetails) {
        Actor actor = theActorCalled(actorName);
        actor.attemptsTo(CreateAUser.withName(userDetails.get("name")).withEmail(userDetails.get("email")));
        actor.remember("userDetails", userDetails);
    }

    @Then("{word} should see the new item in the user list")
    public void actorShouldSeeTheNewItemInTheUserList(String actorName) {
        Actor actor = theActorCalled(actorName);
        Map<String,String> userDetails = actor.recall("userDetails");
        actor.should(
                seeThat(
                        TheUserList.displayed(),
                        hasItem("%s (%s)".formatted(userDetails.get("name"), userDetails.get("email")))
                )
        );
    }

    @When("{word} edits the user having email {string} with these details")
    public void actorEditsTheUserHavingEmailWithTheseDetails(String actorName, String email,Map<String,String> userDetails) {
        Actor actor = theActorCalled(actorName);
        actor.attemptsTo(Click.on(USER_ITEM_EDIT_BUTTON.of(email)));
        actor.attemptsTo(Ensure.that(NAME_INPUT).hasValue("Jane"));
        actor.attemptsTo(Ensure.that(EMAIL_INPUT).hasValue("jane@example.com"));
        actor.attemptsTo(Enter.theValue(userDetails.get("name")).into(NAME_INPUT));
        actor.attemptsTo(Enter.theValue(userDetails.get("email")).into(EMAIL_INPUT));
        actor.attemptsTo(Click.on(CREATE_OR_UPDATE_USER_BUTTON));
        actor.attemptsTo(WaitUntil.the("//div[@role='alert']",isVisible()));
        actor.attemptsTo(Ensure.that(Text.of("//div[@role='alert']")).contains("User updated successfully"));
        actor.remember("updatedUserDetails", userDetails);
    }

    @Then("{word} should see that the new value is visible in the user list")
    public void actorShouldSeeThatTheNewValueIsVisibleInTheUserList(String actorName) {
        Actor actor = theActorCalled(actorName);
        Map<String,String> userDetails = actor.recall("updatedUserDetails");
        actor.should(
                seeThat(
                        TheUserList.displayed(),
                        hasItem("%s (%s)".formatted(userDetails.get("name"), userDetails.get("email")))
                )
        );
    }

    @When("{word} deletes the user with these details")
    public void rachelDeletesTheUserWithTheseDetails(String actorName, Map<String,String> userDetails) {
        Actor actor = theActorCalled(actorName);
        actor.attemptsTo(
                Click.on(
                        USER_ITEM_DELETE_BUTTON
                                .of("%s (%s)".formatted(userDetails.get("name"), userDetails.get("email")))
                )
        );
        actor.remember("deletedUserDetails", userDetails);
    }

    @Then("{word} should see that the deleted item is not visible in user list")
    public void actorShouldSeeThatTheDeletedItemIsNotVisibleInUserList(String actorName) {
        Actor actor = theActorCalled(actorName);
        Map<String,String> userDetails = actor.recall("deletedUserDetails");
        String name = userDetails.get("name");
        String email = userDetails.get("email");
        actor.attemptsTo(
                Ensure.that(USER_ITEM.of("%s (%s)".formatted(name, email)))
                        .isNotDisplayed());
        actor.should(
                seeThat(
                        TheUserList.displayed(),
                        not(hasItem("%s (%s)".formatted(name, email)))
                )
        );
    }

}
