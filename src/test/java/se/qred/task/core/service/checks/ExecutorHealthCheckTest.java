package se.qred.task.core.service.checks;

import com.codahale.metrics.health.HealthCheck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;

import java.util.concurrent.ScheduledFuture;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExecutorHealthCheckTest extends BaseMockitoTest {

    @Mock
    private ScheduledFuture executorTask;

    ExecutorHealthCheck executorHealthCheck;

    @Before
    public void setUp() throws Exception {
        executorHealthCheck = new ExecutorHealthCheck(executorTask);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenExecutorIsRunning_thenReturnHealthy() throws Exception {
        // Given

        // When
        when(executorTask.isDone()).thenReturn(Boolean.FALSE);
        when(executorTask.isCancelled()).thenReturn(Boolean.FALSE);

        // Then
        final HealthCheck.Result check = executorHealthCheck.check();
        verify(executorTask).isDone();
        verify(executorTask).isCancelled();
        verifyNoMoreInteractions(executorTask);

        assertTrue(check.isHealthy());
        assertEquals("Contracts expire when time has passed", check.getMessage());
    }

    @Test
    public void whenExecutorIsNotRunning_thenReturnUnhealthy() throws Exception {
        // Given

        // When
        when(executorTask.isDone()).thenReturn(Boolean.TRUE);

        // Then
        final HealthCheck.Result check = executorHealthCheck.check();
        verify(executorTask).isDone();
        verifyNoMoreInteractions(executorTask);

        assertFalse(check.isHealthy());
        assertEquals("Task has stopped running, contracts are not expiring", check.getMessage());
    }

    @Test
    public void whenExecutorIsDoneButNotCancelled_thenReturnUnhealthy() throws Exception {
        // Given

        // When
        when(executorTask.isDone()).thenReturn(Boolean.TRUE);

        // Then
        final HealthCheck.Result check = executorHealthCheck.check();
        verify(executorTask).isDone();
        verifyNoMoreInteractions(executorTask);

        assertFalse(check.isHealthy());
        assertEquals("Task has stopped running, contracts are not expiring", check.getMessage());
    }

    @Test
    public void whenExecutorIsCancelledButNotDone_thenReturnUnhealthy() throws Exception {
        // Given

        // When
        when(executorTask.isDone()).thenReturn(Boolean.FALSE);
        when(executorTask.isCancelled()).thenReturn(Boolean.TRUE);

        // Then
        final HealthCheck.Result check = executorHealthCheck.check();
        verify(executorTask).isDone();
        verify(executorTask).isCancelled();
        verifyNoMoreInteractions(executorTask);

        assertFalse(check.isHealthy());
        assertEquals("Task has stopped running, contracts are not expiring", check.getMessage());
    }
}