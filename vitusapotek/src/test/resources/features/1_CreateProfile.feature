Feature: 1.0000 CreateProfile

  @1.0001
  @RunAll
  Scenario: 1.0001 Check ability to add new Address to the Customer Profile
    Given Create Customer profile if not exists via REST API
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Dine leveringsadresser" item from Profile details
    When Click on Add new address button for adding Address to the Customer Profile
    When Select "Norge" value from Land field for New Delivery Address
    When Enter "NewAddedFirstName" into "Fornavn" field for New Delivery Address
    When Enter "NewAddedLastName" into "Etternavn" field for New Delivery Address
    When Enter "New added Address 1" into "Adresselinje 1" field for New Delivery Address
    When Enter "New added Address 2" into "Adresselinje 2" field for New Delivery Address
    When Enter "01234567" into "Telefonnummer" field for New Delivery Address
    When Enter "1081" into "Postnummer" field for New Delivery Address
    When Click on Add new address button
    Then Alert message "Du må validere din epost adresse." should be displayed
    Then Address book should contain address
      | firstName         | lastName         | address1            | address2            | phoneNumber | postCode | city | land  |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 | New added Address 2 | 01234567    | 1081     | OSLO | Norge |
    And Click on Logout button in Header menu

  @1.0002
  @RunAll
  Scenario: 1.0002 Check ability to modify Address of the Customer Profile
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Dine leveringsadresser" item from Profile details
    When Click on Modify button for Customer Profile address
      | firstName         | lastName         | address1            | address2            | phoneNumber | postCode | city | land  |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 | New added Address 2 | 01234567    | 1081     | OSLO | Norge |
    When Select "Norge" value from Land field for New Delivery Address
    When Enter "ModifiedFirstName" into "Fornavn" field for New Delivery Address
    When Enter "ModifiedLastName" into "Etternavn" field for New Delivery Address
    When Enter "ModifiedAddress 1" into "Adresselinje 1" field for New Delivery Address
    When Enter "ModifiedAddress 2" into "Adresselinje 2" field for New Delivery Address
    When Enter "01010101" into "Telefonnummer" field for New Delivery Address
#    When Enter "1081" into "Postnummer" field for New Delivery Address
    When Click on Save modified address button
    Then Alert message "Du må validere din epost adresse." should be displayed
    Then Address book should contain address
      | firstName         | lastName         | address1          | address2          | phoneNumber | postCode | city | land  |
      | ModifiedFirstName | ModifiedLastName | ModifiedAddress 1 | ModifiedAddress 2 | 01010101    | 1081     | OSLO | Norge |
    Then Customer Profile shouldn't contain removed address
      | firstName         | lastName         | address1            | address2            | phoneNumber | postCode | city | land  |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 | New added Address 2 | 01234567    | 1081     | OSLO | Norge |
    And Click on Logout button in Header menu

  @1.0003
  @RunAll
  Scenario: 1.0003 Check ability to change Default Address of the Customer Profile
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Dine leveringsadresser" item from Profile details
    When Click on Set as default button for Customer Profile address
      | firstName         | lastName         | address1          | address2          | phoneNumber | postCode | city | land  |
      | ModifiedFirstName | ModifiedLastName | ModifiedAddress 1 | ModifiedAddress 2 | 01010101    | 1081     | OSLO | Norge |
    Then Alert message "Din standardadresse ble oppdatert" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    Then First Address in the Address book should be
      | firstName         | lastName         | address1          | address2          | phoneNumber | postCode | city | land  |
      | ModifiedFirstName | ModifiedLastName | ModifiedAddress 1 | ModifiedAddress 2 | 01010101    | 1081     | OSLO | Norge |
    And Click on Logout button in Header menu

  @1.0004
  @RunAll
  Scenario: 1.0004 Check ability to remove Address from the Customer Profile
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Dine leveringsadresser" item from Profile details
    When Click on Remove button for Customer Profile address
      | firstName         | lastName         | address1          | address2          | phoneNumber | postCode | city | land  |
      | ModifiedFirstName | ModifiedLastName | ModifiedAddress 1 | ModifiedAddress 2 | 01010101    | 1081     | OSLO | Norge |
    When Click on Delete button on Delete Address pop-up
    Then Alert message "Din adresse ble fjernet" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    Then Customer Profile shouldn't contain removed address
      | firstName         | lastName         | address1          | address2          | phoneNumber | postCode | city | land  |
      | ModifiedFirstName | ModifiedLastName | ModifiedAddress 1 | ModifiedAddress 2 | 01010101    | 1081     | OSLO | Norge |
    And Click on Logout button in Header menu

  @1.0005
  @RunAll
  Scenario: 1.0016 Check ability to change "I want to be VI + Customer" status
    Given Create Customer profile if not exists via REST API
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Din informasjon" item from Profile details
    When Uncheck option I want to be VI Customer
    When Click on Save button for Updated Personal details of the profile
    Then Alert message "Profilen din har blitt oppdatert" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    And Click on Logout button in Header menu
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select "Din informasjon" item from Profile details
    Then Option I want to be VI Customer shouldn't be checked

  @1.0006
  @RunAll
  Scenario: 1.0005 Check ability to Update customer Personal details
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Select "Din informasjon" item from Profile details
    When Update Personal details of the Profile with new ones
      | firstName      | lastName      | phoneNumber | newsLetter | iWantTobe4 |
      | Updateddmitriy | Updatedtestov | 87654321    | true       | true       |
    When Click on Save button for Updated Personal details of the profile
    Then Alert message "Profilen din har blitt oppdatert" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    And Click on Logout button in Header menu
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select "Din informasjon" item from Profile details
    Then Din informasjon section of the Profile should be opened with registration data
      | firstName      | lastName      | phoneNumber | newsLetter | iWantTobe4 |
      | Updateddmitriy | Updatedtestov | 87654321    | true       | true       |
    And Click on Logout button in Header menu

  @1.0007
  @RunAll
  Scenario: 1.0006 Check ability to Change Customer password
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select "Endre passord" item from Profile details
    Then Alert message "Du må validere din epost adresse." should be displayed
    When Enter "registered" value into "Nåværende passord" field on Updating Passsword form
    When Enter "newPassword" value into "Nytt passord" field on Updating Passsword form
    When Enter "newPassword" value into "Bekreft nytt passord" field on Updating Passsword form
    When Click on "Endre passord" to Change Customer password
    Then Alert message "Ditt passord har blitt endret" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    And Click on Logout button in Header menu
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "newPassword"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0008
  @RunAll
  Scenario: 1.0007 Check ability to Update customer Email
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "newPassword"
    When Click on Update Profile button in Header menu
    When Select "Oppdater epost adresse" item from Profile details
    When Generate New Customer email "uniqueEmail"
    When Update Profile email to "uniqueEmail"
    When Enter "newPassword" value into Password field for Updating email
    When Click on Change Email button for Updating email
    Then Alert message "Profilen din har blitt oppdatert" should be displayed
    Then Alert message "Du må validere din epost adresse." should be displayed
    And Click on Logout button in Header menu

  @1.0009
  @RunAll
  Scenario: 1.0008 Check ability to SignIn with new email once it has been updated
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Login with new Updated Email "uniqueEmail" and password "newPassword"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0010
  @RunAll
  Scenario: 1.0009 Check inability to SignIn with old email once it has been updated
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    Then Alert message "Feil brukernavn eller passord. Brukernavn er din e-postadresse." should be displayed
    Then Update Profile button shouldn't be displayed in Header menu

  @1.0011
  @RunAll
  Scenario Outline: 1.0010 Check that after entered PostCode the City field is populated with the appropriate value
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    When Enter new Customer's "<PostCode>" into "Postnummer" field
    Then The value "<City>" should be displayed in the Poststed field
    Examples:
      | PostCode | City    |
      | 1081     | OSLO    |
      | 3030     | DRAMMEN |

  @1.0012
  @RunAll
  Scenario Outline: 1.0011 Check inability to register customer without filling mandatory fields
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    When Enter new Customer's "<FirstName>" into "Fornavn" field
    When Enter new Customer's "<LastName>" into "Etternavn" field
    When Enter new Customer's "<PhoneNumber>" into "Telefonnummer til hentemelding" field
    When Enter new Customer's "<Address>" into "Gatenavn og nummer" field
    When Enter new Customer's "<PostCode>" into "Postnummer" field
    When Enter new Customer's "<Email>" into "Epost" field
    When Enter new Customer's "<Password>" into "Passord" field
    When Enter new Customer's "<ConfirmPassword>" into "Gjenta passord" field
    When Click on Create Account button
    Then Alert message "Vennligst kontroller at feltene under er fylt ut riktig." should be displayed
    Then Error message "<Error message>" should be displayed for Customer profile
    Then Update Profile button shouldn't be displayed in Header menu
    Examples:
      | FirstName | LastName | PhoneNumber | Address     | PostCode | Email              | Password    | ConfirmPassword | Error message                                |
      |           | Testov   | 12345678    | Avenue str. | 1081     | newemail@gmail.com | newpassword | newpassword     | Fornavn mangler                              |
      | Dmitriy   |          | 12345678    | Avenue str. | 1081     | newemail@gmail.com | newpassword | newpassword     | Etternavn mangler                            |
      | Dmitriy   | Testov   |             | Avenue str. | 1081     | newemail@gmail.com | newpassword | newpassword     | Mobilnummer mangler                          |
      | Dmitriy   | Testov   | 12345678    |             | 1081     | newemail@gmail.com | newpassword | newpassword     | Adresse mangler                              |
      | Dmitriy   | Testov   | 12345678    | Avenue str. |          | newemail@gmail.com | newpassword | newpassword     | Postnummer mangler                           |
      | Dmitriy   | Testov   | 12345678    | Avenue str. | 1081     |                    | newpassword | newpassword     | Epost mangler                                |
      | Dmitriy   | Testov   | 12345678    | Avenue str. | 1081     | newemail@gmail.com |             | newpassword     | Vennligst oppgi passord med minimum 6 tegn.  |
      | Dmitriy   | Testov   | 12345678    | Avenue str. | 1081     | newemail@gmail.com | newpassword |                 | Passord og passord bekreftelse er ikke likt. |

  @1.0013
  @RunAll
  Scenario: 1.0012 CreateProfile Check ability to create a profile with valid data
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    When Fill the registration form with data
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    When Select membership groups
      | Revmatiker    |
      | Volvat medlem |
    When Enter Favorite Pharmacies
      | Vitusapotek Oslo City |
      | Vitusapotek Byporten  |
    When Click on Create Your Account button
    Then Alert message "Du må validere din epost adresse." should be displayed
    Then Alert message "Takk for at du registrerte deg hos Vitusapotek." should be displayed
    Then Update Profile button "Velkommen Dmitriy" should be displayed in Header menu
    When Click on Update Profile button in Header menu
    When Select "Din informasjon" item from Profile details
    Then Din informasjon section of the Profile should be opened with registration data
      | firstName | lastName | phoneNumber | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | true       | true       |
    Then Personal details of the Profile should be displayed with selected Membership groups
      | Revmatiker    |
      | Volvat medlem |
    Then Personal details of the Profile should be displayed with selected Pharmacies
      | Vitusapotek Oslo City |
      | Vitusapotek Byporten  |
    When Select "Oppdater epost adresse" item from Profile details
    Then The value of "Ny e-post" field in Update email address section should be "qavitusapotek@gmail.com"
    When Select "Dine leveringsadresser" item from Profile details
    Then First Address in the Address book should be
      | firstName | lastName | address1        | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 | 1081     | OSLO |
    And Click on Logout button in Header menu

  @1.0014
  @RunAll
  Scenario: 1.0013 Check inability use Email twice for successful Customer registration
    Given Create Customer profile if not exists via REST API
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    When Fill the registration form with data
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      |
    When Click on Create Account button
    Then Alert message "Vennligst kontroller at feltene under er fylt ut riktig." should be displayed
    Then Error message " Det kan virke som om denne epost-adressen allerede er registrert hos Vitusapotek." should be displayed for Customer profile
    Then Update Profile button shouldn't be displayed in Header menu

  @1.0015
  @RunAll
  Scenario: 1.0014 Check ability to Sign In as registered customer
    Given Create Customer profile if not exists via REST API
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0016
  @RunAll
  Scenario Outline: 1.0015 Check inability to Sign In with wrong credentials
    Given Create Customer profile if not exists via REST API
      | firstName | lastName | phoneNumber | address         | postCode | city | email                   | password   | confirmPassword | newsLetter | iWantTobe4 |
      | Dmitriy   | Testov   | 12345678    | Avenue, str. 52 | 1081     | OSLO | qavitusapotek@gmail.com | registered | registered      | true       | true       |
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "<email>" and password "<password>"
    Then Alert message "Feil brukernavn eller passord. Brukernavn er din e-postadresse." should be displayed
    Then Update Profile button shouldn't be displayed in Header menu
    Examples:
      | email                         | password     |
      | unregistered@unregistered.com | unregistered |
      | qavitusapotek@gmail.com       |              |
      |                               | registered   |
      |                               |              |













