package dev.ple.screenplay.html;

import net.serenitybdd.screenplay.targets.Target;

public class UsersPage {
    public static Target NAME_INPUT = Target.the("User form name field").locatedBy("#name");
    public static Target EMAIL_INPUT = Target.the("User form email field").locatedBy("#email");
    public static Target CREATE_OR_UPDATE_USER_BUTTON = Target.the("Create or edit user button").locatedBy("#submit-user-btn");
    public static Target USER_ITEM_EDIT_BUTTON = Target.the("User item edit button of {0}").locatedBy("//li[contains(., '{0}')]//button[@id='edit-item']");
    public static Target USER_ITEM_DELETE_BUTTON = Target.the("User item delete button of {0}").locatedBy("//li[contains(., '{0}')]//button[@id='delete-item']");
    public static Target USER_ITEM = Target.the("User item of {0}").locatedBy("//li[contains(., '{0}')]");
}
