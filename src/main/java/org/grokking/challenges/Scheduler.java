package org.grokking.challenges;

import org.grokking.challenges.services.AsynchronousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by vinhdp on 26/11/17.
 */

@Component
public class Scheduler {

    @Autowired
    private AsynchronousService checkAsyncService;

    @Scheduled(fixedDelay = 1000)
    public void checkTheScedule() {
        checkAsyncService.executeAsynchronously();
    }
}
