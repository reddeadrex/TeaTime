package com.example.albert;

/*
 * Created by Albert on 6/5/2017.
 */

public class User
{
    public String name;
    public String email;

    public User()
    {

    }
    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
