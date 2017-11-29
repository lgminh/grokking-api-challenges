package org.grokking.challenges.controller;

import org.grokking.challenges.model.Result;
import org.grokking.challenges.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vinhdp on 27/11/17.
 */

@Controller
public class ScoreboardController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/scoreboard")
    public String getScoreboard(Model model) {

        List<Result> lstScores = redisService.getUserScores();
        List<UserScore> results = lstScores.stream()
                .map(r -> new UserScore(r.getEmail(), 0, 0, r.getScore()))
                .collect(Collectors.toList());

        model.addAttribute("lstScores", results);
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
