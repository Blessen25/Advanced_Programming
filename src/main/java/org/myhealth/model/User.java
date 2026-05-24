package org.myhealth.model;

// This class is to store user information.
public class User {

    // Variables which are used to store user data.
    private int id;
    private String user_Name;
    private String password;
    private String first_Name;
    private String last_Name;

    public User(String user_Name, String password, String first_Name, String last_Name) {

        this.user_Name = user_Name;
        this.password = password;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
    }

    // To return id
    public int getID() {

        return id;
    }

    // To return UserName
    public String getUser_Name() {

        return user_Name;
    }

    // To return Password
    public String getPassword() {

        return password;
    }

    // To return FirstName
    public String getFirst_Name() {

        return first_Name;
    }

    // To return LastName
    public String getLast_Name() {

        return last_Name;
    }

    // Updates first name
    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    // Updates last name
    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }
}
