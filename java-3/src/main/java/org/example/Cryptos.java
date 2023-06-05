package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public enum Cryptos {
    BTC("BTC"),
    ETH("ETH"),
    DOGE("DOGE");

    private String type;

    Cryptos(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}