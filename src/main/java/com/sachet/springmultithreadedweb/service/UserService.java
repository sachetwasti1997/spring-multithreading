package com.sachet.springmultithreadedweb.service;

import com.sachet.springmultithreadedweb.entity.User;
import com.sachet.springmultithreadedweb.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final Object target = new Object();
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Async
    public CompletableFuture<List<User>> saveUser (MultipartFile file) {
        long startTime = System.currentTimeMillis();
        var users = parseJson(file);
        LOGGER.info("saving list of users of size {}, thread:- {}",
                users.size(), Thread.currentThread().getName());
        users = userRepository.saveAll(users);
        long endTime = System.currentTimeMillis();
        LOGGER.info("Total time: {}", (endTime-startTime));
        return CompletableFuture.completedFuture(users);
    }
    @Async
    public CompletableFuture<List<User>> findAllUsers() {
        var users = userRepository.findAll();
        LOGGER.info("Get list of users by thread: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseJson(final MultipartFile file) {
        final List<User> users = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String [] data = line.split(",");
                final User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user);
            }
        } catch (IOException e) {
            LOGGER.error("Error Thrown `parseJson` method "+ Arrays.toString(e.getStackTrace()));
        }
        return users;
    }

}
