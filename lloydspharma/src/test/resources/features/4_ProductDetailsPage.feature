Feature: 4.0000 Product Details page

  Background: Setup
    Given Login page is opened
    Given Language "NL" is selected
    Given Confirm using cookies

  @4.0001
  @RunAll
  Scenario: 4.0001 Check ability to open Product details page from Products page
    When Click on Global Search button in Header
    When Enter "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" from Products page
    Then Product details page should be opened with product
      | productCode | productName                                     | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3286523     | ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML | 1   | 59.9  |               | true      | true         |                 |

  @4.0002
  @RunAll
  Scenario: 4.0002 Check ability to open Product Details page from Autocomplete results of Global search field
    When Click on Global Search button in Header
    When Enter "ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML" into Global search field
    When Select "ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML" product from Autocomplete results of Global search field
    Then Product details Page should be opened with title "ALVITYL MULTIVITAMINEN DRINKB.OPL FL 150ML"

  @4.0003
  @RunAll
  Scenario: 4.0003 Check that all appropriate features of Promotional product are displayed on Products details page
    When Click on Global Search button in Header
    When Enter "Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075" from Products page
    Then Product details page should be opened with product
      | productCode | productName                                      | qty | price | discountPrice | statusNet | statusApotek | promotionalText |
      | 3092251     | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 | 1   | 9.75  | 7.80          | true      | true         | -20%            |
#      | 3522166     | BIOCURE LONG ACTION MEGATONE COMP 30 | 1   | 17.00 | 11.90         | true      | true         | -30%            |
    Then Badge discount should be displayed on Products details page for promotional product

  @4.0004
  @RunAll
  Scenario: 4.0004 Check ability to search Pharmacies by post code
    When Click on Global Search button in Header
    When Enter "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" from Products page
    When Enter "6060" into Search Pharmacies field of Product details page
    When Click on Find Pharmacies button on Product details page
    Then First Pharmacy in Search results should contains "6060" value in Address column

  @4.0005
  @RunAll
  Scenario: 4.0005 Check ability to search Pharmacies by Name
    When Click on Global Search button in Header
    When Enter "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "ACAR acarien UP KIT DUO 2 TEXTILES + VAPO 100ML" from Products page
    When Enter "Soleilmont" into Search Pharmacies field of Product details page
    When Click on Find Pharmacies button on Product details page
    Then Pharmacies in Search results should contains "Soleilmont" value in Store column

  @4.0006
  @RunAll
  Scenario: 4.0006 Check inability add to Cart Unavailable product from Product Details page
    When Click on Global Search button
    When Enter "ATTENDS SOFT 3 EXTRA PLUS INLEG ANATOM. 1X10" into Global search field
    When Click on Global Search button in Header
    When Open Product details page of "ATTENDS SOFT 3 EXTRA PLUS INLEG ANATOM. 1X10" from Products page
    Then Inactive Out of Stock button should be displayed instead of Add to Cart
    When Click on Cart button in header
    Then Cart should by empty

  @4.0007
  @RunAll
  Scenario: 4.0007 Check ability to open Larger image on Product Details page
    When Open Product details page of product "2277515"
    When Click On Larger image button
    Then Large image of product with code "2277515" should be displayed
