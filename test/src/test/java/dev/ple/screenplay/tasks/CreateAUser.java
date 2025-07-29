package dev.ple.screenplay.tasks;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static dev.ple.screenplay.html.UsersPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CreateAUser implements Task {
    private String name;
    private String email;

    protected CreateAUser(String name) {
        this.name = name;
    }

    public static CreateAUser withName(String name) {
        return Instrumented.instanceOf(CreateAUser.class).withProperties(name);
    }

    public CreateAUser withEmail(String email) {
        this.email=email;
        return this;
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(name).into(NAME_INPUT));
        actor.attemptsTo(Enter.theValue(email).into(EMAIL_INPUT));
        actor.attemptsTo(Click.on(CREATE_OR_UPDATE_USER_BUTTON));
        actor.attemptsTo(WaitUntil.the("//div[@role='alert']",isVisible()));
        actor.attemptsTo(Ensure.that(Text.of("//div[@role='alert']")).contains("User added successfully"));
    }
}
