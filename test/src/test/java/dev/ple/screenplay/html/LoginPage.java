package dev.ple.screenplay.html;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {
    public static Target USERNAME_INPUT = Target.the("Username field").locatedBy("#username");
    public static Target PASSWORD_INPUT = Target.the("Password field").locatedBy("#password");
    public static Target LOGIN_SUBMIT_BUTTON = Target.the("Submit form button").locatedBy("#login-btn");
    public static Target LOGIN_ERROR_MESSAGE = Target.the("The login error message div").locatedBy(".login-error");
}
