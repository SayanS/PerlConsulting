Feature: 3.0000 Shopping Cart

  @3.0001
  @RunAll
  Scenario: 3.0001 Check ability to add product into the Shopping cart from Product page
    Given Clear Customer's shopping cart - email "qavitusapotek@gmail.com" password "registered"
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice | discountPrice | statusNet | statusApotek | promotionalText               |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   | 139.00    |               | true      | true         |                               |
      | 939021     | Crescina Complete Treatment 1300 Woman 20 x 3.5 ml           | 2   | 1799.90   | 1259.93       | true      | true         | -30% Crescina to VI + Members |
    When Click on Cart button in Header
    Then Shopping Cart should contain all added products
    Then Check value of "Produkter" field in Order Overview section
    Then Check value of "Rabatt:" field in Order Overview section
    Then Check value of "Total" field in Order Overview section

  @3.0002
  @RunAll
  Scenario: 3.0002 Check that mini Shopping cart contains added to Cart products
    Given Clear Customer's shopping cart - email "qavitusapotek@gmail.com" password "registered"
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty | listPrice | discountPrice |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 2   | 139.00    |               |
      | 939021     | Crescina Complete Treatment 1300 Woman 20 x 3.5 ml           | 2   | 1799.90   | 1259.93       |
    When Move to Cart button in Header
    Then Mini shopping cart should contain added to Cart products

  @3.0003
  @RunAll
  Scenario: 3.0003 Ability to clear Customer's Shopping cart
    Given Clear Customer's shopping cart - email "qavitusapotek@gmail.com" password "registered"
    Given Home page is opened
    Given Confirm using cookies
    When Click on New User/Login button
    And Sign In with email "qavitusapotek@gmail.com" and password "registered"
    When Add product to Shopping Cart
      | itemNumber | productName                                                  | qty |
      | 999226     | Savon Liquide Terra Lemon Verbena - flytende håndsåpe 500 ml | 1   |
      | 983974     | Tufte herreboxer hawaii / blue S                             | 2   |
    When Click on Cart button in Header
    When Delete all products from the Shopping Cart
    Then Alert message "Produktet har blitt fjernet fra handlekurven" should be displayed
    Then "Din handlekurv er tom" message should be displayed in the Shopping Cart
    Then Cart count on the Header Cart button should be disappeared
    Then Click on "Fortsett å handle" button should open "/search/?text=983974" page

  @3.0004
  @RunAll
  Scenario: 3.0004 Inability add prescription product to Cart with qty 2
    Given Home page is opened
    Given Confirm using cookies
    When Add product to Shopping Cart
      | itemNumber | productName                       | qty | listPrice |
      | 042264     | Zonnic Munnpulv med mintsmak 2 mg | 2   | 89.9      |
    Then Alert message "Du har allerede lagt et lignende legemiddel i handlekurven. Myndighetene har bestemt at det kun er lov å kjøpe én pakning av slike legemidler. Har man behov for langvarig bruk bør man oppsøke lege." should be displayed
    Then Product Counter on the Shopping Cart button should be "1"


