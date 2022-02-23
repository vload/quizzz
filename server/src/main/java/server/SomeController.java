package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class SomeController {

    /**
     * Adding checkstyle
     * @return Adding checkstyle
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }

    /**
     * This returns a place-holder question when the user requests one.
     * @return JSON String with the question and image filename
     */
    @GetMapping("/api/question")
    @ResponseBody
    public String question() {
        return "{\"question\": \"What is a question?\", \"image\": \"image001.png\"}";
    }
}