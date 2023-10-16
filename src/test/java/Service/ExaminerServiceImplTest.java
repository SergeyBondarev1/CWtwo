package Service;

import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerServiceImpl;

    @BeforeEach
    public void setUp() {
        Collection<Question> questions = Stream.of(
                new Question("question text1", "answer text1"),
                new Question("question text2", "answer text2"),
                new Question("question text3", "answer text3"),
                new Question("question text4", "answer text4"),
                new Question("question text5", "answer text5")
        ).collect(Collectors.toSet());

        when(javaQuestionService.getAll()).thenReturn(questions);
    }

    @Test
    public void getQuestionsNegativeTest() {
        assertThrows(IncorrectAmountOfQuestionException.class,
                () -> examinerServiceImpl.getQuestions(6));
        assertThrows(IncorrectAmountOfQuestionException.class,
                () -> examinerServiceImpl.getQuestions(-1));
        assertThrows(IncorrectAmountOfQuestionException.class,
                () -> examinerServiceImpl.getQuestions(0));
    }

    @Test
    public void getQuestionsPositiveTest() {

        List<Question> questions = new ArrayList<>(javaQuestionService.getAll());

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(0),
                questions.get(1),
                questions.get(0),
                questions.get(2),
                questions.get(1)
        );
        assertThat(examinerServiceImpl.getQuestions(3))
                .containsExactly(questions.get(0), questions.get(1), questions.get(2));

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(4),
                questions.get(3),
                questions.get(2),
                questions.get(0),
                questions.get(1),
                questions.get(2)
        );
        assertThat(examinerServiceImpl.getQuestions(5))
                .containsExactly(questions.get(4), questions.get(3),
                        questions.get(2), questions.get(0), questions.get(1));
    }
}