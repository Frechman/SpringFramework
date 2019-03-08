package ru.gavrilov.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (!Objects.equals(question, quiz.question)) return false;
        if (!Objects.equals(correctAnswer, quiz.correctAnswer))
            return false;
        return Objects.equals(answers, quiz.answers);
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (correctAnswer != null ? correctAnswer.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "question='" + question + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", answers=" + answers +
                '}';
    }
}
