package ru.gavrilov.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
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
