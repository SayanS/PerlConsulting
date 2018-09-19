Feature: 3.0000 My prescription

  Background: Setup
    Given Clear Customer's shopping cart - email "lloydp2017@gmail.com" password "registered"
    Given Login page is opened
    Given Language "FR" is selected
    Given Confirm using cookies

  @3.0001
  @RunAll
  Scenario: 3.0001 Check ability to create Prescription Order with EMAIL communication method - Guest checkout
    Given Save scenario number "3.0001" to Order
    When Select "My prescription" item in Header menu
    When Upload prescription scan "./src/test/resources/data/MyPrescription.jpg"
    Then Uploaded file name "MyPrescription.jpg" should be displayed in the Prescription form
    When Enter "Please send me the order ASAP" into the "Comments" field of My prescription Form
    When Enter "Serg" into the "FirstName" field of My prescription Form
    When Enter "Hoffman" into the "LastName" field of My prescription Form
    When Enter "lloydp2017@gmail.com" into the "Email" field of My prescription Form
    When Enter "+45555555555" into the "PhoneNumber" field of My prescription Form
    When Enter "44444444444" into the "NationalPhone" field of My prescription Form
    When Click on Your pharmacy button of My prescription form
    When Enter "1000" into search field in Find your pharmacy pop-up
    When Click on Find Pharmacy button in Find your pharmacy pop-up
    When Choose Pharmacy number "3" from Search results in Find your pharmacy pop-up
    Then "Chosen" button should be displayed for Pharmacy number "3"
    When Close Find your pharmacy pop-up
    Then Chosen Pharmacy should be displayed in Prescription form
    When Select "email" option for communication of My prescription Form
    When Click on Send Prescription button
    Then E-receipt page should be opened with the Messages
      | Thank you for your order!                                     |
      | You'll be notified when your order will be ready for delivery |
    Then Prescription order comment should be "Please send me the order ASAP"
    Then Pickup at Pharmacy value should be the Pharmacy Name from Prescription form
    When Download prescription file
    Then "MyPrescription.jpg" file should be in folder "./src/test/resources/download/"
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" with receiver "Serg Hoffman"

  @3.0002
  @RunAll
  Scenario: 3.0002 Check ability to create Prescription Order with SMS communication method - Guest checkout
    Given Save scenario number "3.0002" to Order
    When Select "My prescription" item in Header menu
    When Upload prescription scan "./src/test/resources/data/MyPrescription.jpg"
    Then Uploaded file name "MyPrescription.jpg" should be displayed in the Prescription form
    When Enter "Please send me the order ASAP" into the "Comments" field of My prescription Form
    When Enter "Andre" into the "FirstName" field of My prescription Form
    When Enter "Stolz" into the "LastName" field of My prescription Form
    When Enter "lloydp2017@gmail.com" into the "Email" field of My prescription Form
    When Enter "+91191191155" into the "PhoneNumber" field of My prescription Form
    When Enter "91191144911" into the "NationalPhone" field of My prescription Form
    When Click on Your pharmacy button of My prescription form
    When Enter "1000" into search field in Find your pharmacy pop-up
    When Click on Find Pharmacy button in Find your pharmacy pop-up
    When Choose Pharmacy number "3" from Search results in Find your pharmacy pop-up
    Then "Chosen" button should be displayed for Pharmacy number "3"
    When Close Find your pharmacy pop-up
    Then Chosen Pharmacy should be displayed in Prescription form
    When Select "sms" option for communication of My prescription Form
    When Click on Send Prescription button
    Then E-receipt page should be opened with the Messages
      | Thank you for your order!                                     |
      | You'll be notified when your order will be ready for delivery |
    Then Prescription order comment should be "Please send me the order ASAP"
    Then Pickup at Pharmacy value should be the Pharmacy Name from Prescription form
    When Download prescription file
    Then "MyPrescription.jpg" file should be in folder "./src/test/resources/download/"
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" with receiver "Andre Stolz"

  @3.0003
  @RunAll
  Scenario: 3.0003 Check ability to create Prescription Order with EMAIL communication method - Customer logged in
    Given Save scenario number "3.0003" to Order
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Select "My prescription" item in Header menu
    Then My prescription form should be prepopulate with the values
      | FirstName   | Dmitriy              |
      | LastName    | Testov               |
      | Email       | lloydp2017@gmail.com |
      | PhoneNumber | +434343434343        |
    When Upload prescription scan "./src/test/resources/data/MyPrescription.jpg"
    Then Uploaded file name "MyPrescription.jpg" should be displayed in the Prescription form
    When Enter "Please send me the order ASAP" into the "Comments" field of My prescription Form
    When Enter "44444444444" into the "NationalPhone" field of My prescription Form
    When Click on Your pharmacy button of My prescription form
    When Enter "1000" into search field in Find your pharmacy pop-up
    When Click on Find Pharmacy button in Find your pharmacy pop-up
    When Choose Pharmacy number "3" from Search results in Find your pharmacy pop-up
    Then "Chosen" button should be displayed for Pharmacy number "3"
    When Close Find your pharmacy pop-up
    Then Chosen Pharmacy should be displayed in Prescription form
    When Select "email" option for communication of My prescription Form
    When Click on Send Prescription button
    Then E-receipt page should be opened with the Messages
      | Thank you for your order!                                     |
      | You'll be notified when your order will be ready for delivery |
    Then Prescription order comment should be "Please send me the order ASAP"
    Then Pickup at Pharmacy value should be the Pharmacy Name from Prescription form
    When Download prescription file
    Then "MyPrescription.jpg" file should be in folder "./src/test/resources/download/"
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" with receiver "Dmitriy Testov"
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Created order should be in the Order History list
    When Open Order Details page of created order
    Then Order Details page should be displayed with all the details of created order

  @3.0004
  @RunAll
  Scenario: 3.0004 Check ability to remove Uploaded prescription
    When Select "My prescription" item in Header menu
    When Upload prescription scan "./src/test/resources/data/MyPrescription.jpg"
    Then Uploaded file name "MyPrescription.jpg" should be displayed in the Prescription form
    When Click on Remove button for Uploaded prescription
    Then Uploaded file should be removed from Prescription form

  @3.0005
  @RunAll
  Scenario Outline: 3.0005 Check ability to Search Pharmacy by Name
    When Select "My prescription" item in Header menu
    When Click on Your pharmacy button of My prescription form
    When Enter "<Pharmacy>" into search field in Find your pharmacy pop-up
    When Click on Find Pharmacy button in Find your pharmacy pop-up
    Then Each Pharmacy name in Search results should contains "<Pharmacy>"
    When Close Find your pharmacy pop-up
    Examples:
      | Pharmacy   |
      | Eeklo      |
      | Gitschotel |


