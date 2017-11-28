package org.grokking.challenges.services;

import org.grokking.challenges.model.Player;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by vinhdp on 26/11/17.
 */

@Service
public class RedisService {
	private static Jedis jedis;
	public RedisService(){
		jedis = new Jedis("localhost",6379);
	}

	public boolean isplayerExist(String username){
		return jedis.exists(username + "_list");
    }

    public boolean authenticateUserToken(String username,String token) {
		return jedis.get(username + "_token").equals(token);
	}

	public Player savePlayer(String username, String token){
		Player player = new Player(username, 0, token);
		jedis.sadd(username + "_list", "");
		jedis.zadd("ranking", 0, username);
		jedis.set(username + "_token", token);
		return player;
	}

	public boolean checkanswerExist(String username,Long answer){
		return jedis.sismember( username + "_list", answer.toString());
	}

	public void updatescorePlayer(String username, Long[] answer){
		double score = jedis.zscore("ranking", username);
		//duplicate submit
		for(int i = 0; i < answer.length; i++) {
			if (checkanswerExist(username, answer[i]) == true) {
				score -= 1;
			} else {
				// prime number
				if (jedis.sismember("primes", answer[i].toString())) {
					score += 1;
					jedis.sadd(username + "_list",answer[i].toString());
				} else {
					score -= 2;
				}
			}
			jedis.zadd("ranking", score, username);
		}
	}

}
