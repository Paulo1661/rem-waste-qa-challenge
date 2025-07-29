@UI
Feature: Login and User UI CRUD
  Functional UI Automation for User CRUD application

  @Login
  @ValidCredentials
  Scenario: Login with valid credentials
    Given Rachel has opened the login page
    When Rachel login with her credentials
    Then Rachel should see that the users page is visible

  @Login
  @InvalidCredentials
  Scenario: Login with invalid credentials
    Given Rachel has opened the login page
    When she login with invalid credentials
    Then Rachel should see that the login page is still visible
    And an the error message "Invalid credentials" is visible

  @CreateUSer
  Scenario: Create a new user
    Given Rachel has logged in the application
    When Rachel adds a user with these details
      | name  | John Doe           |
      | email | jdoe@remwaste.test |
    Then Rachel should see the new item in the user list

  @EditUser
  Scenario: Edit an existing user
    Given Rachel has logged in the application
    When Rachel edits the user having email "jane@example.com" with these details
      | name  | Jane Doe           |
      | email | jdoe@remwaste.test |
    Then Rachel should see that the new value is visible in the user list

  @DeleteUser
  Scenario: Delete an existing user
    Given Rachel has logged in the application
    When Rachel deletes the user with these details
      | name  | Rachid           |
      | email | rachid@example.com |
    Then Rachel should see that the deleted item is not visible in user list