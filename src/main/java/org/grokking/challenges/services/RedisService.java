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
	private static JedisPool pool;
	public RedisService(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(32);
		this.pool = new JedisPool(config, "localhost");
	}

	public boolean isplayerExist(String username){
		Jedis jedis = pool.getResource();
		if (jedis.exists(username + "_list")) {
			boolean exists =  jedis.exists(username + "_list");
			jedis.close();
			return exists;

		} else {
			jedis.close();
			return false;
		}
    }

    public boolean authenticateUserToken(String username,String token) {
		Jedis jedis = pool.getResource();
		boolean authenticated =  jedis.get(username + "_token").equals(token);
		jedis.close();
		return authenticated;
	}

	public Player savePlayer(String username, String token){
		Jedis jedis = pool.getResource();
		Player player = new Player(username, 0, token);
		jedis.sadd(username + "_list", "");
		jedis.zadd("ranking", 0, username);
		jedis.set(username + "_token", token);
		jedis.close();
		return player;
	}

	public boolean checkanswerExist(String username,Long answer){
		Jedis jedis = pool.getResource();
		return jedis.sismember( username + "_list", answer.toString());
	}

	public void updatescorePlayer(String username, Long[] answer){
		Jedis jedis = pool.getResource();
		double score = jedis.zscore("ranking", username);
		//duplicate submit
		int answer_length = answer.length <= 100 ? answer.length : 100;
		for(int i = 0; i <  answer_length; i++) {
			if (jedis.sismember( username + "_list", answer.toString())) {
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
		jedis.close();
	}
}
