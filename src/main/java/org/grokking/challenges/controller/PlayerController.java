package org.grokking.challenges.controller;

/**
 * Created by daniel on 11/26/17.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.atomic.AtomicLong;
import org.grokking.challenges.services.PlayerService;
import org.grokking.challenges.model.Player;

@RestController
public class PlayerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
	public static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	@Autowired
	PlayerService playerService; //Service which will do all data retrieval/manipulation work


	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template, name));
	}


	// -------------------Create a User-------------------------------------------
	@RequestMapping(value = "/player/", method = RequestMethod.POST)
	public ResponseEntity<?> createPlayer(@RequestBody player, UriComponentsBuilder Builder) {
		logger.info("Creating User : {}", player);

		if (playerService.isplayerExist()) {
			logger.error("Unable to create. A User with name {} already exist", player.getUsername());
			return new ResponseEntity(String("Player named {}",player.getUsername()), HttpStatus.CONFLICT);
		}
		playerService.savePlayer(player);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(player.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
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
