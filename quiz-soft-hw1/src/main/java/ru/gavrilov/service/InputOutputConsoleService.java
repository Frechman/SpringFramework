package ru.gavrilov.service;

import java.util.Scanner;

public class InputOutputConsoleService implements InputOutputService {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void outputData(String message) {
        System.out.println(message);
    }

    @Override
    public String inputData() {
        return sc.nextLine();
    }

    @Override
    public String ask(String question) {
        System.out.print(question);
        return inputData();
    }
}
