package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;

public class UsersServiceImpl {

    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    boolean authenticate(String login, String password) {
        try {
            User user = repository.findByLogin(login);
            if (user.getIsAuthentication()) {
                throw new AlreadyAuthenticatedException("Already authenticated");
            }
            if (user.getPassword().equals(password)) {
                user.setIsAuthentication(true);
                repository.update(user);
                return true;
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
        return false;
    }
}
