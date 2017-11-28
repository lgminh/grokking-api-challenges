package org.grokking.challenges.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.omg.CORBA.SetOverrideTypeHelper;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by daniel on 11/26/17.
 */
public class Player {
	private String username = "";
	private long score = 0;
	private String token = "";
	public Player(String username, long score, String token){
		this.username = username;
		this.score = 0;
		this.token = token;
	}

	public Player(){

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

	public String getUsername() {
		return username;
	}

}

