package org.grokking.challenges.controller;

/**
 * Created by daniel on 11/26/17.
 */
import org.apache.tomcat.util.codec.binary.Base64;
import org.grokking.challenges.services.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.beans.Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.grokking.challenges.services.RedisService;
import org.grokking.challenges.model.Player;


@RestController
public class PlayerController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private static RedisService redisService = new RedisService(); //Service which will do all data retrieval/manipulation work


	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template, name));
	}

	// -------------------Create a User-------------------------------------------
	@RequestMapping(value = "/register.json", method = RequestMethod.POST)
	public ResponseEntity<String> createPlayer(@RequestParam String username, UriComponentsBuilder Builder) {
		logger.info("Creating User : {}", username);

		if (redisService.isplayerExist(username)) {
			logger.error("Unable to create. A User with name {} already exist", username);
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		} else {

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[128];
		random.nextBytes(bytes);
		String token = Base64.encodeBase64String(bytes).toString();
		Player player = redisService.savePlayer(username, token);
		ResponseEntity<String> playerResponseEntity = new ResponseEntity<String>(token, HttpStatus.CREATED);
		return playerResponseEntity;
		}
	}

	// -------------------Submit score-------------------------------------------
	@RequestMapping(value = "/submit.json", method = RequestMethod.POST)
	public ResponseEntity<?> createPlayer(@RequestBody Long[] answers,
	  	@RequestHeader("X-User") String username,
  		@RequestHeader("X-Token") String token,
	  UriComponentsBuilder Builder) {
		HttpHeaders headers = new HttpHeaders();
		if (redisService.isplayerExist(username)) {
			// simple authenticate
			// need to rewrite later
			if (redisService.authenticateUserToken(username, token)) {
				redisService.updatescorePlayer(username, answers);
			} else  {
				return new ResponseEntity<Void>(headers, HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<Void>(headers, HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}


}

class Greeting {

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
