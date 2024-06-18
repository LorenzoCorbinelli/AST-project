Feature: Swimmer Application Frame
  Specification of the behavior of the Swimmer Application

Background:
  Given The database contains some swimmers
  And The Swimmer View is shown

Scenario: Add a new swimmer
  Given The user provides swimmer data
  When The user clicks the "Add" button
  Then The list contains the new swimmer