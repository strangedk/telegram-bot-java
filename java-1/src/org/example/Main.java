package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World");
        System.out.println("How are you?");

        var scanner = new Scanner(System.in);
        var answer = scanner.nextLine();

        System.out.println("I like that you feel " + answer);
    }
}