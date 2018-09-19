Feature: 4.0000 Products

  Background: Setup
    Given Home page is opened
    Given Confirm using cookies

  @4.0001
  @RunAll
  Scenario: 4.0001 Check ability navigate to product category via "Produkter" item in Main menu
    When Move to "Produkter" item in Main menu
    When Select product category "Allergi" and subcategory "Allergitabletter"
    Then The title of Products page should contain "Allergitabletter"
    Then The last breadcrumb link should contain "Allergitabletter"

  @4.0002
  @RunAll
  Scenario: 4.0002 Check Global search by product Item Number
    When Global Search for "999226"
    Then Search results should be opened with products
      | itemNumber | productName                                                  | qty | listPrice | discountPrice | statusNet | statusApotek | promotionalText |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |               | true      | true         |                 |

  @4.0003
  @RunAll
  Scenario: 4.0002 Check Global search by product multi Item Number
    When Global Search for "999226 939021"
    Then Search results should be opened with products
      | itemNumber | productName                                                  | qty | listPrice | discountPrice | statusNet | statusApotek | promotionalText                                   |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |               | true      | true         |                                                   |
      | 939021     | Crescina Complete Treatment 1300 Woman 20 x 3.5 ml           | 1   | 1799.90   |               | true      | true         | 30% discount to members of the VI + customer club |