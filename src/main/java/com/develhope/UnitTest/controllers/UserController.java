package com.develhope.UnitTest.controllers;

import com.develhope.UnitTest.models.User;
import com.develhope.UnitTest.repositories.UserRepository;
import jakarta.validation.Valid;
import com.develhope.UnitTest.dto.APIResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> readById(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){

            return  ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(optionalUser.get());

        }
    }
    @PostMapping("")
    public ResponseEntity<APIResponse> create(@Valid @RequestBody User user,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new APIResponse(bindingResult.getAllErrors()));
        }else{
                return ResponseEntity.ok().body(new APIResponse(userRepository.save(user)));
         }

    }
    @GetMapping("/users")
    public List<User> readAll() {
    return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Integer id, @NotNull @RequestBody User us) {
        us.setId(id);
        userRepository.save(us);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
