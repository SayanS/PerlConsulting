Feature: 5.0001 Products page

  Background: Setup
    Given Login page is opened
    Given Language "NL" is selected
    Given Confirm using cookies

  @5.0001
  @RunAll
  Scenario Outline: 5.0001 Check autocomplete functionality of Global search field
    When Click on Global Search button in Header
    When Enter "<text>" into Global search field
    Then The names of products in Autocomplete results should contains "<text>"
    Examples:
      | text                         |
      | BIOCURE LONG ACTION MEGATONE |
      | ALVITYL                      |

  @5.0002
  @RunAll
  Scenario Outline: 5.0002 Check that all appropriate features of Promotional product are displayed on Products page
    When Click on Global Search button in Header
    When Enter "<product name>" into Global search field
    When Click on Global Search button in Header
    Then The price of "<product name>" product should be price from Autocomplete results
    Then Discount price should be displayed for "<product name>" product
    Then Promotional text should be displayed for "<product name>" product
    Then Badge discount should be displayed for "<product name>" product
    Examples:
      | product name                                     |
      | Bibi Zuigfles Glas Mama Papa 250ml Verv. 1071075 |
#      | BIOCURE LONG ACTION MEGATONE COMP 30 |

  @5.0003
  @RunAll
  Scenario Outline: 5.0003 Check ability to open Product category via Products drop-down in Header
    When Move to Products drop-down in header
    When Select "<Category>" catagory and "<SubCategory>" subcategory from header Products drop-downn
    Then Title of the products page should be "<SubCategory>"
    Examples:
      | Category                | SubCategory |
      | Home care and first aid | Instruments |
      | Care and hygiene        | Lips        |

  @5.0004
  @RunAll
  Scenario: 5.0004 Check filter by price
    When Select "Products" item in Header menu
    When Select "20 - 30" option of Filter by Price
    Then "20 - 30" should be displayed in Applied facets section
    Then Products page should contains products with price within 20 - 30

  @5.0005
  @RunAll
  Scenario: 5.0005 Check ability to sort by Price
    When Select "Products" item in Header menu
    When Select option sort by "Price high - low"
    When Select option sort by "Price low - high"
    Then Products should be sorted by "Price low - high"
    When Navigate to Products page number "3"
    Then Products should be sorted by "Price low - high"
    When Navigate to Products page number "1"
    When Select option sort by "Price high - low"
    Then Products should be sorted by "Price high - low"
    When Navigate to Products page number "3"
    Then Products should be sorted by "Price high - low"

  @5.0006
  @RunAll
  Scenario: 5.0006 Check ability to sort by Product Name
    When Select "Products" item in Header menu
    When Select option sort by "Alphabetical"
    When Select option sort by "Alphabetical"
    Then Products should be sorted by "Alphabetical"
    When Navigate to Products page number "3"
    Then Products should be sorted by "Alphabetical"
    When Navigate to Products page number "1"
    When Select option sort by "Descending"
    Then Products should be sorted by "Descending"
    When Navigate to Products page number "3"
    Then Products should be sorted by "Descending"