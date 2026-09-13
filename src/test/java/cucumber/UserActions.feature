@tag
Feature: User actions on Ecommerce Website
  Validate login, cart, and order journey

  Background:
    Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Valid user can login
    When Logged in with username <name> and password <password>
    Then user should be redirected to the product catalogue

    Examples:
      | name                  | password          |
      | ${username}           | ${password}       |

  @ErrorValidation
  Scenario Outline: Invalid user cannot login
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples:
      | name                    | password      |
      | imarjun.180497@gmail.com | WrongPass@123 |

  @Regression
  Scenario Outline: User can add a product to cart
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    Then cart should display <productName>

    Examples:
      | name              | password      | productName |
      | ${username}       | ${password}   | ZARA COAT 3 |

  @Regression
  Scenario Outline: User can view order history
    Given Logged in with username <name> and password <password>
    And I open my orders page
    Then order history should contain <productName>

    Examples:
      | name              | password      | productName |
      | ${username}       | ${password}   | ZARA COAT 3 |
