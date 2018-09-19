Feature: 2.0000 Checkout

  Background: Setup
    Given Clear Customer's shopping cart - email "qavitusapotek@gmail.com" password "registered"
    Given Home page is opened
    Given Confirm using cookies

  @2.0001
  @RunAll
  Scenario: 2.0001 Guest checkout - Delivery method get in pharmacy and Save Order in profile
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Get in Pharmacy
    When Select first pharmacy with status "Har alle produkter på lager" for products in the Cart
    Then Selected Pharmacy should be displayed in Select pharmacy section
    When Enter "12312312" into the "Telefon" field for reminder
    When Enter "qavitusapotek@gmail.com" into the "E-post" field for reminder
    When Enter an Order notes "My test order"
    When Click on Next button on Step 1 in case Get in pharmacy
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should contain selected Pharmacy
    Then Payment information on the Order Confirmation page should contain text "Du betaler når du henter."
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total should be the same as in the Shopping Cart
    When Enter email "qavitusapotek@gmail.com" and password "registered" for Getting address from profile
    When Click on Save order button
    When Click on Update Profile button in Header menu
    When Select "Se din ordrehistorikk" item from Profile details
    Then Created order should be in the Order History list
    Then Mail "qavitusapotek@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<kundeservice@vitusapotek.no>" subject "Ordrebekreftelse fra Vitusapotek CreatedOrderNumber"
    Then Letter should contains text "Hei, Gjest"
    Then Order total in email Order conformation should be equal to created order
    And Click on Logout button in Header menu

  @2.0002
  @RunAll
  Scenario: 2.0002 Checkout as Registered customer - Shipping method- send by e-mail, Delivery address - get from Profile
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 2   | 139.00    |
      | 882799     | NISIM Shampoo for normal til fet hodebunn og fett hår 240 ml | 1   | 259.90    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Get Address from profile
    When Enter email "qavitusapotek@gmail.com" and password "registered" for Getting address from profile
    When Click on Further to the shipping and payment button
#    Then Alert message "Lagret handlekurv tilknyttet din bruker har blitt slått sammen med nåværende handlekurv." should be displayed
    When Select "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO" as Delivery address
    When Click on Further to the shipping and payment button
    Then Delivery section should be displayed with address "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Select Payment option "Ja, lagre mine kortopplysninger"
    When Enter an Order notes "My test order"
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
#    When Click on Next button on the NetAxept page
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price
    When Click on Update Profile button in Header menu
    When Select "Se din ordrehistorikk" item from Profile details
    Then Created order should be in the Order History list
    Then Mail "qavitusapotek@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<kundeservice@vitusapotek.no>" subject "Ordrebekreftelse fra Vitusapotek CreatedOrderNumber"
    Then Letter should contains text "Hei, Dmitriy"
    Then Order total in email Order conformation should be equal to created order
    And Click on Logout button in Header menu

  @2.0003
  @RunAll
  Scenario: 2.0003 Guest Checkout - Shipping method- send by e-mail, Delivery address - without Profile, with User creation
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Continue without profile
    When Click on Further to the shipping and payment button
    When Enter "Peter" into "Fornavn" field for New Delivery Address
    When Enter "Hoffman" into "Etternavn" field for New Delivery Address
    When Generate New Customer email "uniqueEmail"
    When Enter generated "uniqueEmail" into "Epost" field for New Customer
    When Generate phone number "uniquePhone"
    When Enter generated "uniquePhone" into "Telefon" field for New Customer
    When Enter "Wall str. 3" into "Adresse" field for New Delivery Address
    When Enter "1081" into "Postnummer" field for New Delivery Address
    When Select option "Jeg vil opprette bruker" on Step2
    When Enter "registered" value into Password and Repeat password fields
    When Click on Further to the shipping and payment button
    Then Delivery section should be displayed with address "Peter  Hoffman,  Wall str. 3 , 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Select Payment option "Ja, lagre mine kortopplysninger"
    When Enter an Order notes "My test order"
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2 | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 |          | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2 | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 |          | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price
    When Click on Update Profile button in Header menu
    When Select "Se din ordrehistorikk" item from Profile details
    Then Created order should be in the Order History list
    And Click on Logout button in Header menu

  @2.0004
  @RunAll
  Scenario: 2.0004  Guest Checkout - Shipping method- send by e-mail, Delivery address - without Profile, without User creation
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Continue without profile
    When Click on Further to the shipping and payment button
    When Enter "Peter" into "Fornavn" field for New Delivery Address
    When Enter "Hoffman" into "Etternavn" field for New Delivery Address
    When Generate New Customer email "uniqueEmail"
    When Enter generated "uniqueEmail" into "Epost" field for New Customer
    When Generate phone number "uniquePhone"
    When Enter generated "uniquePhone" into "Telefon" field for New Customer
    When Enter "Wall str. 3" into "Adresse" field for New Delivery Address
    When Enter "1081" into "Postnummer" field for New Delivery Address
    When Click on Further to the shipping and payment button
    Then Delivery section should be displayed with address "Peter  Hoffman,  Wall str. 3 , 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Click on Payment link Nets
    When Enter an Order notes "My test order"
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2 | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 |          | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2 | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 |          | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price

  @2.0005
  @RunAll
  Scenario: 2.0005 Checkout as Registered customer with New added Delivery address
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Get Address from profile
    When Enter email "qavitusapotek@gmail.com" and password "registered" for Getting address from profile
    When Click on Further to the shipping and payment button
    When Click on Add new address for Delivery
    When Select "Norge" value from Land field for New Delivery Address
    When Enter "Peter" into "Fornavn" field for New Delivery Address
    When Enter "Hoffman" into "Etternavn" field for New Delivery Address
    When Enter "12345678" into "Telefon" field for New Delivery Address
    When Enter "Wall str. 3" into "Adresselinje 1" field for New Delivery Address
    When Enter "Mall str. 7" into "Adresselinje 2" field for New Delivery Address
    When Enter "1081" into "Postnummer" field for New Delivery Address
    When Select option Save as new address
    When Click on Add Address button
    Then Delivery section should be displayed with address "Peter  Hoffman,  Wall str. 3  Mall str. 7, 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Select Payment option "Ja, lagre mine kortopplysninger"
    When Enter an Order notes "My test order"
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2    | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 | Mall str. 7 | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1    | address2    | postCode | city |
      | Peter     | Hoffman  | Wall str. 3 | Mall str. 7 | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price
    When Click on Update Profile button in Header menu
    When Select "Se din ordrehistorikk" item from Profile details
    Then Created order should be in the Order History list
    Then Mail "qavitusapotek@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<kundeservice@vitusapotek.no>" subject "Ordrebekreftelse fra Vitusapotek CreatedOrderNumber"
    Then Letter should contains text "Hei, Dmitriy"
    Then Order total in email Order conformation should be equal to created order
    And Click on Logout button in Header menu

  @2.0006
  @RunAll
  Scenario: 2.0006 Checkout when Product with "Nett" status "Out of stock" added to cart
    When Add product to Shopping Cart
      | itemNumber | productName                  | qty | listPrice |
      | 804877     | Dermalogica Ultracalming Kit | 1   | 535.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    Then Send by email option on Delivery method step should be displayed with the message "Kurven inneholder varer som ikke kan sendes. Klikk for å se mer."
    When Select Shipping method Send by email
    Then Warning Pop-up should be displayed with the selected Unavailable products
      | itemNumber | productName                  | qty | listPrice |
      | 804877     | Dermalogica Ultracalming Kit | 1   | 535.00    |
    When Click on Cancel button on the Warning popup
    When Click on Skip to Shipping address button
    Then "BaseURL/checkout/multi/delivery-method/choose" page should be opened
    When Select Shipping method Send by email
    When Click on Remove All button on the Warning popup
    Then "BaseURL/cart" page should be opened
    Then "Din handlekurv er tom" message should be displayed in the Shopping Cart
    Then Cart count on the Header Cart button should be disappeared

  @2.0007
  @RunAll
  Scenario: 2.0007 Checkout when Prescription Product added to cart
    When Add product to Shopping Cart
      | itemNumber | productName                       | qty | listPrice |
      | 042264     | Zonnic Munnpulv med mintsmak 2 mg | 1   | 89.9      |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Get Address from profile
    When Enter email "qavitusapotek@gmail.com" and password "registered" for Getting address from profile
    When Click on Further to the shipping and payment button
    When Select "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO" as Delivery address
    When Click on Further to the shipping and payment button
    Then Delivery section should be displayed with address "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Select Payment option "Ja, lagre mine kortopplysninger"
    When Enter an Order notes "My test order"
    When Enter "26079405344" value into Personal identification code field for Verifying age
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price
    And Click on Logout button in Header menu

  @2.0008
  @RunAll
  Scenario: 2.0008 Check Terms and Conditions pop-up
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Get Address from profile
    When Enter email "qavitusapotek@gmail.com" and password "registered" for Getting address from profile
    When Click on Further to the shipping and payment button
    When Select "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO" as Delivery address
    When Click on Further to the shipping and payment button
    When Click on Terms and Conditions button on Checkout
    Then Terms and Conditions pop-up with the title "SALGSBETINGELSER FOR FORBRUKERKJØP FRA VITUSAPOTEK NETTBUTIKK" should be opened
    Then Terms and Conditions pop-up should contains Headings
      | Generelt                                           |
      | Parter                                             |
      | Bestilling                                         |
      | Opplysninger i nettbutikk                          |
      | Priser                                             |
      | Betaling                                           |
      | Levering og forsinkelser                           |
      | Undersøkelse av produktene                         |
      | Dine rettigheter ved feil og mangler (Reklamasjon) |
      | Angrerett                                          |
      | Angrefrist                                         |
      | Kreditering ved reklamasjon og angrerett           |
      | Retur                                              |
      | Personopplysninger                                 |
      | Tvister                                            |
    When Click on Close button of the Terms and Conditions pop-up
    Then "/checkout/multi/payment-method/add" page should be opened

  @2.0009
  @RunAll
  Scenario: 2.0009 Checkout as Registered customer when Discount product added to Cart
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | itemNumber | productName                                        | qty | listPrice | discountPrice | promotionalText               |
      | 939021     | Crescina Complete Treatment 1300 Woman 20 x 3.5 ml | 2   | 1799.90   | 1259.93       | -30% Crescina to VI + Members |
    When Click on Cart button in Header
    When Click on Checkout button in the Cart
    When Select Shipping method Send by email
    When Click on Skip to Shipping address button
    When Select Delivery address option Get Address from profile
    When Select "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO" as Delivery address
    When Click on Further to the shipping and payment button
    Then Delivery section should be displayed with address "Dmitriy  Testov,  Avenue, str. 52 , 1081 OSLO"
    When Select "Ekspress innen 2,5 timer (leveres hverdager)" as Delivery method for an Order
    When Select Payment method "Betal med kort"
    When Select Payment option "Ja, lagre mine kortopplysninger"
    When Enter an Order notes "My test order"
    When Click on "Til trygg betaling" button on Step_3
    Then "https://test.epayment.nets.eu/Terminal/default.aspx" page should be opened
    When Select "Visa" from NetAxept Payment options
    Then Order Number on NetAxept page should be the same as in the Shopping Cart
    Then Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery
    When Enter card number "4925000000000004" on the NetAxept page
    When Enter "123" value into the CVV2 field on the NetAxept page
    When Select "12" month and "2017" year for Expiration date on the NetAxept page
    When Click on Pay button on the NetAxept page
    Then Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart
    Then Delivery information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then Payment information on the Order Confirmation page should be address
      | firstName | lastName | address1        | address2 | postCode | city |
      | Dmitriy   | Testov   | Avenue, str. 52 |          | 1081     | OSLO |
    Then All added to Cart products with appropriate Qty and Price should be in the Order
    Then Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price
    When Click on Update Profile button in Header menu
    When Select "Se din ordrehistorikk" item from Profile details
    Then Created order should be in the Order History list
    Then Mail "qavitusapotek@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<kundeservice@vitusapotek.no>" subject "Ordrebekreftelse fra Vitusapotek CreatedOrderNumber"
    Then Letter should contains text "Hei, Dmitriy"
    Then Order total in email Order conformation should be equal to created order
    And Click on Logout button in Header menu







