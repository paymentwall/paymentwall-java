package com.paymentwall.java;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoahieu on 09/01/17.
 */
public class UserProfile extends Messages{
    protected String email;
    protected Long registrationDate;
    protected Long birthday;
    protected String sex;
    protected String username;
    protected String firstname;
    protected String lastname;
    protected String city;
    protected String state;
    protected String address;
    protected String country;
    protected String zip;
    protected String membership;
    protected Long membershipDate;
    protected String registrationCountry;
    protected String registrationIp;
    protected String registrationEmail;
    protected Boolean registrationEmailVerified;
    protected String registrationName;
    protected String registrationLastname;
    protected String registrationSource;
    protected Integer loginsNumber;
    protected Integer paymentsNumber;
    protected Double paymentsAmount;
    protected Integer followers;
    protected Integer messageSent;
    protected Integer messageSentLast24h;
    protected Integer messageReceived;
    protected Integer interactions;
    protected Integer interactionsLast24h;
    protected Float riskScore;
    protected Integer complaints;
    protected Boolean wasBanned;
    protected Integer deliveredProducts;
    protected Integer canceledPayments;
    protected Float rating;
    protected Integer registrationAge;
    protected Boolean enable3dSecure;

    public UserProfile() {
    }

    public UserProfile(String email, Long registrationDate) {
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public Long getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Long membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getRegistrationCountry() {
        return registrationCountry;
    }

    public void setRegistrationCountry(String registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public Boolean getRegistrationEmailVerified() {
        return registrationEmailVerified;
    }

    public void setRegistrationEmailVerified(Boolean registrationEmailVerified) {
        this.registrationEmailVerified = registrationEmailVerified;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getRegistrationLastname() {
        return registrationLastname;
    }

    public void setRegistrationLastname(String registrationLastname) {
        this.registrationLastname = registrationLastname;
    }

    public String getRegistrationSource() {
        return registrationSource;
    }

    public void setRegistrationSource(String registrationSource) {
        this.registrationSource = registrationSource;
    }

    public Integer getLoginsNumber() {
        return loginsNumber;
    }

    public void setLoginsNumber(Integer loginsNumber) {
        this.loginsNumber = loginsNumber;
    }

    public Integer getPaymentsNumber() {
        return paymentsNumber;
    }

    public void setPaymentsNumber(Integer paymentsNumber) {
        this.paymentsNumber = paymentsNumber;
    }

    public Double getPaymentsAmount() {
        return paymentsAmount;
    }

    public void setPaymentsAmount(Double paymentsAmount) {
        this.paymentsAmount = paymentsAmount;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(Integer messageSent) {
        this.messageSent = messageSent;
    }

    public Integer getMessageSentLast24h() {
        return messageSentLast24h;
    }

    public void setMessageSentLast24h(Integer messageSentLast24h) {
        this.messageSentLast24h = messageSentLast24h;
    }

    public Integer getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(Integer messageReceived) {
        this.messageReceived = messageReceived;
    }

    public Integer getInteractions() {
        return interactions;
    }

    public void setInteractions(Integer interactions) {
        this.interactions = interactions;
    }

    public Integer getInteractionsLast24h() {
        return interactionsLast24h;
    }

    public void setInteractionsLast24h(Integer interactionsLast24h) {
        this.interactionsLast24h = interactionsLast24h;
    }

    public Float getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Float riskScore) {
        this.riskScore = riskScore;
    }

    public Integer getComplaints() {
        return complaints;
    }

    public void setComplaints(Integer complaints) {
        this.complaints = complaints;
    }

    public Boolean getWasBanned() {
        return wasBanned;
    }

    public void setWasBanned(Boolean wasBanned) {
        this.wasBanned = wasBanned;
    }

    public Integer getDeliveredProducts() {
        return deliveredProducts;
    }

    public void setDeliveredProducts(Integer deliveredProducts) {
        this.deliveredProducts = deliveredProducts;
    }

    public Integer getCanceledPayments() {
        return canceledPayments;
    }

    public void setCanceledPayments(Integer canceledPayments) {
        this.canceledPayments = canceledPayments;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getRegistrationAge() {
        return registrationAge;
    }

    public void setRegistrationAge(Integer registrationAge) {
        this.registrationAge = registrationAge;
    }

    public Boolean getEnable3dSecure() {
        return enable3dSecure;
    }

    public void setEnable3dSecure(Boolean enable3dSecure) {
        this.enable3dSecure = enable3dSecure;
    }

    public Map<String, String> toParameters() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        if(!isNullOrEmpty(email)) parameters.put(USER_PROFILE_EMAIL, val(email));
        if(registrationDate!=null) parameters.put(USER_PROFILE_REGISTRATION_DATE, val(registrationDate));
        if(birthday!=null) parameters.put(USER_PROFILE_BIRTHDAY, val(birthday));
        if(!isNullOrEmpty(sex)) parameters.put(USER_PROFILE_SEX, val(sex));
        if(!isNullOrEmpty(username)) parameters.put(USER_PROFILE_USERNAME, val(username));
        if(!isNullOrEmpty(firstname)) parameters.put(USER_PROFILE_FIRSTNAME, val(firstname));
        if(!isNullOrEmpty(lastname)) parameters.put(USER_PROFILE_LASTNAME, val(lastname));
        if(!isNullOrEmpty(city)) parameters.put(USER_PROFILE_CITY, val(city));
        if(!isNullOrEmpty(state)) parameters.put(USER_PROFILE_STATE, val(state));
        if(!isNullOrEmpty(address)) parameters.put(USER_PROFILE_ADDRESS, val(address));
        if(!isNullOrEmpty(country)) parameters.put(USER_PROFILE_COUNTRY, val(country));
        if(!isNullOrEmpty(zip)) parameters.put(USER_PROFILE_ZIP, val(zip));
        if(!isNullOrEmpty(membership)) parameters.put(USER_PROFILE_MEMBERSHIP, val(membership));
        if(membershipDate!=null) parameters.put(USER_PROFILE_MEMBERSHIP_DATE, val(membershipDate));
        if(!isNullOrEmpty(registrationCountry)) parameters.put(USER_PROFILE_REGISTRATION_COUNTRY, val(registrationCountry));
        if(!isNullOrEmpty(registrationIp)) parameters.put(USER_PROFILE_REGISTRATION_IP, val(registrationIp));
        if(!isNullOrEmpty(registrationEmail)) parameters.put(USER_PROFILE_REGISTRATION_EMAIL, val(registrationEmail));
        if(registrationEmailVerified!=null) parameters.put(USER_PROFILE_REGISTRATION_EMAIL_VERIFIED, val(registrationEmailVerified));
        if(!isNullOrEmpty(registrationName)) parameters.put(USER_PROFILE_REGISTRATION_NAME, val(registrationName));
        if(!isNullOrEmpty(registrationLastname)) parameters.put(USER_PROFILE_REGISTRATION_LASTNAME, val(registrationLastname));
        if(!isNullOrEmpty(registrationSource)) parameters.put(USER_PROFILE_REGISTRATION_SOURCE, val(registrationSource));
        if(loginsNumber!=null) parameters.put(USER_PROFILE_LOGINS_NUMBER, val(loginsNumber));
        if(paymentsNumber!=null) parameters.put(USER_PROFILE_PAYMENTS_NUMBER, val(paymentsNumber));
        if(paymentsAmount!=null) parameters.put(USER_PROFILE_PAYMENTS_AMOUNT, val(paymentsAmount));
        if(followers!=null) parameters.put(USER_PROFILE_FOLLOWERS, val(followers));
        if(messageSent!=null) parameters.put(USER_PROFILE_MESSAGE_SENT, val(messageSent));
        if(messageSentLast24h!=null) parameters.put(USER_PROFILE_MESSAGE_SENT_LAST_24H, val(messageSentLast24h));
        if(messageReceived!=null) parameters.put(USER_PROFILE_MESSAGE_RECEIVED, val(messageReceived));
        if(interactions!=null) parameters.put(USER_PROFILE_INTERACTIONS, val(interactions));
        if(interactionsLast24h!=null) parameters.put(USER_PROFILE_INTERACTIONS_LAST_24H, val(interactionsLast24h));
        if(riskScore!=null) parameters.put(USER_PROFILE_RISK_SCORE, val(riskScore));
        if(complaints!=null) parameters.put(USER_PROFILE_COMPLAINTS, val(complaints));
        if(wasBanned!=null) parameters.put(USER_PROFILE_WAS_BANNED, val(wasBanned));
        if(deliveredProducts!=null) parameters.put(USER_PROFILE_DELIVERED_PRODUCTS, val(deliveredProducts));
        if(canceledPayments!=null) parameters.put(USER_PROFILE_CANCELED_PAYMENTS, val(canceledPayments));
        if(rating!=null) parameters.put(USER_PROFILE_RATING, val(rating));
        if(registrationAge!=null) parameters.put(USER_PROFILE_REGISTRATION_AGE, val(registrationAge));
        if(enable3dSecure!=null) parameters.put(USER_PROFILE_ENABLE_3D_SECURE, val(enable3dSecure));
        return parameters;
    }



}
