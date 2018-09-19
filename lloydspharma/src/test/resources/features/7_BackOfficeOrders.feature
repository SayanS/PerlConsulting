Feature: 7.0000 Back Office Orders

  Background: Setup
    Given Back Office Login page is opened
    When Login to Back Office user "lpbefulfillment" and password "Qwerty_123456"

  @7.0001
  @RunAll
  Scenario: 7.0001 Check that created Orders are appeared in Back Office
    When In Back Office search for menu item "All orders"
    Then "Order #" column of Search results in Back Office should contains All created Orders Number
    When Back Office Logout

  @7.0002
  @RunAll
  Scenario Outline: 7.0002 Check that "Print pick list" document of "Prescription reserve" orders contains Order number
    When In Back Office search for menu item "Prescription reserve"
    When Search for Order created by "<testID>"
    When Click on Order in Search Results
    When Click on "Print picklist" button in Back Office
    Then Print picklist document should contains Order Number created by Test
    When Back Office Logout
    Examples:
      | testID |
      | 3.0001 |
      | 3.0002 |
      | 3.0003 |

  @7.0003
  @RunAll
  Scenario Outline: 7.0003 Check that "Print pick list" and "Pack slip" documents of "To Be Picked Up" orders contains Order number
    When In Back Office search for menu item "To Be Picked Up"
    When Search for Order created by "<testID>"
    When Click on Order in Search Results
    When Click on "Print picklist" button in Back Office
    Then Print picklist document should contains Order Number created by Test
    When Click on "Pack Slip" button in Back Office
    Then Pack Slip document should contains Order Number created by Test
    When Back Office Logout
    Examples:
      | testID |
      | 2.0007 |
      | 2.0009 |

  @7.0004
  @RunAll
  Scenario Outline: 7.0004 Check that "Split Order" form of "To Be Shipped" order contains Order number
    When In Back Office search for menu item "To Be Shipped"
    When Search for Order created by "<testID>"
    When Click on Order in Search Results
    When Click on "Shipping label" button in Back Office
    Then Shipping label document should contains Order Number created by Test
    When Click on "Split Order" button in Back Office
    Then Split Order form should contains Order Number created by Test
    When Back Office Logout
    Examples:
      | testID |
      | 2.0001 |
      | 2.0002 |
      | 2.0006 |
      | 2.0008 |

  @7.0005
  @RunAll
  Scenario: 7.0005 Check Statuses "New Delivery", "Picking", "On Hold", "Packed" and "Shipped" for "To Be Shipped" orders
    When In Back Office search for menu item "To Be Shipped"
    When Search for Order created by "2.0002"
    Then Status of "2.0002" order in Back Office should be "New Delivery"
    Then In Back Office appropriate Action buttons should be displayed
      | Cancel                  | true  |
      | Print picklist          | true  |
      | Set on hold             | true  |
      | Pack Slip               | true  |
      | Shipping label          | true  |
      | Split Order             | true  |
      | Confirm Ship            | false |
      | Reprint Consignor Label | false |
    When Click on "Print picklist" button in Back Office
    And Close Print popup in Back Office
    Then Status of "2.0002" order in Back Office should be "Picking"
    When Click on "Set on hold" button in Back Office
    Then Status of "2.0002" order in Back Office should be "On Hold"
    Then In Back Office appropriate Action buttons should be displayed
      | Cancel                  | true  |
      | Print picklist          | true  |
      | Set on hold             | false |
      | Pack Slip               | true  |
      | Shipping label          | true  |
      | Split Order             | true  |
      | Confirm Ship            | false |
      | Reprint Consignor Label | false |
    When Click on "Pack Slip" button in Back Office
    And Close Print popup in Back Office
    Then Status of "2.0002" order in Back Office should be "Packed"
    Then In Back Office appropriate Action buttons should be displayed
      | Cancel                  | true  |
      | Print picklist          | true  |
      | Set on hold             | true  |
      | Pack Slip               | true  |
      | Shipping label          | true  |
      | Split Order             | true  |
      | Confirm Ship            | true  |
      | Reprint Consignor Label | false |
    When Click on "Confirm Ship" button in Back Office
    When Click on "Yes" button on "Confirm Ship" pop-up
    Then Alert message should contains text "shipped successfully!"
    When In Back Office search for menu item "All orders"
    When Search for Order created by "2.0002"
    Then Status of "2.0002" order in Back Office should be "Shipped"
    Then In Back Office appropriate Action buttons should be displayed
      | Cancel                  | false |
      | Print picklist          | false |
      | Set on hold             | false |
      | Pack Slip               | true  |
      | Shipping label          | true  |
      | Split Order             | false |
      | Confirm Ship            | false |
      | Reprint Consignor Label | true  |
    When Back Office Logout
    When Login page is opened
    When Language "NL" is selected
    When Confirm using cookies
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Order status of "2.0002" in Order History should be "Completed"
#    Then Mail "lloydp2017@gmail.com" password "Rfhfylfitkm12r" should contains order confirmation from "<info@lloydspharma.be>" subject "Your order has been shipped"
#    Then Letter should contains order number "2.0002"

  @7.0006
  @RunAll
  Scenario: 7.0006 Check Statuses "New Delivery", "Cancelled" for "To Be Shipped" orders
    When In Back Office search for menu item "To Be Shipped"
    When Search for Order created by "2.0006"
    When Click on Order in Search Results
    Then Status of "2.0006" order in Back Office should be "New Delivery"
    When Click on "Cancel" button in Back Office
    When Click on "Yes" button on "Cancel" pop-up
    Then Alert message should contains text "Consignment cancelled"
    When In Back Office search for menu item "All orders"
    When Search for Order created by "2.0006"
    Then Status of "2.0006" order in Back Office should be "Cancelled"
    Then In Back Office appropriate Action buttons should be displayed
      | Cancel                  | false |
      | Print picklist          | false |
      | Set on hold             | false |
      | Pack Slip               | false |
      | Shipping label          | true  |
      | Split Order             | false |
      | Confirm Ship            | false |
      | Reprint Consignor Label | false |
    When Back Office Logout
    When Login page is opened
    When Language "NL" is selected
    When Confirm using cookies
    And Sign In with email "lloydp2017@gmail.com" and password "registered"
    When Click on Update Profile button in Header menu
    When Select Order History item from left side menu
    Then Order status of "2.0006" in Order History should be "Cancelled"

  @7.0007
  @RunAll
  Scenario: 7.0007 Check Statuses "New Pickup", "Picking", "On Hold", "Packed" and "Pickup complete" for "To Be Picked Up" orders
    When In Back Office search for menu item "To Be Picked Up"
    When Search for Order created by "2.0007"
    When Click on Order in Search Results
    Then Status of "2.0007" order in Back Office should be "Picking"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | true  |
      | Packed         | true  |
      | Not collected  | true  |
      | Pack Slip      | true  |
      | Confirm Pickup | false |
    When Click on "Print picklist" button in Back Office
    And Close Print popup in Back Office
    Then Status of "2.0007" order in Back Office should be "Picking"
    When Click on "Unable to pick" button in Back Office
    Then Status of "2.0007" order in Back Office should be "On Hold"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | false |
      | Packed         | true  |
      | Not collected  | true  |
      | Pack Slip      | true  |
      | Confirm Pickup | false |
    When Click on "Packed" button in Back Office
    Then Status of "2.0007" order in Back Office should be "Packed"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | true  |
      | Pack Slip      | true  |
      | Confirm Pickup | true  |
    When Click on "Pack Slip" button in Back Office
    And Close Print popup in Back Office
    Then Status of "2.0007" order in Back Office should be "Packed"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | true  |
      | Pack Slip      | true  |
      | Confirm Pickup | true  |
    When Click on "Confirm Pickup" button in Back Office
    When Click on "Yes" button on "Confirm Pickup" pop-up
    When In Back Office search for menu item "All orders"
    When Search for Order created by "2.0007"
    Then Status of "2.0007" order in Back Office should be "Pickup complete"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | false |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | false |
      | Pack Slip      | false |
      | Confirm Pickup | false |
    When Back Office Logout

  @7.0008
  @RunAll
  Scenario: 7.0008 Check Statuses "New Pickup", "Picking", "On Hold", "Packed" and "Pickup complete" for "Prescription reserve" orders
    When In Back Office search for menu item "Prescription reserve"
    When Search for Order created by "3.0001"
    When Click on Order in Search Results
    Then Status of "3.0001" order in Back Office should be "Picking"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | true  |
      | Packed         | true  |
      | Not collected  | true  |
      | Confirm Pickup | false |
    When Click on "Print picklist" button in Back Office
    And Close Print popup in Back Office
    Then Status of "3.0001" order in Back Office should be "Picking"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | true  |
      | Packed         | true  |
      | Not collected  | true  |
      | Confirm Pickup | false |
    When Click on "Unable to pick" button in Back Office
    Then Status of "3.0001" order in Back Office should be "On Hold"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | false |
      | Packed         | true  |
      | Not collected  | true  |
      | Confirm Pickup | false |
    When Click on "Packed" button in Back Office
    Then Status of "3.0001" order in Back Office should be "Packed"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | true  |
      | Confirm Pickup | true  |
    When Click on "Confirm Pickup" button in Back Office
    When Click on "Yes" button on "Confirm Pickup" pop-up
    When In Back Office search for menu item "All orders"
    When Search for Order created by "3.0001"
    Then Status of "3.0001" order in Back Office should be "Pickup complete"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | false |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | false |
      | Confirm Pickup | false |
    When Back Office Logout

  @7.0009
  @RunAll
  Scenario: 7.0009 Check Status "Cancelled" for "Prescription reserve" orders
    When In Back Office search for menu item "Prescription reserve"
    When Search for Order created by "3.0002"
    When Click on Order in Search Results
    Then Status of "3.0002" order in Back Office should be "Picking"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | true  |
      | Unable to pick | true  |
      | Packed         | true  |
      | Not collected  | true  |
      | Confirm Pickup | false |
    When Click on "Not collected" button in Back Office
    When Click on "Yes" button on "Not collected" pop-up
    Then Alert message should contains text "Consignment have been marked as 'not collected'"
    When In Back Office search for menu item "All orders"
    When Search for Order created by "3.0002"
    Then Status of "3.0002" order in Back Office should be "Cancelled"
    Then In Back Office appropriate Action buttons should be displayed
      | Print picklist | false |
      | Unable to pick | false |
      | Packed         | false |
      | Not collected  | false |
      | Confirm Pickup | false |
    When Back Office Logout

