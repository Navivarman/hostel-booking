package com.hostelbooking.app.controller;

import com.hostelbooking.app.model.User;
import com.hostelbooking.app.repository.UserRepository;
import com.hostelbooking.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> req){
        return ResponseEntity.ok(
                userService.loginUser(req.get("email"), req.get("password"))
       );
   }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
