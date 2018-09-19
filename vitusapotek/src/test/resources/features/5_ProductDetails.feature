Feature: 5.0000 ProductDetails

  Background: Setup
    Given Home page is opened
    Given Confirm using cookies

  @5.0001
  @RunAll
  Scenario: 5.0001 Check ability to open Product details page from Products page
    When Global Search for product
      | itemNumber | productName                                                  | qty |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   |
    When Click on Product name "Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml"
    Then Product details page should be be opened with
      | itemNumber | productName                                                  | qty | listPrice | discountPrice | statusNet | statusApotek | promotionalText |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |               | true      | true         |                 |

  @5.0002
  @RunAll
  Scenario: 5.0002 Check ability to Add Product to Cart from Product details page
    When Global Search for product
      | itemNumber | productName                                                  | qty |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   |
    When Click on Product name "Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml"
    When On Product details page select qty "1"
    When On Product details page click on Add to cart button
    When Click on Cart button in Header
    Then Shopping Cart should contain all added products

  @5.0003
  @RunAll
  Scenario: 5.0003 Check ability to search Pharmacies by zip code on Product details page
    When Global Search for product
      | itemNumber | productName                                                  | qty |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   |
    When Click on Product name "Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml"
    When Enter "0250" into Search Pharmacies field of Product details page
    When Click on Find Pharmacies button on Product details page
    Then First Pharmacy in Search results should contains "0250" value in "Adresse" column

  @5.0004
  @RunAll
  Scenario: 5.0004 Check ability to search Pharmacies by City on Product details page
    When Global Search for product
      | itemNumber | productName                                                  | qty |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   |
    When Click on Product name "Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml"
    When Enter "Oslo" into Search Pharmacies field of Product details page
    When Click on Find Pharmacies button on Product details page
    Then Pharmacies in Search results should contains "Oslo" value in "Adresse" column
