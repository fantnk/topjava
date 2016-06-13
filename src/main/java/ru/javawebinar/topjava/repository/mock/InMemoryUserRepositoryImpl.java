package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");

        List<User> users = new LinkedList<>(repository.values());
        Collections.sort(users, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        //TODO: NullPointerException
        return repository.values()
                .stream()
                .findFirst()
                .filter(user -> user.getEmail().equals(email)).orElseGet(null);
    }

/*    public static void main(String[] args) {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();
        userRepository.repository.put(userRepository.counter.incrementAndGet(), new User(0, "Name", "mail@mail.ru", "p@ss", Role.ROLE_USER));
        userRepository.repository.put(userRepository.counter.incrementAndGet(), new User(1, "Name2", "mail2@mail.ru", "p@s42", Role.ROLE_USER));
        userRepository.repository.put(userRepository.counter.incrementAndGet(), new User(2, "Name1", "mail3@mail.ru", "p@s8", Role.ROLE_USER));
        userRepository.repository.put(userRepository.counter.incrementAndGet(), new User(3, "Name4", "mail1@mail.ru", "p@ss5", Role.ROLE_USER));

        userRepository.getAll().forEach(System.out::println);
    }*/
}
