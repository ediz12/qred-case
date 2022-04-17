package se.qred.task.core.service;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockUser;
import se.qred.task.db.UserRepository;
import se.qred.task.db.dto.User;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleAuthenticatorTest extends BaseMockitoTest {

    @Mock
    private UserRepository userRepository;

    private SimpleAuthenticator simpleAuthenticator;

    @Before
    public void setUp() throws Exception {
        simpleAuthenticator = new SimpleAuthenticator(userRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenUserDoesNotExist_thenAuthenticateFails() throws AuthenticationException {
        // Given
        final BasicCredentials basicCredentials = new BasicCredentials("testuser", "testpass");

        // When
        when(userRepository.findByUsername(basicCredentials.getUsername())).thenReturn(null);

        // Then
        final Optional<User> optional = simpleAuthenticator.authenticate(basicCredentials);
        verify(userRepository).findByUsername(basicCredentials.getUsername());
        verifyNoMoreInteractions(userRepository);

        assertFalse(optional.isPresent());
    }

    @Test
    public void whenUserPasswordIsWrong_thenAuthenticateFails() throws AuthenticationException {
        // Given
        final BasicCredentials basicCredentials = new BasicCredentials("fay", "abcd");
        final User realUser = MockUser.simpleUser();

        // When
        when(userRepository.findByUsername(basicCredentials.getUsername())).thenReturn(realUser);

        // Then
        final Optional<User> optional = simpleAuthenticator.authenticate(basicCredentials);
        verify(userRepository).findByUsername(basicCredentials.getUsername());
        verifyNoMoreInteractions(userRepository);

        assertFalse(optional.isPresent());
    }

    @Test
    public void whenUserPasswordIsCorrect_thenAuthenticateSuccess() throws AuthenticationException {
        // Given
        final BasicCredentials basicCredentials = new BasicCredentials("fay", "1234");
        final User realUser = MockUser.simpleUser();

        // When
        when(userRepository.findByUsername(basicCredentials.getUsername())).thenReturn(realUser);

        // Then
        final Optional<User> optional = simpleAuthenticator.authenticate(basicCredentials);
        verify(userRepository).findByUsername(basicCredentials.getUsername());
        verifyNoMoreInteractions(userRepository);

        assertTrue(optional.isPresent());
        final User user = optional.get();
        assertEquals(user.getUsername(), realUser.getUsername());
        assertEquals(user.getPassword(), realUser.getPassword());
    }
}