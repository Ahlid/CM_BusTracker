package com.example.pcts.bustracker.Lists;

/**
 * Created by pcts on 12/22/2016.
 */

public class StandartSpinner {

    public int id;
    public String name;


    public StandartSpinner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString()
    {
        return name;
    }
}
