package org.grokking.challenges.model;

/**
 * Created by vinhdp on 26/11/17.
 */
public class Result {

    private String email;
    private int score;

    public Result(String email, int score) {
        this.email = email;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
