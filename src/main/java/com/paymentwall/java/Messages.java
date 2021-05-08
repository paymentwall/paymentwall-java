package com.paymentwall.java;

import java.util.Map;

public abstract class Messages {
    /**
     * Brick API properties
     */
    protected static final String PROP_MESSAGE = "message";
    protected static final String PROP_SUCCESS = "success";
    protected static final String PROP_ERROR = "error";
    protected static final String PROP_CODE = "code";
    protected static final String PROP_TYPE = "type";
    protected static final String PROP_TOKEN = "token";
    protected static final String PROP_LAST4 = "last4";
    protected static final String PROP_EXPIRATION_MONTH = "exp_month";
    protected static final String PROP_EXPIRATION_YEAR = "exp_year";
    protected static final String PROP_EXPIRATION = "expires_in";
    protected static final String PROP_ISTEST = "test";
    protected static final String PROP_ISACTIVE = "active";
    protected static final String PROP_ISTRIAL = "trial";
    protected static final String PROP_ISEXPIRED = "expired";
    protected static final String PROP_OBJECT_CHARGE = "object";
    protected static final String PROP_ISCAPTURED = "captured";
    protected static final String PROP_RISK_STATUS = "risk";
    protected static final String PROP_ISREFUNDED = "refunded";
    protected static final String PROP_CARD_NO = "card";
    protected static final String PROP_SECURE = "secure";

    /**
     * Brick API response types
     */
    protected static final String RESPONSE_SUCCESS = "success";
    protected static final String RESPONSE_ERROR = "error";
    protected static final String RESPONSE_SECURE= "secure";

    /**
     * Brick API error messages
     */
    protected static final String VALUE_INTERNAL_ERROR = "Internal error";
    protected static final String VALUE_ERROR = "Error";

    /**
     * PW Local parameters names
     */
    protected static final String PARAM_SIG = "sig";
    protected static final String PARAM_SIGN = "sign";
    protected static final String PARAM_SIGN_VERSION = "sign_version";
    protected static final String PARAM_UID = "uid";
    protected static final String PARAM_TYPE = "type";
    protected static final String PARAM_CURRENCY = "currency";
    protected static final String PARAM_GOODSID = "goodsid";
    protected static final String PARAM_SLENGTH = "slength";
    protected static final String PARAM_SPERIOD = "speriod";
    protected static final String PARAM_REF = "ref";
    protected static final String PARAM_AMOUNT = "amount";
    protected static final String PARAM_CURRENCY_CODE = "currencyCode";
    protected static final String PARAM_AG_NAME = "ag_name";
    protected static final String PARAM_AG_EXTERNAL_ID = "ag_external_id";
    protected static final String PARAM_AG_TYPE = "ag_type";
    protected static final String PARAM_AG_PERIOD_L = "ag_period_length";
    protected static final String PARAM_AG_PERIOD_TYPE = "ag_period_type";
    protected static final String PARAM_AG_RECURRING = "ag_recurring";
    protected static final String PARAM_AG_TRIAL = "ag_trial";
    protected static final String PARAM_AG_P_TRIAL_EXTERNAL_ID = "ag_post_trial_external_id";
    protected static final String PARAM_AG_P_TRIAL_PERIOD_L = "ag_post_trial_period_length";
    protected static final String PARAM_AG_P_TRIAL_PERIOD_TYPE = "ag_post_trial_period_type";
    protected static final String PARAM_AG_P_TRIAL_NAME = "ag_post_trial_name";
    protected static final String PARAM_AG_P_TRIAL_AMOUNT = "post_trial_amount";
    protected static final String PARAM_AG_P_TRIAL_CURRENCY_CODE = "post_trial_currencyCode";
    protected static final String PARAM_EXTERNAL_IDS = "external_ids";
    protected static final String PARAM_PRICES = "prices";
    protected static final String PARAM_CURRENCIES = "currencies";
    protected static final String PARAM_NAMES = "names";

    /**
     * Pingback types verbal meaning
     */
    protected static final String PINGBACK_VERBAL_SUBSCRIPTION_CANCELLATION = "user_subscription_cancellation";
    protected static final String PINGBACK_VERBAL_SUBSCRIPTION_EXPIRED = "user_subscription_expired";
    protected static final String PINGBACK_VERBAL_SUBSCRIPTION_PAYMENT_FAILED = "user_subscription_payment_failed";

    /**
     * Brick API messages
     */
    protected static final String API_OBJECT_CHARGE = "charge";
    protected static final String API_OBJECT_SUBSCRIPTION = "subscription";
    protected static final String API_OBJECT_ONE_TIME_TOKEN = "token";
    protected static final String API_BRICK_URL = "/brick/";
    protected static final String API_ACTION_REFUND = "refund";
    protected static final String API_ACTION_CAPTURE = "capture";
    protected static final String API_ACTION_VOID = "void";
    protected static final String API_RISK_PENDING = "pending";

    /**
     * PW Local API entry point controllers
     */
    protected static final String CONTROLLER_PAYMENT_VIRTUAL_CURRENCY	= "ps";
    protected static final String CONTROLLER_PAYMENT_DIGITAL_GOODS = "subscription";
    protected static final String CONTROLLER_PAYMENT_CART	= "cart";

    /**
     *  Exception messages
     */
    protected static final String EXCEPTION_EMPTY_RESPONSE = "Empty response";
    protected static final String EXCEPTION_WRONG_SIGN = "Wrong signature";
    protected static final String EXCEPTION_IP_NOT_WHITELISTED = "IP address is not whitelisted";
    protected static final String EXCEPTION_MISSING_PARAMETERS = "Missing parameters";
    protected static final String EXCEPTION_ONLY_1_PRODUCT = "Only 1 product is allowed in flexible widget call";
    protected static final String EXCEPTION_WRONG_HEADER = "Header does not comply format 'key:value'";

    /**
     * Http request default included header and tokenization gateway url
     */
    protected static final String DEFAULT_REQUEST_HEADER = "User-Agent:Paymentwall Java Library v. ";
    protected static final String GATEWAY_TOKENIZATION_URL = "https://pwgateway.com/api/token";

    /**
     * Product types
     */
    public final static String TYPE_SUBSCRIPTION = "subscription";
    public final static String TYPE_FIXED = "fixed";
    /**
     * Product period types
     */
    public final static String PERIOD_TYPE_DAY = "day";
    public final static String PERIOD_TYPE_WEEK = "week";
    public final static String PERIOD_TYPE_MONTH = "month";
    public final static String PERIOD_TYPE_YEAR = "year";

    /**
     * User profile parameters
     */
    protected static final String USER_PROFILE_EMAIL = "email";
    protected static final String USER_PROFILE_REGISTRATION_DATE = "history[registration_date]";
    protected static final String USER_PROFILE_BIRTHDAY = "customer[birthday]";
    protected static final String USER_PROFILE_SEX = "customer[sex]";
    protected static final String USER_PROFILE_USERNAME = "customer[username]";
    protected static final String USER_PROFILE_FIRSTNAME = "customer[firstname]";
    protected static final String USER_PROFILE_LASTNAME = "customer[lastname]";
    protected static final String USER_PROFILE_CITY = "customer[city]";
    protected static final String USER_PROFILE_STATE = "customer[state]";
    protected static final String USER_PROFILE_ADDRESS = "customer[address]";
    protected static final String USER_PROFILE_COUNTRY = "customer[country]";
    protected static final String USER_PROFILE_ZIP = "customer[zip]";
    protected static final String USER_PROFILE_MEMBERSHIP = "history[membership]";
    protected static final String USER_PROFILE_MEMBERSHIP_DATE = "history[membership_date]";
    protected static final String USER_PROFILE_REGISTRATION_COUNTRY = "history[registration_country]";
    protected static final String USER_PROFILE_REGISTRATION_IP = "history[registration_ip]";
    protected static final String USER_PROFILE_REGISTRATION_EMAIL = "history[registration_email]";
    protected static final String USER_PROFILE_REGISTRATION_EMAIL_VERIFIED = "history[registration_email_verified]";
    protected static final String USER_PROFILE_REGISTRATION_NAME = "history[registration_name]";
    protected static final String USER_PROFILE_REGISTRATION_LASTNAME = "history[registration_lastname]";
    protected static final String USER_PROFILE_REGISTRATION_SOURCE = "history[registration_source]";
    protected static final String USER_PROFILE_LOGINS_NUMBER = "history[logins_number]";
    protected static final String USER_PROFILE_PAYMENTS_NUMBER = "history[payments_number]";
    protected static final String USER_PROFILE_PAYMENTS_AMOUNT = "history[payments_amount]";
    protected static final String USER_PROFILE_FOLLOWERS = "history[followers]";
    protected static final String USER_PROFILE_MESSAGE_SENT = "history[messages_sent]";
    protected static final String USER_PROFILE_MESSAGE_SENT_LAST_24H = "history[messages_sent_last_24hours]";
    protected static final String USER_PROFILE_MESSAGE_RECEIVED = "history[messages_received]";
    protected static final String USER_PROFILE_INTERACTIONS = "history[interactions]";
    protected static final String USER_PROFILE_INTERACTIONS_LAST_24H = "history[interactions_last_24hours]";
    protected static final String USER_PROFILE_RISK_SCORE = "history[risk_score]";
    protected static final String USER_PROFILE_COMPLAINTS = "history[complaints]";
    protected static final String USER_PROFILE_WAS_BANNED = "history[was_banned]";
    protected static final String USER_PROFILE_DELIVERED_PRODUCTS = "history[delivered_products]";
    protected static final String USER_PROFILE_CANCELED_PAYMENTS = "history[cancelled_payments]";
    protected static final String USER_PROFILE_RATING = "history[customer_rating]";
    protected static final String USER_PROFILE_REGISTRATION_AGE = "history[registration_age]";
    protected static final String USER_PROFILE_ENABLE_3D_SECURE = "3dsecure";

    public static String val(Object object) {
        if(object == null) throw new NullPointerException("Value must be non null");
        if(object instanceof String) return (String) object;
        else if(object instanceof Integer
                || object instanceof Float
                || object instanceof Double
                || object instanceof Long) {
            return String.valueOf(object);
        } else if(object instanceof Boolean) {
            if((Boolean) object) {
                return "1";
            } else {
                return "0";
            }
        } else {
            throw new IllegalArgumentException("Data type is not accepted");
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.isEmpty());
    }
}