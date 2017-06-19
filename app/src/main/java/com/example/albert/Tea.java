package com.example.albert;

/*
 * Created by Albert on 6/6/2017.
 */

public class Tea
{
    private String name;
    private String detail;

    public Tea()
    {

    }

    public Tea(String name, String detail)
    {
        this.name = name;
        this.detail = detail;
    }

    public Tea(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
