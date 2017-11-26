package org.challenges.grokking.Model;

/**
 * Created by daniel on 11/26/17.
 */
public class Player {
	private String username = "";
	private long score = 0;

	public Player(String username, long score){
		this.username = username;
		this.score = 0;
	}

	public long getScore(){
		return this.score;
	}

	public void setScore(long adjust) {
		this.score += adjust;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername(String username) {
		return username;
	}

}

