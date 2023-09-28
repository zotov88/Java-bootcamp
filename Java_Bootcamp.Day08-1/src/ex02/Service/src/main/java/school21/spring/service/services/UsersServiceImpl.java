package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcImpl") UsersRepository repository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String signUp(String email) {
        String password = bCryptPasswordEncoder.encode("password");
        repository.save(new User(1L, password, email));
        return password;
    }
}
