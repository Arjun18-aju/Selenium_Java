@tag
Feature: Register user on Ecommerce Website
  Validate registration flow using the registration form

  Background:
    Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: User can open the registration page
    Given I am on registration page
    Then I should see the registration page loaded

  @Regression
  Scenario Outline: User can fill the registration form
    Given I am on registration page
    When I fill the registration form with first name <firstName>, last name <lastName>, email <email>, phone <phone>, occupation <occupation>, gender <gender>, and password <password>
    Then I should see the registration page loaded

    Examples:
      | firstName | lastName | email                    | phone       | occupation | gender | password      |
      | Arjun     | s    | arjun.reg.test.1@gmail.com | 9876543210 | Student     | Male   | Test@12345    |
