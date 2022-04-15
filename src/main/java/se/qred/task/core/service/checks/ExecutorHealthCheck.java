package se.qred.task.core.service.checks;

import com.codahale.metrics.health.HealthCheck;

import java.util.concurrent.ScheduledFuture;

public class ExecutorHealthCheck extends HealthCheck {

    private final ScheduledFuture checkExpiredContractsTask;

    public ExecutorHealthCheck(ScheduledFuture checkExpiredContractsTask) {
        this.checkExpiredContractsTask = checkExpiredContractsTask;
    }

    @Override
    protected Result check() throws Exception {
        if (checkExpiredContractsTask.isDone() || checkExpiredContractsTask.isCancelled()) {
            return Result.unhealthy("Task has stopped running, contracts are not expiring");
        }
        return Result.healthy("Contracts expire when time has passed");
    }
}
