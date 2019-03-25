package ru.gavrilov.service;

public interface InputOutputService {

    void outputData(String message);

    String inputData();

    String ask(String question);
}
