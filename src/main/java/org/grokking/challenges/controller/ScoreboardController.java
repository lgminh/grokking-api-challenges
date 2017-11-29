package org.grokking.challenges.controller;

import org.grokking.challenges.model.Challenge;
import org.grokking.challenges.model.Result;
import org.grokking.challenges.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    ZoneId zone = ZoneId.of("GMT+0700");

    @RequestMapping("/scoreboard")
    public String getScoreboard(Model model) {

        Challenge c = redisService.getChallenge(1);

        long current = LocalDateTime.now()
                .atZone(zone).toEpochSecond() * 1000;

        long over = current - c.getStartTime();

        int percent = (int)(over * 1.0 / (c.getEndTime() - c.getStartTime()) * 100);
        if(percent < 0) {
            percent = 0;
        }

        if(percent > 100) {
            percent = 100;
        }

        List<Result> lstScores = redisService.getUserScores();
        List<UserScore> results = lstScores.stream()
                .map(r -> new UserScore(r.getEmail(), 0, 0, r.getScore()))
                .collect(Collectors.toList());

        model.addAttribute("challenge", c);
        model.addAttribute("lstScores", results);
        model.addAttribute("percent", percent);
        return "scoreboard";
    }

    @RequestMapping(value = "/create-challenge", produces = "application/json")
    @ResponseBody
    public Challenge createChallenge() {

        int duration = 90;
        long date = LocalDate.of(2017, 11, 29)
                .atStartOfDay(zone).toEpochSecond() * 1000;
        long startTime = LocalDateTime.of(2017, 11, 29, 22, 0, 0)
                .atZone(zone).toEpochSecond() * 1000;

        long endTime = startTime + duration * 60 * 1000;

        Challenge cha = new Challenge();
        cha.setId(1);
        cha.setName("Grokking Challenge Demo");
        cha.setDate(date);
        cha.setStartTime(startTime);
        cha.setEndTime(endTime);

        redisService.setChallenge(cha);

        return cha;
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
