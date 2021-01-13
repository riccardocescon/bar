package com.keyautomation.mybar;

import java.util.Date;

public abstract class Waiter extends  AutoId{

    private long id;
    private String name, password;
    private Date born, begin_working;
    private float salary;

    public Waiter(String name, String password, Date born, Date begin_working, float salary){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.name = name;
        this.password = password;
        this.born = born;
        this.begin_working = begin_working;
        this.salary = salary;
    }

}
