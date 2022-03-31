package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.JokerUse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import server.services.MultiPlayerGameService;
import server.services.SinglePlayerGameService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JokerController.class)
class JokerControllerTest {
    MvcResult mainResult;

    @MockBean
    private SinglePlayerGameService singlePlayerGameService;

    @MockBean
    private MultiPlayerGameService multiPlayerGameService;

    @MockBean
    private MultiPlayerGameController multiPlayerGameController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() throws Exception {
        mainResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/game/singleplayer/create/")
                        .content(asJsonString("Test"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void useJokerMultiplayer() {
    }

    @Test
    void useJokerSingleplayer() throws Exception {
    }
}