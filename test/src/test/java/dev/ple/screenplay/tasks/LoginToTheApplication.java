package dev.ple.screenplay.tasks;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static dev.ple.screenplay.html.LoginPage.*;

public class LoginToTheApplication implements Task {
    private String username;
    private String password;

    protected LoginToTheApplication() {}

    public static LoginToTheApplication with() {
        return Instrumented.instanceOf(LoginToTheApplication.class).newInstance();
    }

    public LoginToTheApplication theUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginToTheApplication thePassword(String password) {
        this.password = password;
        return this;
    }

    public LoginToTheApplication and() {
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(username).into(USERNAME_INPUT));
        actor.attemptsTo(Enter.theValue(password).into(PASSWORD_INPUT));
        actor.attemptsTo(Click.on(LOGIN_SUBMIT_BUTTON));
    }
}
