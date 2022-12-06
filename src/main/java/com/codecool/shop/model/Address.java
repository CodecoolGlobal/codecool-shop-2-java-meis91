package com.codecool.shop.model;

public class Address {

    private String country;
    private String city;
    private String zipcode;
    private String address;
    private AddressType addressType;

    public Address(String country, String city, String zipcode, String address, AddressType addressType) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.address = address;
        this.addressType = addressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}
