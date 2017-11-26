package org.grokking.challenges.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by vinhdp on 26/11/17.
 */
@Component
public class BackgroundWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundWorker.class);

    public void run() {
        // get redis ranking with score
        // add to sorted set & return
        LOGGER.info("Background worker is running...");
    }
}
