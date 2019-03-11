package ru.gavrilov.store;

import ru.gavrilov.model.Quiz;

import java.io.*;
import java.util.*;

public class QuizStoreImpl implements QuizStore {

    private static final String COMMA_DELIMITER = ";";

    private final List<Quiz> quizStore;

    public QuizStoreImpl(final String pathFile) throws IOException {
        quizStore = Collections.unmodifiableList(loadQuizFromCsvWithBuffReader(pathFile));
    }

    @Override
    public List<Quiz> getAllData() {
        return quizStore;
    }

    private List<Quiz> loadQuizFromCsvWithBuffReader(String fileName) throws IOException {
        List<Quiz> list = new ArrayList<>();
        final InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                try {
                    list.add(createQuizFromValues(values));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Некорректные данные в csv. Заллогировал...");
                }
            }
            return list;
        }
    }

    private List<Quiz> loadQuizFromCsvWithScanner(String fileName) throws IOException {
        List<Quiz> list = new ArrayList<>();
        String file = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile();
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNextLine()) {
                String[] values = getRecordFromLine(scanner.nextLine());
                list.add(createQuizFromValues(values));
            }
            return list;
        }
    }

    private Quiz createQuizFromValues(String[] values) {
        String question = values[0];
        Integer correctAnswer = Integer.valueOf(values[1]);
        List<String> answers = Arrays.asList(values[2], values[3], values[4], values[5]);
        return new Quiz(question, correctAnswer, answers);
    }

    private String[] getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values.toArray(new String[0]);
    }
}
