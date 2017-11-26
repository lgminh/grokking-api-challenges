package org.challenges.grokking.Controller;

/**
 * Created by daniel on 11/26/17.
 */

import org.challenges.grokking.Model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PlayerController {
	public ResponseEntity<?> createUser(@RequestBody Player player, UriComponentsBuilder ucBuilder) {

	}
}
