package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.entity.Notifications;
import com.event_manager.eventsproject.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping("")
    public ResponseEntity<List<Notifications>> getAllNotifications(){
        return new ResponseEntity<>(this.notificationsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notifications> getOneNotification(@PathVariable("id") Long id){
        Optional<Notifications> notifications = this.notificationsRepository.findById(id);
        return notifications.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Notifications>> getUsersNotification(@PathVariable("id") Long id) {
        List<Notifications> notifications = this.notificationsRepository.findByUserId(id);
        return new ResponseEntity<>(notifications,HttpStatus.OK);
    }

    @GetMapping("/users/{email}/context/{context}")
    public ResponseEntity<List<Notifications>> getAllUsersNotification(@PathVariable("email") String email,@PathVariable("context") String context) {
        List<Notifications> notifications = this.notificationsRepository.getAllUsersNotifications(email,context);
        return new ResponseEntity<>(notifications,HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<Void> saveNotification(@RequestBody Notifications notifications){
        try{
            this.notificationsRepository.save(notifications);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/all")
    public ResponseEntity<Void> saveAllNotifications(@RequestBody List<Notifications> notifications){
        try{
            this.notificationsRepository.saveAll(notifications);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotification(@PathVariable("id") Long id, @RequestBody Notifications notifications){
        try{
            Notifications myNotif = this.notificationsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("sorry no notification found with this id: "+id));
            myNotif.setContext(notifications.getContext());
            myNotif.setMessage(notifications.getMessage());
            this.notificationsRepository.save(myNotif);

            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id){
        try{
            this.notificationsRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
