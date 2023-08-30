package com.sachet.springmultithreadedweb.controller;

import com.sachet.springmultithreadedweb.entity.User;
import com.sachet.springmultithreadedweb.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
    produces = "application/json")
    public ResponseEntity saveUser(
            @RequestParam(value = "files")MultipartFile[] file) {
        for (MultipartFile file1: file) {
            userService.saveUser(file1);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<User>>> findAllUsers() {
        return userService.findAllUsers().thenApply(ResponseEntity::ok);
    }
}
