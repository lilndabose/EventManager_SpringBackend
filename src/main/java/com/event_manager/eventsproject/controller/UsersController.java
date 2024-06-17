package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.dto.LoginInfo;
import com.event_manager.eventsproject.dto.UsersDto;
import com.event_manager.eventsproject.entity.Users;
import com.event_manager.eventsproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Users> getAllUsers() {
        return this.usersService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(this.usersService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsersDto> createUser(@RequestBody Users user) {
        UsersDto usersDto = this.usersService.save(user);

        if(usersDto == null) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(usersDto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDto> loginUser(@RequestBody LoginInfo loginInfo){
        UsersDto usersDto = usersService.loginUser(loginInfo);
        if(usersDto == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(usersDto,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDto> updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
        Users users = this.usersService.findById(id);
        users.setEmail(userDetails.getEmail());
        users.setUsername(userDetails.getUsername());
        users.setPassword(userDetails.getPassword());

        return new ResponseEntity<>(this.usersService.save(users),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.usersService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
