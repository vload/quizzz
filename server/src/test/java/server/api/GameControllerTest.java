package server.api;

import commons.Activity;
import commons.Question;
import commons.Submission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import server.database.MockActivityRepository;
import server.server_classes.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GameControllerTest {

    private Map<Long, AbstractGame> games;
    private GameController sut;
    @BeforeEach
    void init() {
        Activity a1 = new Activity(
                "1","examplePath",
                "Activviy1",23.4,
                "www.exam.com");

        Activity a2 = new Activity(
                "2","examplePath",
                "Activity2",92.5,
                "www.higher.com");

        Activity a3 = new Activity(
                "3","examplePath",
                "Activity3",24.5,
                "www.need.com");
        List<Activity> activityTestList = List.of(a1,a2,a3);
        MockActivityRepository mockRepo = new MockActivityRepository();
        mockRepo.saveAll(activityTestList);

        QuestionGenerator mockQuestionGen = new QuestionGenerator(mockRepo,new Random(42));

        games = new HashMap<>();
        sut = new GameController(games,mockQuestionGen);
    }

    @Test
    void nullNameTest() {
        var r1 = sut.startSinglePlayer("");
        assertEquals(BAD_REQUEST,r1.getStatusCode());
        var r2 = sut.startMultiPlayer(null);
        assertEquals(BAD_REQUEST,r2.getStatusCode());
    }

    @Test
    void nullIDDelete() {
        var r1 = sut.deleteGame("");
        assertEquals(BAD_REQUEST,r1.getStatusCode());
        var r2 = sut.deleteGame(null);
        assertEquals(BAD_REQUEST,r2.getStatusCode());

    }

    @Test
    void generatingIDTestSingleplayer() {
        sut.startSinglePlayer("Alice");
        sut.startSinglePlayer("Bob");
        var response = sut.startSinglePlayer("Pac");
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(2L,response.getBody());
    }

    @Test
    void startSinglePlayer() {
        sut.startSinglePlayer("Hudson");
        assertEquals(1,games.size());
        var game = games.get(0L);
        assertTrue(game instanceof SinglePlayerGame);
        assertEquals(((SinglePlayerGame) game).getPlayerName(),"Hudson");
    }

    @Test
    void deleteGame() {
        sut.startSinglePlayer("Bob");
        assertEquals(1,games.size());
        sut.startSinglePlayer("Alice");
        assertEquals(2,games.size());
        sut.deleteGame("0");
        assertEquals(1,games.size());
        SinglePlayerGame aliceGame = (SinglePlayerGame) games.get(1L);
        assertEquals("Alice",aliceGame.getPlayerName());
    }

    @Test
    void getQuestions() {
        sut.startSinglePlayer("Bob");
        assertEquals(20,sut.getQuestions(String.valueOf(0)).size());
        sut.getNextQuestion(String.valueOf(0));
        assertEquals(19,sut.getQuestions(String.valueOf(0)).size());
        sut.startSinglePlayer("Hannah");
        assertEquals(20,sut.getQuestions(String.valueOf(1)).size());
    }

    @Test
    void getNextQuestion() {
        var r1 = sut.getNextQuestion("0");
        assertEquals(BAD_REQUEST,r1.getStatusCode());
        sut.startSinglePlayer("Hannah");
        var r2 = sut.getNextQuestion("0");
        assertNotNull(r2.getBody());
    }

    @Test
    void validateAnswer() {
        sut.startSinglePlayer("Tyrone");
        List<Question> questionsToBeAsked = sut.getQuestions("0");
        Question firstQuestion = questionsToBeAsked.get(0);
        assertEquals(ResponseEntity.badRequest().build(),
                sut.validateAnswer(new Submission("Test",8.3),"0"));

        Question q1 = sut.getNextQuestion("0").getBody();
        assertEquals(firstQuestion,q1);
        assertEquals(0L,sut.validateAnswer(
                new Submission(
                        firstQuestion.getCorrectAnswer() + 1,2.8),"0").getBody());

        assertEquals(0L,sut.validateAnswer(
                new Submission(
                        firstQuestion.getCorrectAnswer() + 1,10.1),"0").getBody());

        assertNotEquals(0L,sut.validateAnswer(
                new Submission(firstQuestion.getCorrectAnswer(),7.7),"0").getBody());
    }

    @Test
    void startMultiPlayer() {

    }

    @Test
    void startMultiPlayerIP() {
    }


}