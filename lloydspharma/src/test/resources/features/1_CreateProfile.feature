Feature: 1.0000 New profile creation

  Background:
    Given Login page is opened
    Given Language "NL" is selected
    When Click on New User/Login button

  @1.0001
  @RunAll
  Scenario: 1.0001 Check ability to add new Address to the Customer Profile
    Given Create Customer profile if not exists via REST API
      | title | firstName | lastName | phoneNumber      | address      | building | box | postCode | city    | email                | password   | confirmPassword | birthDate  | additionalInfo    |
      | Mevr  | Dmitriy   | Testov   | +434343434343000 | Avenue, str. | 777      | 888 | 1000     | BRUSSEL | lloydp2017@gmail.com | registered | registered      | 12.11.1977 | Test profile info |
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Manage Address item from side menu of MyAccount
    Then Alert message "You must validate your email address" should be displayed
    When Click on Add new address button for adding Address to the Customer Profile
    When Enter "NewAddedFirstName" into "First Name *" field for New Delivery Address
    When Enter "NewAddedLastName" into "Last Name *" field for New Delivery Address
    When Enter "New added Address 1" into "Addresss 1 *" field for New Delivery Address
    When Enter "1111" into "Building" field for New Delivery Address
    When Enter "2222" into "Post box" field for New Delivery Address
    When Enter "New added Address 2" into "Addresss 2" field for New Delivery Address
    When Enter "+444444444444" into "Mobile number *" field for New Delivery Address
    When Enter "1000" into "Post code *" field for New Delivery Address
    When Enter "BRUSSEL" into "City" field for New Delivery Address
    When Click on Add new address button
    Then Alert message "You must validate your email address" should be displayed
    Then Profile should contains address
      | firstName         | lastName         | address1                      | address2            | phoneNumber   | postCode | city    | land   |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 1111‐2222 | New added Address 2 | +444444444444 | 1000     | BRUSSEL | België |
    And Click on Logout button in Header menu

  @1.0002
  @RunAll
  Scenario: 1.0002 Check ability to modify Address of the Customer Profile
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Manage Address item from side menu of MyAccount
    When Click on Modify button for Customer Profile address
      | firstName         | lastName         | address1            | address2            | phoneNumber   | postCode | city    | land   |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 | New added Address 2 | +444444444444 | 1000     | BRUSSEL | België |
    When Enter "ModifiedFirstName" into "First Name *" field for New Delivery Address
    When Enter "ModifiedLastName" into "Last Name *" field for New Delivery Address
    When Enter "Modified Address 1" into "Addresss 1 *" field for New Delivery Address
    When Enter "M777" into "Building" field for New Delivery Address
    When Enter "M888" into "Post box" field for New Delivery Address
    When Enter "Modified Address 2" into "Addresss 2" field for New Delivery Address
    When Enter "+1111111111" into "Mobile number *" field for New Delivery Address
    When Enter "1000" into "Post code *" field for New Delivery Address
    When Enter "MODIFIED" into "City" field for New Delivery Address
    When Click on Save modified address button
    Then Alert message "You must validate your email address" should be displayed
    Then Profile should contains address
      | firstName         | lastName         | address1                     | address2           | phoneNumber | postCode | city     | land   |
      | ModifiedFirstName | ModifiedLastName | Modified Address 1 M777‐M888 | Modified Address 2 | +1111111111 | 1000     | MODIFIED | België |
    Then Customer Profile shouldn't contains removed address
      | firstName         | lastName         | address1                      | address2            | phoneNumber   | postCode | city    | land   |
      | NewAddedFirstName | NewAddedLastName | New added Address 1 1111‐2222 | New added Address 2 | +444444444444 | 1000     | BRUSSEL | België |
    And Click on Logout button in Header menu

  @1.0003
  @RunAll
  Scenario: 1.0003 Check ability to Update customer Phone Number
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    Then Update Phone number to the new generated one
    Then Alert message "Your profile is changed" should be displayed
    Then Alert message "You must validate your email address" should be displayed
    And Click on Logout button in Header menu

  @1.0004
  @RunAll
  Scenario: 1.0004 Check ability to Change Customer password
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Update Password item from side menu of MyAccount
    Then Alert message "You must validate your email address" should be displayed
    When Enter "registered" value into Current Password field on Updating Password form
    When Enter "newPassword" value into New Password field on Updating Password form
    When Enter "newPassword" value into Confirm New Password field on Updating Password form
    When Click on Update Password button
    Then Alert message "Your password has been changed" should be displayed
    Then Alert message "You must validate your email address" should be displayed
    And Click on Logout button in Header menu
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "newPassword"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0005
  @RunAll
  Scenario: 1.0005 Check ability to Update customer Email
    And Sign In with email "lloydp2017@gmail.com" and password "newPassword"
    When Click on Update Profile button in Header menu
    When Select Update Email item from side menu of MyAccount
    When Update Profile email to the new generated one without changing password "newPassword"
    Then Alert message "Your profile is changed" should be displayed
    Then Alert message "You must validate your email address" should be displayed
    Then Update Personal Details page should be opened
    And Click on Logout button in Header menu

  @1.0006
  @RunAll
  Scenario: 1.0006 Check ability to SignIn with new email once it has been updated
    And Login with new Updated Email and password "newPassword"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0007
  @RunAll
  Scenario: 1.0007 Check inability to SignIn with old email once it has been updated
    And Sign In with email "lloydp2017@gmail.com" and password "newPassword"
    Then Alert message "Your username or password was incorrect. Your username is your email address" should be displayed
    Then Update Profile button shouldn't be displayed in Header menu

  @1.0008
  @RunAll
  Scenario Outline: 1.0008 Check inability to register customer without filling mandatory fields
    When Enter Title "<Title>" into registration form
    When Enter First name "<FirstName>" into registration form
    When Enter Last name "<LastName>" into registration form
    When Enter Phone number "<PhoneNumber>" into registration form
    When Enter Address "<Address>" into registration form
    When Enter Address "<Building>" into registration form
    When Enter Post code "<PostCode>" into registration form
    When Enter City "<City>" into registration form
    When Enter Email "<Email>" into registration form
    When Enter Password "<Password>" into registration form
    When Enter Password confirmation "<ConfirmPassword>" into registration form
    When Click on Create free Account button
    Then Alert message "Please correct the errors below" should be displayed
    Then Error message "<Error message>" should be displayed below the field
    Then Update Profile button shouldn't be displayed in Header menu
    Examples:
      | Title | FirstName | LastName | PhoneNumber   | Address     | Building | PostCode | City    | Email              | Password    | ConfirmPassword | Error message                                          |
      |       | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please select a title                                  |
      | Mevr  |           | Testov   | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please enter a first name                              |
      | Mevr  | Dmitriy   |          | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please enter a last name                               |
      | Mevr  | Dmitriy   | Testov   |               | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please provide your phone number                       |
      | Mevr  | Dmitriy   | Testov   | +380102030405 |             | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please enter an address                                |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. |          | 1000     | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Building number is required                            |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      |          | BRUSSEL | newemail@gmail.com | newpassword | newpassword     | Please enter your postcode                             |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      | 1000     |         | newemail@gmail.com | newpassword | newpassword     | Please indicate your town                              |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL |                    | newpassword | newpassword     | Please enter a valid email address                     |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com |             | newpassword     | Please enter a strong password (at least 6 characters) |
      | Mevr  | Dmitriy   | Testov   | +380102030405 | Avenue str. | 777      | 1000     | BRUSSEL | newemail@gmail.com | newpassword |                 | Please confirm your password                           |

  @1.0009
  @RunAll
  Scenario: 1.0009 CreateProfile Check ability to create a profile with valid data
    When Fill the registration form with data
      | title | firstName | lastName | phoneNumber   | address      | building | box | postCode | city    | email                | password   | confirmPassword | iWantToResiveNewsLetter | birthDate  | additionalInfo    |
      | Mevr  | Dmitriy   | Testov   | +434343434343 | Avenue, str. | 777      | 888 | 1000     | BRUSSEL | lloydp2017@gmail.com | registered | registered      | true                    | 12.11.1977 | Test profile info |
    When Enter Favorite Pharmacies
      | Vitusapotek Jernbanetorget |
    Then The background of selected fields should be "Green" and ticked
      | title | firstName | lastName | phoneNumber | address  | building | box      | postCode | city | email    | password | confirmPassword | iWantToResiveNewsLetter |
      |       | Selected  | Selected | Selected    | Selected | Selected | Selected | Selected |      | Selected |          |                 |                         |
    When Click on Create free Account button
    Then Alert message "Thanks to volunteer." should be displayed
    Then Alert message "You must validate your email address" should be displayed
    Then Update Profile button should be displayed in Header menu
    Then The caption of Update Profile button should contains First Name from registration form
    When Click on Update Profile button in Header menu
    Then Updating personal details of Update Profile Page should be opened with registration data
    When Select Update Email item from side menu of MyAccount
    Then Update Email page should be opened with email from registration data
    When Select Manage Address item from side menu of MyAccount
    Then Address book should contain registration data
    And Click on Logout button in Header menu
    Then Alert message "You are logged off" should be displayed

  @1.0010
  @RunAll
  Scenario: 1.0010 Check that email notification is sent after Customer registration
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains letter from "<info@lloydspharma.be>" subject "Welcome to WeCare by Lloydspharma!"
    Then Letter should contains text "To celebrate this, we would like to offer you 500 welcome points!"

  @1.0011
  @RunAll
  Scenario: 1.0011 Check ability to Sign In as registered customer
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    Then Logout button should be displayed in Header menu
    Then Update Profile button should be displayed in Header menu
    And Click on Logout button in Header menu

  @1.0012
  @RunAll
  Scenario Outline: 1.0012 Check inability use Email or Phone Number twice for successful Customer registration
    Given Create Customer profile if not exists via REST API
      | title | firstName | lastName | phoneNumber      | address      | building | box | postCode | city    | email                | password   | confirmPassword | birthDate  | additionalInfo    |
      | Mevr  | Dmitriy   | Testov   | +434343434343000 | Avenue, str. | 777      | 888 | 1000     | BRUSSEL | lloydp2017@gmail.com | registered | registered      | 12.11.1977 | Test profile info |
    When Enter Title "<Title>" into registration form
    When Enter First name "<FirstName>" into registration form
    When Enter Last name "<LastName>" into registration form
    When Enter Phone number "<PhoneNumber>" into registration form
    When Enter Address "<Address>" into registration form
    When Enter Building "<Building>" into registration form
    When Enter Box "<Box>" into registration form
    When Enter Post code "<PostCode>" into registration form
    When Enter City "<City>" into registration form
    When Enter Email "<Email>" into registration form
    When Enter Password "<Password>" into registration form
    When Enter Password confirmation "<ConfirmPassword>" into registration form
    When Click on Create free Account button
    Then Alert message "Please correct the errors below" should be displayed
    Then Error message "<Error message>" should be displayed below the field
    Then Update Profile button shouldn't be displayed in Header menu
    Examples:
      | Title | FirstName | LastName | PhoneNumber   | Address     | Building | Box | PostCode | City    | Email                 | Password    | ConfirmPassword | Error message                                   |
      | Mevr  | Dmitriy   | Testov   | +434343434343 | Avenue str. | 777      | 888 | 1000     | BRUSSEL | nonexistnew@gmail.com | newpassword | newpassword     | This phone number already exists                |
      | Mevr  | Dmitriy   | Testov   | +391111101110 | Avenue str. | 777      | 888 | 1000     | BRUSSEL | lloydp2017@gmail.com  | newpassword | newpassword     | An account already exists for the email address |

  @1.0013
  @RunAll
  Scenario Outline: 1.0013 Check inability to Sign In with wrong credentials
    Given Create Customer profile if not exists via REST API
      | title | firstName | lastName | phoneNumber      | address      | building | box | postCode | city    | email                | password   | confirmPassword | birthDate  | additionalInfo    |
      | Mevr  | Dmitriy   | Testov   | +434343434343000 | Avenue, str. | 777      | 888 | 1000     | BRUSSEL | lloydp2017@gmail.com | registered | registered      | 12.11.1977 | Test profile info |
    And Sign In with email "<email>" and password "<password>"
    Then Alert message "Your username or password was incorrect. Your username is your email address" should be displayed
    Then Update Profile button shouldn't be displayed in Header menu
    Examples:
      | email                         | password     |
      | unregistered@unregistered.com | unregistered |
      | lloydp2017@gmail.com          |              |
      |                               | unregistered |
      |                               | registered   |
      |                               |              |








