package ru.gavrilov.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gavrilov.model.Quiz;

import java.io.*;
import java.util.*;

@Service
public class QuizStoreImpl implements QuizStore {

    private static final String COMMA_DELIMITER = ";";

    private final List<Quiz> quizStore;

    public QuizStoreImpl(@Value("${test.pathFile}") final String pathFile,
                         @Value("${test.locale}") final String locale) throws IOException {
        String file = String.format("%s%s.csv", pathFile, locale);
        quizStore = Collections.unmodifiableList(loadQuizFromCsvWithBuffReader(file));
    }

    @Override
    public List<Quiz> getAllData() {
        return quizStore;
    }

    private List<Quiz> loadQuizFromCsvWithBuffReader(final String fileName) throws IOException {
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

    private List<Quiz> loadQuizFromCsvWithScanner(final String fileName) throws IOException {
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
