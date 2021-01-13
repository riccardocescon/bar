package com.keyautomation.mybar;

public class AutoId {
    protected static long auto_id = 1;

    public static void setAuto_id(long id){
        System.out.println("current id : " + id);
        auto_id = id;
    }
}
