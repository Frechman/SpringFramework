package ru.gavrilov.presentation;

public interface PresenterQuizService {

    void outputData(String message);

    String inputData();

    String ask(String question);

    String runTest();
}
