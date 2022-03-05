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
package server.database;

import commons.Question;
import commons.Activity;
import commons.QuestionType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.TestQuestionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionRepoTest {

    private TestQuestionRepository repo;
    private Question q1, q2, q3, q4;

    /**
     * Sets up all the variables for the tests (even for upcoming tests may we decide to add some)
     */
    @BeforeEach
    public void setup() {
        repo = new TestQuestionRepository();
        Activity a1 = new Activity("activity 1", 50, "facebook.com");
        Activity a2 = new Activity("activity 2", 60, "twitter.com");
        Activity a3 = new Activity("activity 3", 70, "google.com");
        Activity a4 = new Activity("activity 4", 80, "youtube.com");
        Set<Activity> activitySet = new HashSet<>();
        activitySet.add(a1);
        activitySet.add(a2);
        activitySet.add(a3);
        Set<Activity> activitySet2 = new HashSet<>();
        activitySet2.add(a4);
        q1 = new Question("To be or not to be?", activitySet, QuestionType.MC);
        q2 = new Question("To be or not to be?2", activitySet2, QuestionType.ESTIMATE);
        q3 = new Question("Multiple choice question?", activitySet, QuestionType.MC);
        q4 = new Question("Estimate question?", activitySet2, QuestionType.ESTIMATE);
    }

    /**
     * Tests the save method of QuoteRepository
     */
    @Test
    public void testSave(){
        repo.save(q3);
        assertEquals("save", repo.calledMethods.get(repo.calledMethods.size()-1));
        assertEquals(q3, repo.questions.get(0));
    }

    /**
     * Tests the saveAll method of QuoteRepository
     */
    @Test
    public void testSaveAll(){
        List<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        repo.saveAll(questions);
        assertEquals("save", repo.calledMethods.get(0));
        assertEquals("save", repo.calledMethods.get(1));
        assertEquals("save", repo.calledMethods.get(2));
        assertEquals(q1, repo.questions.get(0));
        assertEquals(q2, repo.questions.get(1));
        assertEquals(q3, repo.questions.get(2));
    }

    /**
     * Tests the delete method of QuoteRepository
     */
    @Test
    public void testDelete(){
        List<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        repo.saveAll(questions);
        repo.delete(q1);
        assertEquals(1, repo.count());
    }

}