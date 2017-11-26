package org.grokking.challenges.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vinhdp on 27/11/17.
 */

@Controller
public class ScoreboardController {

    @RequestMapping("/scoreboard")
    public String getScoreboard() {
        return "scoreboard";
    }
}
