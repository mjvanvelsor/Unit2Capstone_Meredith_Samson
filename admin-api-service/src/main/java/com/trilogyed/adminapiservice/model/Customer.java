package com.trilogyed.adminapiservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Customer {
    private int customerId;
    @NotEmpty(message = "Please enter your first name")
    @Size(max = 50, message = "Firstname cannot exceed 50 characters")
    @Pattern(regexp = "[a-z-A-Z ]*", message = "Please check your first name for invalid characters")
    private String firstName;
    @NotEmpty(message = "Please enter your last name")
    @Size(max = 50, message = "Lastname cannot exceed 50 characters")
    @Pattern(regexp = "[a-z-A-Z ]*", message = "Please check your last name for invalid characters")
    private String lastName;
    @NotEmpty(message = "Please enter your street name")
    @Size(max = 50, message = "Street cannot exceed 50 characters")
    private String street;
    @NotEmpty(message = "Please enter your city name")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    @Pattern(regexp = "[a-z-A-Z ]*", message = "Please check your city name for invalid characters")
    private String city;
    @NotNull(message = "You are missing a zipcode in your order. Please ensure you have a valid zipcode.")
    @Size(min = 5, max = 10, message = "Please enter a valid zipcode with 5 to 10 digits")
    private String zip;
    @NotNull(message = "You are missing an e-mail address")
    @Size(max = 75, message = "E-mail address cannot exceed 75 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
          message = "Please check your e-mail address for invalid characters")
    private String email;
    @NotNull(message = "You are missing a phone number")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phone;

    public Customer() {
    }

    public Customer(int customerId, String firstName, String lastName, String street, String city, String zip, String email, String phone) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId &&
                firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                street.equals(customer.street) &&
                city.equals(customer.city) &&
                zip.equals(customer.zip) &&
                email.equals(customer.email) &&
                phone.equals(customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, street, city, zip, email, phone);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
