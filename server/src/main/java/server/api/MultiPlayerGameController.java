package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.MultiPlayerGameService;

@RestController
@RequestMapping("/api/game/multiplayer")
public class MultiPlayerGameController {

    private final MultiPlayerGameService service;

    /**
     * Constructor for the MultiplayerGameController
     * 
     * @param service The service used to handle all business logic
     */
    @Autowired
    public MultiPlayerGameController(MultiPlayerGameService service) {
        this.service = service;
    }

}
