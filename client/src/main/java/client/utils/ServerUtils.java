/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


import client.scenes.MyMainCtrl;
import commons.Question;

import commons.Submission;
import org.glassfish.jersey.client.ClientConfig;

import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Adding checkstyle
     * @throws IOException
     */
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Adding checkstyle
     * @return Adding checkstyle
     */
    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
    }

    /**
     * Adding checkstyle
     * @param quote
     * @return Adding checkstyle
     */
    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    /**
     *
     * @return returns 20 questions
     */
    public Question getQuestions() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleplayer/questions/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Question.class);
    }
    /**
     * Gets the next question in the question set
     * @return Adding checkstyle
     * @param gameID
     */
    public Question getQuestion(String gameID) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleplayer/getquestion/"+ MyMainCtrl.gameID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Question.class);
       /* int random = ThreadLocalRandom.current().nextInt(0, 2);
        if (random == 1) {
            var activitySet = new HashSet<Activity>();
            activitySet.add(new Activity("1", "facebook.com/image1"
                    , "A1", 20, "facebook.com/source1"));
            activitySet.add(new Activity("2", "facebook.com/image2",
                    "A2", 30, "facebook.com/source2"));
            activitySet.add(new Activity("3", "facebook.com/image3",
                    "A3", 40, "facebook.com/source3"));
            return new Question("To be or not to be?", activitySet, QuestionType.MC, "2");
        } else {
            var activitySet = new HashSet<Activity>();
            activitySet.add(new Activity("1", "facebook.com/image1",
                                        "A1", 20, "facebook.com/source1"));
            return new Question("To be or not to be? (estimate)",
                                        activitySet, QuestionType.ESTIMATE, "20");
        }*/
    }


    /**
     * @param gameID
     * @param submission
     * @return validates an answer and returns the updated score
     */
    public Long validateQuestion(Submission submission,String gameID){
         return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleplayer/validate/" + MyMainCtrl.gameID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(submission, APPLICATION_JSON), Long.class);
    }


    /**
     *
     * @param name
     * @return returns and ID of the newly created game
     */
    public String createGame(String name){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/game/singleplayer/create/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(name, APPLICATION_JSON), String.class);
    }

}