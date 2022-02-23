package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SomeControllerTest {

    /**
     * tests the question method
     */
    @Test
    void question() {
        SomeController a = new SomeController();
        assertEquals("{question: \"What is a question?\", image: \"image001.png\"}", a.question());
    }
}