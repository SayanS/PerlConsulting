Feature: 2.0000 Checkout

  Background: Setup
    Given Clear Customer's shopping cart - email "lloydp2017@gmail.com" password "registered"
    Given Login page is opened
    Given Language "NL" is selected
    Given Confirm using cookies

  @2.0001
  @RunAll
  Scenario: 2.0001 Checkout - shipping method- send to Address, delivery address - without Profile, with User creation
    Given Save scenario number "2.0001" to Order
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Continue without profile for Delivery address
    When Select Title "Mrs" for Profile details for Delivery Address
    When Enter "Peter" into "FirstName" field in Profile details for Delivery Address
    When Enter "Hoffman" into "LastName" field in Profile details for Delivery Address
    When Generate New Customer email "uniqueCustomerEmail"
    When Enter generated "uniqueCustomerEmail" into "Email" field in Profile details for Delivery Address
    When Generate phone number "uniquePhoneNumber"
    When Enter generated "uniquePhoneNumber" into "PhoneNumber" field for New Delivery Address
    When Enter "Fyrgangen" into "Address" field in Profile details for Delivery Address
    When Enter "777" into "Building" field in Profile details for Delivery Address
    When Enter "888" into "Post box" field in Profile details for Delivery Address
    When Enter "1000" into "Postcode" field in Profile details for Delivery Address
    When Enter "BRUSSEL" into "City (auto-filled)" field in Profile details for Delivery Address
    When Enter "10.12.2000" into "dd.mm.yyyy" field in Profile details for Delivery Address
    When Enter "Test profile info" into "Important information about your health (pregnancy, illness, ...)" field in Profile details for Delivery Address
    When Select option Create new User
    When Enter "registered" into the Password and Confirmation fields for New User Profile
    When Click on Further to the shipping and payment button
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    When Click on Next button on Delivery option step
    Then "Ship To:" section should be displayed with address in Order summary
      | firstName | lastName | address1  | building | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 777      | 1000     | BRUSSEL |
#    Then Delivery method "home or office" should be displayed in Ship To section
    When Select Payment Method "Pay with card"
    When Enter an Order notes "My test order" for Guest Checkout
    When Click on Place Order button
    Then Page "https://secure.ogone.com/ncol/test/orderstandard.asp" should be opened
    When Select payment method Visa card
    When Enter "67030000000000003" value into Card Number field on Ingenico page
    When Select "12" value from the Month drop-down of Expiration date on Ingenico page
    When Select "2018" value from the Year drop-down of Expiration date on Ingenico page
    When Enter card verification code "123"
    When Click on Confirm my Order button on Ingenico page
    When Go through Ingenico additional security pages with password "11111"
    Then Order details page should be displayed with title "checkout.orderConfirmation.checkoutSuccessful"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1  | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 1000     | BRUSSEL |
    Then Payment address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1  | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 1000     | BRUSSEL |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    Then Check that order notes is displayed

  @2.0002
  @RunAll
  Scenario: 2.0002 Checkout with New added Delivery address
    Given Save scenario number "2.0002" to Order
    When Add product to Shopping Cart
      | productCode | productName                                      | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML  | 1   | 59.9  |               |                 |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 2   | 9.75  | 7.80          | -20%            |
#      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30              | 1   | 17.00 | 11.90         | -30%            |
#      | 3522141     | Biocure Long Action Megatone Energy Boost Comp 30 | 2   | 19.00 | 13.30         | -30%            |
    When Click on Cart button in header
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Select "shipping Method" step of the Checkout
    When Click on Next button on the first Step of Checkout
    When Click on Add new address for Delivery
    When Enter "Peter" into "First Name *" field for New Delivery Address
    When Enter "Hoffman" into "Last Name *" field for New Delivery Address
    When Enter "Test Address1" into "Addresss 1 *" field for New Delivery Address
    When Enter "777" into "Building *" field for New Delivery Address
    When Enter "888" into "Box" field for New Delivery Address
    When Enter "Test Address2" into "Addresss 2" field for New Delivery Address
    When Enter "+444444444444" into "Mobile number *" field for New Delivery Address
    When Enter "1000" into "Post code *" field for New Delivery Address
    When Enter "Brussel" into "City" field for New Delivery Address
    When Click on Save Address In My AddressBook checkbox
    When Click on Add new address button
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    When Click on Next button on Delivery option step
    Then "Ship To:" section should be displayed with address in Order summary
      | firstName | lastName | address1      | building | postCode | city    |
      | Peter     | Hoffman  | Test Address1 | 777      | 1000     | Brussel |
    When Select Payment Method "Pay with card"
    When Enter an Order notes "My test order" for Guest Checkout
    When Click on Place Order button
    Then Page "https://secure.ogone.com/ncol/test/orderstandard.asp" should be opened
    When Select payment method Visa card
    When Enter "67030000000000003" value into Card Number field on Ingenico page
    When Select "12" value from the Month drop-down of Expiration date on Ingenico page
    When Select "2018" value from the Year drop-down of Expiration date on Ingenico page
    When Enter card verification code "123"
    When Click on Confirm my Order button on Ingenico page
    When Go through Ingenico additional security pages with password "11111"
    Then Order details page should be displayed with title "checkout.orderConfirmation.checkoutSuccessful"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be equal to New added Address
      | firstName | lastName | address1      | address2      | postCode | city    |
      | Peter     | Hoffman  | Test Address1 | Test Address2 | 1000     | Brussel |
    Then Payment address on the Order Confirmation page should be equal to New added Address
      | firstName | lastName | address1      | address2      | postCode | city    |
      | Peter     | Hoffman  | Test Address1 | Test Address2 | 1000     | Brussel |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Check that order notes is displayed
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Created order should be in the Order History list
    Then The Price of Created order in the Order History list should be the same as in the Cart
    When Open Order Details page of created order
    Then Order Details page should be displayed with all the details of created order
    Then Order Details page should be displayed with all the products of created order
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" subject "CreatedOrderNumber"
    Then Letter should contains text "Beste Dmitriy"
    Then Order total in email Order conformation should be equal to created order

  @2.0003
  @RunAll
  Scenario: 2.0003 Check that New added delivery address is Saved in Customer Profile
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Manage Address item from side menu of MyAccount
    Then Profile should contains address
      | firstName | lastName | address1              | address2      | phoneNumber   | postCode | city    | land   |
      | Peter     | Hoffman  | Test Address1 777‐888 | Test Address2 | +444444444444 | 1000     | BRUSSEL | België |
    And Click on Logout button in Header menu

  @2.0004
  @RunAll
  Scenario: 2.0004 Check ability to change default Address of the Customer Profile
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Manage Address item from side menu of MyAccount
    When Set Address as default for Customer Profile
      | firstName | lastName | address1              | address2      | phoneNumber   | postCode | city    | land   |
      | Peter     | Hoffman  | Test Address1 777‐888 | Test Address2 | +444444444444 | 1000     | BRUSSEL | België |
    Then Alert message "Your default address is updated." should be displayed
    Then Alert message "You must validate your email address" should be displayed
    Then The first address in the addresses list of the Customer Profile should be
      | firstName | lastName | address1              | address2      | phoneNumber   | postCode | city    | land   |
      | Peter     | Hoffman  | Test Address1 777‐888 | Test Address2 | +444444444444 | 1000     | BRUSSEL | België |
    When Set Address as default for Customer Profile
      | firstName | lastName | address1         | address2 | phoneNumber   | postCode | city    | land   |
      | Dmitriy   | Testov   | Avenue, str. 777 |          | +434343434343 | 1000     | BRUSSEL | België |
    And Click on Logout button in Header menu

  @2.0005
  @RunAll
  Scenario: 2.0005 Check ability to delete New added delivery address from Customer Profile
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Manage Address item from side menu of MyAccount
    Then Remove address from Customer Profile
      | firstName | lastName | address1              | address2      | phoneNumber   | postCode | city    | land    |
      | Peter     | Hoffman  | Test Address1 777‐888 | Test Address2 | +444444444444 | 1000     | BRUSSEL | Belgium |
    Then Customer Profile shouldn't contains removed address
      | firstName | lastName | address1              | address2      | phoneNumber   | postCode | city    | land    |
      | Peter     | Hoffman  | Test Address1 777‐888 | Test Address2 | +444444444444 | 1000     | BRUSSEL | Belgium |
    And Click on Logout button in Header menu

  @2.0006
  @RunAll
  Scenario: 2.0006 Checkout - shipping method- Send to Address, delivery address - get from Profile
    Given Save scenario number "2.0006" to Order
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Select "shipping Method" step of the Checkout
    When Click on Next button on the first Step of Checkout
    When Select Delivery address "Avenue"
    When Click on Further button on Step 2
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    When Click on Next button on Delivery option step
    Then "Ship To:" section should be displayed with address in Order summary
      | firstName | lastName | address1     | building | postCode | city    |
      | Dmitriy   | Testov   | Avenue, str. | 777      | 1000     | BRUSSEL |
    When Select Payment Method "Pay with card"
    When Enter an Order notes "My test order" for Guest Checkout
    When Click on Place Order button
    Then Page "https://secure.ogone.com/ncol/test/orderstandard.asp" should be opened
    When Select payment method Visa card
    When Enter "67030000000000003" value into Card Number field on Ingenico page
    When Select "12" value from the Month drop-down of Expiration date on Ingenico page
    When Select "2018" value from the Year drop-down of Expiration date on Ingenico page
    When Enter card verification code "123"
    When Click on Confirm my Order button on Ingenico page
    When Go through Ingenico additional security pages with password "11111"
    Then Order details page should be displayed with title "checkout.orderConfirmation.checkoutSuccessful"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1     | phoneNumber       | postCode | city    | land    |
      | Dmitriy   | Testov   | Avenue, str. | 00383666909933333 | 1000     | BRUSSEL | Belgium |
    Then Payment address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1     | phoneNumber       | postCode | city    | land    |
      | Dmitriy   | Testov   | Avenue, str. | 00383666909933333 | 1000     | BRUSSEL | Belgium |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    Then Check that order notes is displayed
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Created order should be in the Order History list
    Then The Price of Created order in the Order History list should be the same as in the Cart
    When Open Order Details page of created order
    Then Order Details page should be displayed with all the details of created order
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" subject "CreatedOrderNumber"
    Then Letter should contains text "Beste Dmitriy"
    Then Order total in email Order conformation should be equal to created order

  @2.0007
  @RunAll
  Scenario: 2.0007 Guest Checkout - shipping method- Get in pharmacy or delivery location, delivery address - without Profile
    Given Save scenario number "2.0007" to Order
    When Add product to Shopping Cart
      | productCode | productName                                      | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML  | 1   | 59.9  |               |                 |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 2   | 9.75  | 7.80          | -20%            |
#      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30              | 1   | 17.00 | 11.90         | -30%            |
#      | 3522141     | Biocure Long Action Megatone Energy Boost Comp 30 | 2   | 19.00 | 13.30         | -30%            |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Get in pharmacy or delivery location
    When Search for "Eeklo" as Desired pharmacy for delivery location
    When Select Desired pharmacy "Eeklo" in search results
    Then "Eeklo" should be displayed in Shipping method section
    When Enter "Tester" into the "FirstName" field for reminder
    When Enter "Testerson" into the "LastName" field for reminder
    When Enter "+444444444444" into the "Number *" field for reminder
    When Enter "lloydp2017@gmail.com" into the "E-mail *" field for reminder
    When Check Notification type "email"
    When Enter an Order notes "My test order"
    When Click on Next button on Step 1 in case Get in pharmacy
    Then Order details page should be displayed with title "Thank you for your order!"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be Default address from Customer profile
      | firstName | address1              | phoneNumber       | postCode | city  |
      | Eeklo     | KoningAlbertstraat 22 | 00383666909933333 | 9900     | Eeklo |
    Then Payment address on the Order Confirmation page should be Default address from Customer profile
      | firstName         | lastName | address1 | phoneNumber | postCode | city | land |
      | Payment at pickup |          |          |             |          |      |      |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    Then Check that order notes is displayed
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" subject "CreatedOrderNumber"
    Then Letter should contains text "Beste Tester Testerson"
    Then Order total in email Order conformation should be equal to created order

  @2.0008
  @RunAll
  Scenario: 2.0008 Checkout - shipping method - Send to Address, delivery address - without Profile, without User creation
    Given Save scenario number "2.0008" to Order
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Continue without profile for Delivery address
    When Select Title "Mrs" for Profile details for Delivery Address
    When Enter "Peter" into "FirstName" field in Profile details for Delivery Address
    When Enter "Hoffman" into "LastName" field in Profile details for Delivery Address
    When Enter "lloydp2017@gmail.com" into "Email" field in Profile details for Delivery Address
    When Enter "+444444444444" into "PhoneNumber" field in Profile details for Delivery Address
    When Enter "Fyrgangen" into "Address" field in Profile details for Delivery Address
    When Enter "777" into "Building" field in Profile details for Delivery Address
    When Enter "888" into "Post box" field in Profile details for Delivery Address
    When Enter "1000" into "Postcode" field in Profile details for Delivery Address
    When Enter "BRUSSEL" into "City (auto-filled)" field in Profile details for Delivery Address
    When Click on Further to the shipping and payment button
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    When Click on Next button on Delivery option step
    Then "Ship To:" section should be displayed with address in Order summary
      | firstName | lastName | address1  | building | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 777      | 1000     | BRUSSEL |
    When Select Payment Method "Pay with card"
    When Enter an Order notes "My test order" for Guest Checkout
    When Click on Place Order button
    Then Page "https://secure.ogone.com/ncol/test/orderstandard.asp" should be opened
    When Select payment method Visa card
    When Enter "67030000000000003" value into Card Number field on Ingenico page
    When Select "12" value from the Month drop-down of Expiration date on Ingenico page
    When Select "2018" value from the Year drop-down of Expiration date on Ingenico page
    When Enter card verification code "123"
    When Click on Confirm my Order button on Ingenico page
    When Go through Ingenico additional security pages with password "11111"
    Then Order details page should be displayed with title "Thank you for your order!"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1  | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 1000     | BRUSSEL |
    Then Payment address on the Order Confirmation page should be Default address from Customer profile
      | firstName | lastName | address1  | postCode | city    |
      | Peter     | Hoffman  | Fyrgangen | 1000     | BRUSSEL |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    Then Check that order notes is displayed

  @2.0009
  @RunAll
  Scenario: 2.0009 Checkout when Customer logged in - shipping method- Get in pharmacy or delivery location
    Given Save scenario number "2.0009" to Order
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | productCode | productName                                      | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML  | 1   | 59.9  |               |                 |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 2   | 9.75  | 7.80          | -20%            |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Get in pharmacy or delivery location
    When Search for "Eeklo" as Desired pharmacy for delivery location
    When Select Desired pharmacy "Eeklo" in search results
    Then "Eeklo" should be displayed in Shipping method section
    When Enter "Tester" into the "FirstName" field for reminder
    When Enter "Testerson" into the "LastName" field for reminder
    Then "Number *" field for reminder should be "+434343434343"
    Then "E-mail *" field for reminder should be "lloydp2017@gmail.com"
    When Check Notification type "email"
    When Enter an Order notes "My test order"
    When Click on Next button on Step 1 in case Get in pharmacy
    Then Order details page should be displayed with title "The order was successfully registered"
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery address on the Order Confirmation page should be Default address from Customer profile
      | firstName | address1              | phoneNumber       | postCode | city  |
      | Eeklo     | KoningAlbertstraat 22 | 00383666909933333 | 9900     | Eeklo |
    Then Payment address on the Order Confirmation page should be Default address from Customer profile
      | firstName         |
      | Payment at pickup |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    Then Check that order notes is displayed
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Created order should be in the Order History list
    Then The Price of Created order in the Order History list should be the same as in the Cart
    When Open Order Details page of created order
    Then Order Details page should be displayed with all the details of created order
    Then Order Details page should be displayed with all the products of created order
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" subject "CreatedOrderNumber"
    Then Letter should contains text "Beste Dmitriy"
    Then Order total in email Order conformation should be equal to created order

  @2.0010
  @RunAll
  Scenario: 2.0010 Check Terms and Conditions pop-up
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Select Delivery address "Avenue"
    When Click on Further button on Step 2
    When Click on Terms and Conditions button on Checkout
    Then Terms and Conditions pop-up with the title "TERMS OF PHARMACY PHARMA LLOYD" should be opened
    Then Terms and Conditions pop-up should contains Headings
      | 1. Application of these Terms and Conditions |
      | 2. Definitions                               |
      | 3. Offered Products                          |
      | 18 Changes to these Terms                    |
    When Click on Close button of the Terms and Conditions pop-up
    Then "/checkout/lloydsbe/multi/payment-method/add" page should be opened

  @2.0011
  @RunAll
  Scenario: 2.0011 Check that "Free Shipping" is applied for Order from 49 Euro
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
      | 2277515     | ACA MASK SET VOLWASSENEN                        | 1   | 5.19  |               |                 |
    When Click on Cart button in header
    Then Tips message "Free delivery" should be displayed in the Shopping Cart
    When Click on Checkout button in the Cart
    Then "Delivery:" value of Checkout order summary should be 0.0
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Select "shipping Method" step of the Checkout
    When Click on Next button on the first Step of Checkout
    When Select Delivery address "Avenue"
    When Click on Further button on Step 2
    Then "Delivery:" value of Checkout order summary should be 0.0
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    Then Delivery cost of the order in the BPOST pop-up should be 0.0
    When Click on Next button on Delivery option step
    Then "Delivery:" value of Checkout order summary should be 0.0
    Then "Total:" value of Checkout order summary should be 65.09

  @2.0012
  @RunAll
  Scenario: 2.0012 Check that "Free Shipping" isn't applied when the Order less then 49 Euro
    When Add product to Shopping Cart
      | productCode | productName              | qty | price | discountPrice | promotionalText |
      | 2277515     | ACA MASK SET VOLWASSENEN | 1   | 5.19  |               |                 |
    When Click on Cart button in header
    Then Tips message "Free Shipping from 49 Euro" should be displayed in the Shopping Cart
    When Click on Checkout button in the Cart
    Then "Delivery:" value of Checkout order summary should be 0.0
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Select "shipping Method" step of the Checkout
    When Click on Next button on the first Step of Checkout
    When Select Delivery address "Avenue"
    When Click on Further button on Step 2
    Then "Delivery:" value of Checkout order summary should be 0.0
    When Click on BPOST button
    When In opened iframe select "home or office" Delivery Method
    When Click on Next button on Delivery place step
    Then Delivery cost of the order in the BPOST pop-up should be 5.00
    When Click on Next button on Delivery option step
    Then "Delivery:" value of Checkout order summary should be 5.00
    Then "Total:" value of Checkout order summary should be 10.19

  @2.0013
  @RunAll
  Scenario: 2.0013 Check Customer's password recovery on Checkout
    When Delete all messages from email "lloydp2017@gmail.com" and password "Rfhfylfitkm12r"
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Click on Password Recovery button
    When Enter "lloydp2017@gmail.com" into restore password field
    When Click on Send email button to restore password
    Then "An e-mail with a link sent to you to change your password" message should be displayed in Restore password pop-up
    Then Close Restore password pop-up
    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains recovery letter from "<info@lloydspharma.be>" subject "Forgotten password"
    Then Letter should contains text "Beste Dmitriy"
    Then Letter should contains text "BaseURL/my-account/update-password"
    Then Check that Update password link in email is opened Update password page with title "Put the password update"
    When Enter "updatedPassword" into New password field
    When Enter "updatedPassword" into Confirmation password field
    When Click on Submit Updated password button
    Then Alert message "Your password has been changed. Log in to access your account." should be displayed
    Then "/login" page should be opened
    And Sign In with email "lloydp2017@gmail.com" and password "updatedPassword"
    When Click on Update Profile button in Header menu
    When Select Update Password item from side menu of MyAccount
    When Enter "updatedPassword" value into Current Password field on Updating Password form
    When Enter "registered" value into New Password field on Updating Password form
    When Enter "registered" value into Confirm New Password field on Updating Password form
    When Click on Update Password button
    And Click on Logout button in Header menu

  @2.0014
  @RunAll
  Scenario: 2.0014 Checkout when Product with "Online" status "Out of stock" added to cart
    When Add product to Shopping Cart
      | productCode | productName                            | qty | price |
      | 2127041     | Herborist Bupleurum Complex 250ml 0711 | 1   | 37.00 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    Then Send to address option on Delivery method step should be displayed with the message "Not in stock online"
    When Select Shipping method Send to address
    Then Warning Pop-up should be displayed with the selected Unavailable products
      | productCode | productName                            | qty | price |
      | 2127041     | Herborist Bupleurum Complex 250ml 0711 | 1   | 37.00 |
    When Click on Cancel button on the Warning popup
    When Click on Next button on the first Step of Checkout
    Then "/checkout/lloydsbe/multi/delivery-method/choose" page should be opened
    When Select Shipping method Send to address
    When Click on Remove All button on the Warning popup
    Then "BaseURL/cart" page should be opened
    Then Shopping cart should be displayed with the message "Your basket is empty"
    Then Cart count on the Header Cart button should be disappeared
