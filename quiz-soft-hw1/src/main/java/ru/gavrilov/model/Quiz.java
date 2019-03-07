package ru.gavrilov.model;

import java.util.List;

public class Quiz {

    private final String question;
    private final Integer correctAnswer;
    private final List<String> answers;

    public Quiz(String question, Integer correctAnswer, List<String> answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return answers.get(correctAnswer);
    }

    public boolean isCorrectAnswer(Integer answer) {
        return correctAnswer.equals(answer);
    }

    public List<String> getAnswers() {
        return answers;
    }
}
