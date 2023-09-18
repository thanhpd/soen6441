package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private List<Country> countriesOwned;
}
