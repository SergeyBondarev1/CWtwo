package Service;

import model.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    private final  QuestionService questionService = new JavaQuestionService ();
    @BeforeEach
    void setUp() {
        questionService.add(new Question(" вопрос 1", " ответ 1"));
        questionService.add(new Question(" вопрос 2", " ответ 2"));
        questionService.add(new Question(" вопрос 3", " ответ 3"));
        questionService.add(new Question(" вопрос 4", " ответ 4"));
    }

    @AfterEach
    void afterEach(){
        Collection<Question> questions = questionService.getAll();
        questions.forEach(question -> questionService.remove(question));
    }

    @Test
    void addPositiveTest() {


        assertThat(questionService.getAll()).hasSize(4);
        Question expected5 = new Question(" вопрос 5"," ответ 5");
        Question expected6 = new Question(" вопрос 6"," ответ 6");
        assertThat(questionService.add(expected5.getQuestion(), expected5.getAnswer())).isEqualTo(expected5);
        assertThat(questionService.add(expected6)).isEqualTo(new Question(" вопрос 6"," ответ 6"));

        assertThat(questionService.getAll()).contains(expected5);
        assertThat(questionService.getAll()).contains(expected6);
        assertThat(questionService.getAll()).hasSize(6);

    }

    @Test
    void addNegativeTest() {
        Question expected5 = new Question(" вопрос 5"," ответ 5");
        questionService.add(expected5);
        assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add(expected5));

    }

    @Test
    void removePositiveTest() {
        Question expected1 = new Question(" вопрос 1", " ответ 1");
        assertThat(questionService
                .remove(expected1))
                .isEqualTo(expected1);
    }
    @Test
    void removeNegativeTest() {
        Question expected7 = new Question(" вопрос 7", " ответ 7");
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(expected7));

    }

    @Test
    void getAll() {
        Assertions.assertThat(questionService.getAll())
                .hasSize(4)
                .containsExactlyInAnyOrder(
                        new Question(" вопрос 1", " ответ 1"),
                        new Question(" вопрос 2", " ответ 2"),
                        new Question(" вопрос 3", " ответ 3"),
                        new Question(" вопрос 4", " ответ 4")

                );
    }

    @Test
    void getRandomQuestionTest (){
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }
}