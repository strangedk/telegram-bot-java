package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        var scanner = new Scanner(System.in);

        var rateData = CryptoPrice.spotPrice("BTC");
        var rate = rateData.getAmount().doubleValue();
        System.out.println("BTC Price is: " + rate);

        while (true) {
            System.out.println("How much dollars do you have?");
            var cash = scanner.nextDouble();

            if (cash <= 0) {
                break;
            }

            var result = cash / rate;
            System.out.println("Result = " + result);
        }
    }
}