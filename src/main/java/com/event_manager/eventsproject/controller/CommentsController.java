package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.entity.Comments;
import com.event_manager.eventsproject.entity.Users;
import com.event_manager.eventsproject.repository.CommentsRepository;
import com.event_manager.eventsproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("")
    public ResponseEntity<List<Comments>> getAllComments(){
        return new ResponseEntity<>(this.commentsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<List<Comments>> getAnEventComments(@PathVariable("id") Long id){
        List<Comments> commentsList = this.commentsRepository.findAllEventsById(id)
                .stream()
                .map((item)->{
                    Optional<Users> users = this.usersRepository.findById(item.getUserId());
                    if(users.isPresent()){
                        item.setUsername(users.get().getUsername());
                    }
                    return item;
                }).toList();

        return new ResponseEntity<>(commentsList,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Void> saveComment(@RequestBody Comments comments){
        try{
            this.commentsRepository.save(comments);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
