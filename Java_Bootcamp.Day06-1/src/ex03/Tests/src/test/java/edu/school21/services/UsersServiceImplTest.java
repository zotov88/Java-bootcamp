package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {

    private final UsersRepository repository = Mockito.mock(UsersRepository.class);
    private final UsersServiceImpl service = new UsersServiceImpl(repository);
    private final User user1 = new User(1L, "login1", "pass1", true);
    private final User user2 = new User(2L, "login2", "pass2", false);

    @Test
    public void correctUsernameAndPasswordAndAuthIsFalse() {
        User user = new User(1L, "login", "pass", false);
        Mockito.when(repository.findByLogin(user.getLogin())).thenReturn(user);
        assertTrue(service.authenticate(user.getLogin(), user.getPassword()));
        assertTrue(user.getIsAuthentication());
    }

    @Test
    public void correctUsernameAndPasswordAndAuthIsTrue() {
        Mockito.when(repository.findByLogin(user1.getLogin())).thenReturn(user1);
        assertThrows(AlreadyAuthenticatedException.class, () -> service.authenticate(user1.getLogin(), user1.getPassword()));
    }

    @Test
    public void correctUsernameButInvalidUserPassword() {
        Mockito.when(repository.findByLogin(user2.getLogin())).thenReturn(user2);
        assertFalse(service.authenticate(user2.getLogin(), "incorrectPassword"));
    }

    @Test
    public void userIsNotInDatabase() {
        Mockito.when(repository.findByLogin("login")).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> service.authenticate("login", "password"));
    }

}
