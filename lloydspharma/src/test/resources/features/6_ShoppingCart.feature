Feature: 6.0000 Shopping Cart

  Background: Setup
    Given Login page is opened
    Given Language "FR" is selected
    Given Confirm using cookies

  @6.0001
  @RunAll
  Scenario Outline: 6.0001 Check ability to Add Product to Cart from Products details page
    When Click on Global Search button in Header
    When Enter "<productName>" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "<productName>" from Products page
    When Select value "<qty>" from Qty drop-down on Products details page
    When Click on Add to Cart button on Products details page
    When Click on Cart button in header
    Then Check that Shopping Cart contains product
      | productCode     | <productCode>     |
      | productName     | <productName>     |
      | qty             | <qty>             |
      | price           | <price>           |
      | discountPrice   | <discountPrice>   |
      | statusNet       | <statusNet>       |
      | statusApotek    | <statusApotek>    |
      | promotionalText | <promotionalText> |
    Examples:
      | productCode | productName                                      | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML  | 1   | 59.9  |               | true      | true         |                 |
      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30             | 1   | 17.00 |               | true      | true         |                 |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 2   | 9.75  | 7.80          | true      | true         | -20%            |
#      | 3522141     | Biocure Long Action Megatone Energy Boost Comp 30 | 2   | 19.00 | 13.30         | true      | true         | -30%            |

  @6.0002
  @RunAll
  Scenario: 6.0002 Check inability add to Cart Unavailable product from Products page
    When Move to Products drop-down in header
    When Select "Home care and first aid" catagory and "Instruments" subcategory from header Products drop-downn
    Then Buy now button shouldn't be displayed for Products with status "Not available"

  @6.0003
  @RunAll
  Scenario: 6.0003 Check Mini - shopping cart
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 2   | 59.9  |               |
    Then Mini shopping Cart should be appeared for a few seconds
    Then The Product Counter on Shopping Cart button should be "2"
    Then Mini shopping Cart should contains products
      | productCode | productName                                     | qty | price | discountPrice |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 2   | 119.8 |               |

  @6.0004
  @RunAll
  Scenario: 6.0004 Check Order Summary section of the Shopping Cart
    When Add product to Shopping Cart
      | productCode | productName                                      | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML  | 1   | 59.9  |               | true      | true         |                 |
      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30             | 1   | 17.00 |               | true      | true         |                 |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 2   | 9.75  | 7.80          | true      | true         | -20%            |
#      | 3522141     | Biocure Long Action Megatone Energy Boost Comp 30 | 2   | 19.00 |               | true      | true         |                 |
    When Click on Cart button in header
    Then "Total products" of the Shopping Cart should be "sum(priceLine)"
    Then "Discount" of the Shopping Cart should be "sum((price-discountPrice)*qty)"
    Then "Total" of the Shopping Cart should be "Total products-Discount"

  @6.0005
  @RunAll
  Scenario: 6.0005 Check that products added to Cart are saved after Customer logout
    Given Clear Customer's shopping cart - email "lloydp2017@gmail.com" password "registered"
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 3   | 59.9  |               | true      | true         |                 |
    And Click on Logout button in Header menu
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Cart button in header
    Then Check that Shopping Cart contains products
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 3   | 59.9  |               | true      | true         |                 |

  @6.0006
  @RunAll
  Scenario: 6.0006 Check that Customer's carts are merged when Customer logged in during Checkout
    Given Clear Customer's shopping cart - email "lloydp2017@gmail.com" password "registered"
    When Click on New User/Login button
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               | true      | true         |                 |
    And Click on Logout button in Header menu
    When Add product to Shopping Cart
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               | true      | true         |                 |
    When Click on Cart button in header
    When Click on Checkout button in the Cart
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    Then Alert message "Your cart saved were merged on your profile and your current shopping cart." should be displayed
    Given Confirm using cookies
    When Click on Show checkout Cart button
    Then Checkout Cart should contains "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" with Qty 2

  @6.0007
  @RunAll
  Scenario: 6.0007 Check ability to Remove product from the Cart
    Given Clear Customer's shopping cart - email "lloydp2017@gmail.com" password "registered"
    Given Login page is opened
    Given Language "NL" is selected
    Given Confirm using cookies
    When Add product to Shopping Cart
      | productCode | productName                                       | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML   | 1   | 59.9  |               | true      | true         |                 |
      | 3037603     | ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML        | 1   | 13.99 |               | false     | true         |                 |
      | 2647584     | ABBOTT MAINTENANCE KIT FREESTYLE LITE ZORGTRAJECT | 1   | 85.75 |               | true      | true         |                 |
      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30              | 1   | 17.00 |               | true      | true         |                 |
      | 2613958     | ABOCA ARNICA ZALF BIO 50ML                        | 1   | 11.45 |               | true      | true         |                 |
      | 2277515     | ACA MASK SET VOLWASSENEN                          | 1   | 5.19  |               | true      | true         |                 |
    When Click on Cart button in header
    When Remove product from the Cart
      | productCode | productName                | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2613958     | ABOCA ARNICA ZALF BIO 50ML | 1   | 11.45 |               | true      | true         |                 |
    Then Alert message "The product has been removed from your cart" should be displayed
    Then Shopping Cart shouldn't contain products
      | productCode | productName                | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2613958     | ABOCA ARNICA ZALF BIO 50ML | 1   | 11.45 |               | true      | true         |                 |
    Then "Total" of the Shopping Cart should be "sum(priceLineWithDiscount)"
    When Click on Checkout button in the Cart
    When Expand bottom Checkout shopping cart
    When Remove product from the Cart
      | productCode | productName              | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2277515     | ACA MASK SET VOLWASSENEN | 1   | 5.19  |               | true      | true         |                 |
    Then Alert message "The product has been removed from your cart" should be displayed
    Then Shopping Cart shouldn't contain products
      | productCode | productName              | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2277515     | ACA MASK SET VOLWASSENEN | 1   | 5.19  |               | true      | true         |                 |
    Then "Total:" on the Checkout Order Summary should be "sum(LineTotal)"
    When Select Shipping method Send to address
    When Click on Next button on the first Step of Checkout
    When Select option Get address from your profile
    When Expand bottom Checkout shopping cart
    When Remove product from the Cart
      | productCode | productName                          | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30 | 1   | 17.00 |               | true      | true         |                 |
    Then Alert message "The product has been removed from your cart" should be displayed
    Then Shopping Cart shouldn't contain products
      | productCode | productName                          | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30 | 1   | 17.00 |               | true      | true         |                 |
    Then "Total:" on the Checkout Order Summary should be "sum(LineTotal)"
    When Enter email "lloydp2017@gmail.com" and password "registered" on Step 2 of the Checkout
    When Click on Further to the shipping and payment button
    When Expand bottom Checkout shopping cart
    When Remove product from the Cart
      | productCode | productName                                       | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2647584     | ABBOTT MAINTENANCE KIT FREESTYLE LITE ZORGTRAJECT | 1   | 85.75 |               | true      | true         |                 |
    Then Alert message "The product has been removed from your cart" should be displayed
    Then Shopping Cart shouldn't contain products
      | productCode | productName                                       | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 2647584     | ABBOTT MAINTENANCE KIT FREESTYLE LITE ZORGTRAJECT | 1   | 85.75 |               | true      | true         |                 |
    Then "Total:" on the Checkout Order Summary should be "sum(LineTotal)"
    When Select Delivery address "Avenue"
    When Click on Further button on Step 2
    When Expand bottom Checkout shopping cart
    When Remove product from the Cart
      | productCode | productName                                | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3037603     | ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML | 1   | 13.99 |               | false     | true         |                 |
    Then Alert message "The product has been removed from your cart" should be displayed
    Then Shopping Cart shouldn't contain products
      | productCode | productName                                | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3037603     | ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML | 1   | 13.99 |               | false     | true         |                 |
    Then "Total:" on the Checkout Order Summary should be "sum(LineTotal)"
    When Expand bottom Checkout shopping cart
    When Remove product from the Cart
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               | true      | true         |                 |
    Then The Product Counter on Shopping Cart button should be ""
    Then Page "BASE_URL/cart" should be opened
    Then Shopping cart should be displayed with the message "Your basket is empty"


