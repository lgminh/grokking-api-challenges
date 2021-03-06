package org.grokking.challenges.services;

import org.grokking.challenges.model.Challenge;
import org.grokking.challenges.model.Player;
import org.grokking.challenges.model.Result;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by vinhdp on 26/11/17.
 */

@Service
public class RedisService {
	private JedisPool pool;

	public RedisService(){
		JedisPoolConfig conf = new JedisPoolConfig();
		conf.setMaxTotal(32);
		conf.setMaxIdle(512);
		conf.setMinIdle(64);
		conf.setMaxTotal(-1);
		conf.setBlockWhenExhausted(false);
		this.pool = new JedisPool(conf, "localhost");
	}

	public boolean isplayerExist(String username) {
		try (Jedis jedis = pool.getResource()) {
			if (jedis.exists(username + "_list")) {
				boolean exists = jedis.exists(username + "_list");
				return exists;

			} else {
				return false;
			}
		}
	}

    public boolean authenticateUserToken(String username,String token) {
		try (Jedis jedis = pool.getResource()) {
			boolean authenticated = jedis.get(username + "_token")
					.equals(token);
			return authenticated;
		}
	}

	public Player savePlayer(String username, String token){
		try (Jedis jedis = pool.getResource()) {
			Player player = new Player(username, 0, token);
			jedis.sadd(username + "_list", "");
			jedis.zadd("ranking", 0, username);
			jedis.set(username + "_token", token);
			return player;
		}
	}

	public boolean checkanswerExist(String username,Long answer){
		try (Jedis jedis = pool.getResource()) {
			boolean isExisted = jedis.sismember(username + "_list", answer.toString());
			return isExisted;
		}
	}

	public void updatescorePlayer(String username, Long[] answer){
		try (Jedis jedis = pool.getResource()) {
			double score = jedis.zscore("ranking", username);
			//duplicate submit
			int answer_length = answer.length <= 100 ? answer.length : 100;
			for (int i = 0; i < answer_length; i++) {
				if (checkanswerExist(username, answer[i]) == true) {
					score -= 1;
				} else {
					// prime number
					if (jedis.sismember("primes", answer[i].toString())) {
						score += 1;
						jedis.sadd(username + "_list", answer[i].toString());
					} else {
						score -= 2;
					}
				}
				jedis.zadd("ranking", score, username);
			}
		}
	}

	public List<Result> getUserScores() {
		try (Jedis jedis = pool.getResource()) {
			Set<Tuple> scores = jedis.zrevrangeByScoreWithScores("ranking", "+inf", "-inf");
			List<Result> results = new ArrayList<Result>();
			scores.forEach(t -> {
				String email = t.getElement();
				Double score = t.getScore();
				results.add(new Result(email, score.intValue()));
			});
			return results;
		}
	}

    public void setChallenge(Challenge challenge){
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("id", String.valueOf(challenge.getId()));
        mp.put("name", challenge.getName());
        mp.put("date", String.valueOf(challenge.getDate()));
        mp.put("startTime", String.valueOf(challenge.getStartTime()));
        mp.put("endTime", String.valueOf(challenge.getEndTime()));

		try (Jedis jedis = pool.getResource()) {
			jedis.hmset("challenge" + challenge.getId(), mp);
		}
	}

	public Challenge getChallenge(int number) {
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> mp = jedis.hgetAll("challenge" + number);
			String id = mp.get("id");
			String name = mp.get("name");
			String date = mp.get("date");
			String startTime = mp.get("startTime");
			String endTime = mp.get("endTime");
			return new Challenge(Integer.valueOf(id), name, Long.valueOf(date),
					Long.valueOf(startTime), Long.valueOf(endTime));
		}
    }
}
