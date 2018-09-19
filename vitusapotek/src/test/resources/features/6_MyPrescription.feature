Feature: 6.0000 MyPrescription


  Background: Setup
    Given Home page is opened
    Given Confirm using cookies

  @6.0001
  @RunAll
  Scenario: 6.0001 Order drugs online today - Get in Pharmacy - when Customer is not logged in
    When Select "Bestill klargjøring av e-resept" form "Bestill medisiner" item of Main menu
    When Add Products name to e-prescription order
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    Then Medicine list should be displayed with products
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    When Enter Additional Information "Send my order as soon as possible." for e-prescription order
    When Enter Customer information "Igor" into "Fornavn" field
    When Enter Customer information "Testov" into "Etternavn" field
    When Enter Customer information "26079405344" into "Fødselsnummer" field
    When Enter Customer information "12345678" into "Mobilnummer" field
    When Select Pharmacy region "OSLO" for e-prescription order
    When Select Pharmacy "Vitusapotek Arkaden" for e-prescription order
    When Click on Send e-prescription button
    Then E-prescription confirmation page should be opened with title "Din bekreftelse"
    Then E-prescription confirmation page should contain all added products
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    Then Additional Comment on E-prescription confirmation page should be "Send my order as soon as possible."
    Then Retrieved by Pharmacy on E-prescription confirmation page should be "Vitusapotek Arkaden"

  @6.0002
  @RunAll
  Scenario: 6.0002 Order drugs online today - Get in Pharmacy - when Customer is logged in
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Select "Bestill klargjøring av e-resept" form "Bestill medisiner" item of Main menu
    When Add Products name to e-prescription order
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    Then Medicine list should be displayed with products
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    When Enter Additional Information "Send my order as soon as possible." for e-prescription order
    Then "Fornavn" field of the Customer information should be "Dmitriy"
    Then "Etternavn" field of the Customer information should be "Testov"
    Then "Fødselsnummer" field of the Customer information should be ""
    When Enter Customer information "26079405344" into "Fødselsnummer" field
    Then "Mobilnummer" field of the Customer information should be "12345678"
    When Select Pharmacy region "OSLO" for e-prescription order
    When Select Pharmacy "Vitusapotek Arkaden" for e-prescription order
    When Click on Send e-prescription button
    Then E-prescription confirmation page should be opened with title "Din bekreftelse"
    Then E-prescription confirmation page should contain all added products
      | RFSU PA Intim Aftershave 50ml          |
      | Dermalogica Shave Post Shave Balm 50ml |
    Then Additional Comment on E-prescription confirmation page should be "Send my order as soon as possible."
    Then Retrieved by Pharmacy on E-prescription confirmation page should be "Vitusapotek Arkaden"
    And Click on Logout button in Header menu

  @6.0003
  @RunAll
  Scenario: 6.0003 My prescriptions  - check ability customer login/logout
    When Select "Mine Resepter" form "Bestill medisiner" item of Main menu
    When Enter "26079405344" value into "Fødselsnummer" field for My prescription
    When Enter "12345678971" value into Reseptpose-ID field for My prescription
    When Click on OK button for My prescription login form
    Then My prescriptions page should be opened for customer "26079405344"
    When Click on Logout button on My prescriptions page
    Then "BaseURL" page should be opened