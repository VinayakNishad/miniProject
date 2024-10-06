package com.example.aquaadventure;

public class UserModel {
    String fullName;
    String address;
    String phone;
    String email;
    String password;
    UserModel(String fullName,String address,String phone,String email,String password){
        fullName =  this.fullName;
        address = this.address;
        phone = this.phone;
        email = this.email;
        password=this.password;
    }
    UserModel(){

    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
