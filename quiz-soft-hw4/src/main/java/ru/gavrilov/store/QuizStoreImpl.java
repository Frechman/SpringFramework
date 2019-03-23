package ru.gavrilov.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.gavrilov.model.Quiz;

import java.io.*;
import java.util.*;

@Service
public class QuizStoreImpl implements QuizStore {

    private static final String COMMA_DELIMITER = ";";
    private final MessageSource messageSource;
    private List<Quiz> quizStore;

    @Autowired
    public QuizStoreImpl(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<Quiz> getAllData() {
        String file = messageSource.getMessage("test.pathFile", null, Locale.getDefault());
        try {
            quizStore = Collections.unmodifiableList(loadQuizFromCsvWithBuffReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizStore;
    }

    private List<Quiz> loadQuizFromCsvWithBuffReader(final String fileName) throws IOException {
        List<Quiz> list = new ArrayList<>();
        final InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                list.add(createQuizFromValues(values));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректные данные в csv. Заллогировал...");
        } catch (NullPointerException npe) {
            System.out.println("Файл не найден. Заллогировал...");
            throw npe;
        }
        return list;
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
