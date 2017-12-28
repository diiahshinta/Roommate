package com.example.diahshintadewi.roommate.account;

import java.io.Serializable;

/**
 * Created by Diah Shinta Dewi on 12/13/2017.
 */

public class User  implements Serializable{
    private String name, phone;
    public User(){

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
