@API
Feature: Login and CRUD API tests

  @Login
  @ValidCredentials
  Scenario: Successful login returns a JWT token
    When Paul sends a POST request to "/auth/login" with JSON body:
      """
      {
        "email": "paul@example.com",
        "password": "1234"
      }
      """
    Then the response status should be 200
    And the response should contain a field "token"

  @Login
  @InValidCredentials
  Scenario: Failed login with wrong credentials
    When Paul sends a POST request to "/auth/login" with JSON body:
      """
      {
        "email": "user@example.com",
        "password": "wrongpassword"
      }
      """
    Then the response status should be 401
    And the response should contain a message with value "Invalid credentials"

  @CreateUser
  Scenario: Create a new user
    Given Paul is authenticated
    When Paul sends a POST request to "/users" with JSON body:
      """
      {
        "name": "Ada",
        "email": "ada@remwaste.test"
      }
      """
    Then the response status should be 201
    And the response should contain the created item
      | name  | Ada               |
      | email | ada@remwaste.test |

  @ReadUsers
  Scenario: Read created users
    Given Paul is authenticated
    And Paul created these users
      | name  | email               |
      | Luc   | luc@remwaste.test   |
      | Ben   | ben@remwaste.test   |
      | Chloe | chloe@remwaste.test |
    When Paul sends a GET request to "/users"
    Then the response status should be 200
    And the response should contain the list of users

  @UpdateUser
  Scenario: Update the created resource
    Given Paul is authenticated
    And Paul created these users
      | name  | email               |
      | Luc   | luc@remwaste.test   |
      | Ben   | ben@remwaste.test   |
      | Chloe | chloe@remwaste.test |
    When Paul sends a PUT request to "/users/{createdItemId}" with JSON body:
      """
      {
        "name": "Rachel",
        "email": "rachel@remwaste.test"
      }
      """
    Then the response status should be 200
    And the response should contain the updated item
      | name  | Rachel               |
      | email | rachel@remwaste.test |

  @DeleteUser
  Scenario: Delete a created resource
    Given Actor is authenticated
    And Paul created these users
      | name   | email                |
      | Emma   | emma@example.com     |
      | Noah   | noah@remwaste.test   |
      | Sophie | sophie@remwaste.test |
    When Paul sends a DELETE request to "/users/{createdItemId}"
    Then the response status should be 200
