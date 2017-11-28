package org.grokking.challenges.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinhdp on 27/11/17.
 */

@Controller
public class ScoreboardController {

    @RequestMapping("/scoreboard")
    public String getScoreboard(Model model) {

        List<UserScore> lstScores = new ArrayList<UserScore>();
        lstScores.add(new UserScore("vinhdangphuc@gmail.com", 10, 1, 50));
        lstScores.add(new UserScore("vinhdangphuc1@gmail.com", 20, 2, 100));
        lstScores.add(new UserScore("vinhdangphuc2@gmail.com", 30, 3, 150));
        lstScores.add(new UserScore("vinhdangphuc3@gmail.com", 40, 4, 250));

        model.addAttribute("lstScores", lstScores);
        return "scoreboard";
    }
}

class UserScore {
    private String email;
    private int time;
    private int rank;
    private int score;

    public UserScore(String email, int time, int rank, int score) {
        this.email = email;
        this.time = time;
        this.rank = rank;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
