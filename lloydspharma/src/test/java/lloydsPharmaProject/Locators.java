package lloydsPharmaProject;

public interface Locators {

    // BASE PAGE
    String BASE_PAGE_SELECT_LANGUAGE="//input[@id='$Language']";
    String BASE_PAGE_HEADER_MENU_NEW_USER_LOGIN_BUTTON ="(.//a[@href='/login'])[1]";
    String BASE_PAGE_ALERTS=".//div[contains(@class,'alert')]//div[@class='container']";

    String BASE_PAGE_HEADER_MENU_LOGOUT_BUTTON ="(.//a[@href='/logout'])[1]";
    String BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON =".//nav[@class='secondary-navigation']//a[@href='/my-account/update-profile']";
    String BASE_PAGE_HEADER_MENU=".//nav[@class='main-navigation js-navigation']/ul/li/a[.='$Item']";

    String BASE_PAGE_GLOBAL_SEARCH_BUTTON="//li[@class='global-search']/button[@class='submit toggle-search']";
    String BASE_PAGE_GLOBAL_SEARCH_FIELD=".//input[@id='autocomplete-search']";
    String BASE_PAGE_GLOBAL_SEARCH_SHUT_SEARCH_FIELD=".//form[@id='autocomplete-form']//span[@class='input-group-btn cancel-search']/button[@type='submit']";
    String BASE_PAGE_HEADER_SHOPPING_CART_BUTTON=".//li[@class='cart js-blink-mini-cart-link']/a[@class='mini-cart-link refresh-blink']";
    String BASE_PAGE_HEADER_COUNTER_ON_SHOPPING_CART_BUTTON=".//span[@class='mini-cart-count js-blink-mini-cart-count']";
    String BASE_PAGE_HEADER_PRODACTS_DROP_DOWN=".//nav[@class='main-navigation js-navigation']/ul/li[1]";
    String BASE_PAGE_PRODUCTS_DROP_DOWN_CATEGORY =".//ul[contains(@class,'cd-dropdown')]//a[.='$Category']";

    //    LOGIN PAGE
    String LOGIN_PAGE_REGISTRATION_FORM=".//form[@id='pearlRegisterForm']";
    String LOGIN_PAGE_REGISTRATION_FORM_TITLE_DROP_DOWN=".//select[@id='profile.title']";
    String LOGIN_PAGE_REGISTRATION_FORM_FIRST_NAME=".//input[@id='register.firstName']";
    String LOGIN_PAGE_REGISTRATION_FORM_LAST_NAME=".//input[@id='register.lastName']";
    String LOGIN_PAGE_REGISTRATION_FORM_PHONE_NUMBER=".//input[@id='register.cellphone']";
    String LOGIN_PAGE_REGISTRATION_FORM_BUILDING=".//*[@id='register.building']";
    String LOGIN_PAGE_REGISTRATION_FORM_POBOX=".//input[@id='register.pobox']";
    String LOGIN_PAGE_REGISTRATION_FORM_ADDRESS=".//input[@id='register.line1']";
    String LOGIN_PAGE_REGISTRATION_FORM_POST_CODE=".//input[@id='register.postcode']";
    String LOGIN_PAGE_REGISTRATION_FORM_CITY=".//input[@id='register.townCity']";
    String LOGIN_PAGE_REGISTRATION_FORM_EMAIL=".//input[@id='register.email']";
    String LOGIN_PAGE_REGISTRATION_FORM_PASSWORD=".//input[@id='password']";
    String LOGIN_PAGE_REGISTRATION_FORM_CONFIRM_PASSWORD=".//input[@id='register.checkPwd']";
    String LOGIN_PAGE_REGISTRATION_FORM_BIRTHDAY=".//input[@id='register.birthDate']";
    String LOGIN_PAGE_REGISTRATION_FORM_ADDITIONAL_INFO=".//textarea[@id='additionalInfo']";

    String LOGIN_PAGE_REGISTRATION_FORM_CONSENT_FORNEWSLETTER_CHECKBOX=".//input[@id='register.consentForNewsletter']";
    String LOGIN_PAGE_REGISTRATION_FORM_SPECIAL_INTERESSEGROEP_DROP_DOWN=".//form[@id='pearlRegisterForm']//select[@id='groupSelect']";
    String LOGIN_PAGE_REGISTRATION_FORM_FAVORITE_PHARMACIES=".//form[@id='pearlLloydsBeRegisterForm']//input[@id='pharmacies-autocomplete']";
    String LOGIN_PAGE_REGISTRATION_FORM_CREATE_ACCOUNT_BUTTON=".//*[@id='pearlLloydsBeRegisterForm']//button[@type='submit']";
    String  LOGIN_PAGE_REGISTRATION_FORM_ERROR_MESSAGES=".//span[contains(@id,'.invalid.errors')]";

    String LOGIN_PAGE_LOGIN_FORM_EMAIL=".//input[@id='j_username']";
    String LOGIN_PAGE_LOGIN_FORM_PASSWORD=".//input[@id='j_password']";
    String LOGIN_PAGE_LOGIN_FORM_SIGN_IN_BUTTON=".//button[@id='loginBtnCheckout']";


    //    MY ACCOUNT PAGE
    String MY_ACCOUNT_PAGE_SIDE_MENU_UPDATE_PERSANAL_DETAILS=".//nav[@class='account-navigation sidebar-navigation']//a[@href='/my-account/update-profile']";
    String MY_ACCOUNT_PAGE_SIDE_MENU_UPDATE_EMAIL_ITEM=".//a[@href='/my-account/update-email']";
    String MY_ACCOUNT_PAGE_SIDE_MENU_MANAGE_DELIVERY_ADDRESS_ITEM=".//a[@href='/my-account/address-book']";

    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_TITLE_DROP_DOWN =".//select[@id='profile.title']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_FIRST_NAME =".//input[@id='profile.firstName']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_LAST_NAME =".//input[@id='profile.lastName']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_PHONE_NUMBER =".//input[@id='profile.mobileNumber']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_BIRTHDAY =".//input[@id='register.birthDate']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_ADDITIONAL_INFO =".//textarea[@id='additionalInfo']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_HAS_MEMBERSHIP_CHECKBOX =".//input[@id='_hasMembership']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_CONSENT_FORNEWSLETTER_CHECKBOX =".//*[@id='_consentForNewsletter']";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SPECIAL_INTERESSEGROEP_LABELS =".//ul[@id='groups-list']/li/label";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SPECIAL_FAVORITE_PHARMACIES =".//ul[@id='favorite-pharmacies']//label";
    String MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SAVE_BUTTON =".//*[@id='pearlLloydsBeUpdateProfileForm']/button";

    String MY_ACCOUNT_PAGE_EMAIL=".//input[@id='profile.email']";
    String MY_ACCOUNT_PAGE_UPDATE_EMAIL_EMAIL=".//input[@id='profile.email']";
    String MY_ACCOUNT_PAGE_UPDATE_EMAIL_REENTER_EMAIL=".//input[@id='profile.checkEmail']";
    String MY_ACCOUNT_PAGE_UPDATE_EMAIL_CHANGE_EMAIL_BUTTON ="//div[@class='form-actions']/button[@type='submit']";
    String MY_ACCOUNT_PAGE_UPDATE_EMAIL_PASSWORD=".//input[@id='profile.pwd']";

    String MY_ACCOUNT_PAGE_SPECIAL_INTERESSEGROEP_CHECKBOXES=".//ul[@id='groups-list']/li/input";
    String MY_ACCOUNT_PAGE_FAVORITE_PHARMACIES_FIELD=".//input[@id='pharmacies-autocomplete']";

    String MY_ACCOUNT_PAGE_ADDRESSES=".//ul[@class='account-addressbook-list']/li";

//  SEARCH RESULTS PAGE

    String SEARCH_RESULTS_PAGE_PRODUCT_ITEM=".//article[@class='product-item']";
    String SEARCH_RESULTS_PAGE_TITLE=".//div[@class='sort-refine-bar']/h1";

//  SHOPPING CART
    String SHOPPING_CART_MINI_CART_BODY=".//div[@class='mini-cart-body']";
    String SHOPPING_CART_CHECKOUT_BUTTON="(.//*[@id='site']//button[@data-checkout-url='/cart/checkout'])[1]";

//  CHECKOUT
    String CHECKOUT_SEND_BY_MAIL_CHECKBOX=".//input[@id='deliveryMethodShipment']/following-sibling::label";
    String CHECKOUT_GET_IN_PHARMACY_CHECKBOX=".//input[@id='input-pickupInStore']";
    String CHECKOUT_NEXT_BUTTON=".//button[@id='deliveryMethodSubmit']";
    String CHECKOUT_GET_ADDRESS_FROM_PROFILE_CHECKBOX=".//label[@for='checkoutLoginTypeLogin']";
    String CHECKOUT_EMAIL=".//input[@id='j_username']";
    String CHECKOUT_EMAIL_PASSWORD=".//input[@id='j_password']";
    String CHECKOUT_CONTINUE_WITHOUT_PROFILE_CHECKBOX=".//input[@id='checkoutLoginTypeGuest']";
    String CHECKOUT_FURTHER_SHIPPING_PAYMENT_BUTTON=".//button[@id='guest-form-btn']";

//    PRODUCT DETAILS PAGE
    String PRODUCT_DETAILS_PAGE_BUY_NOW_BUTTON=".//button[@id='addToCartButton']";
    String PRODUCT_DETAILS_PAGE_LARGER_IMAGE_BUTTON="(.//span[@class='streamline-plus']/ancestor::li)[1]";

//    PRODUCTS PAGE
    String PRODUCTS_PAGE_PRODUCTS_NAME=".//h2/a[@class='name']";
    String PRODUCTS_PAGE_PRODUCTS_PRICE=".//div[@class='price-panel']/*[1]";
}
