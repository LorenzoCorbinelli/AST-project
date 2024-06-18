Feature: Swimmer Application Frame
  Specification of the behavior of the Swimmer Application

  Background:
    Given The database contains some swimmers
    And The Swimmer View is shown

  Scenario: Add a new swimmer
    Given The user provides swimmer data
    When The user clicks the "Add" button
    Then The list contains the new swimmer

  Scenario: Add a new swimmer with an existing id
    Given The suer provides swimmer data with an existing id
    When The user clicks the "Add" button
    Then An error is shown containing the name of the existing swimmer
    