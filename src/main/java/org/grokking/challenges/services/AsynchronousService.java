package org.grokking.challenges.services;

import org.grokking.challenges.workers.BackgroundWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by vinhdp on 26/11/17.
 */

@Service
public class AsynchronousService {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    public void executeAsynchronously() {
        BackgroundWorker worker = applicationContext.getBean(BackgroundWorker.class);
        taskExecutor.execute(worker);
    }
}
