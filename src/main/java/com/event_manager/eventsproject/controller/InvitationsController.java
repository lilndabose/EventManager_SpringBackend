package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.entity.Events;
import com.event_manager.eventsproject.entity.Invitations;
import com.event_manager.eventsproject.entity.Users;
import com.event_manager.eventsproject.repository.EventsRepository;
import com.event_manager.eventsproject.repository.InvitationsRepository;
import com.event_manager.eventsproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/invitations")
public class InvitationsController {

    @Autowired
    private InvitationsRepository invitationsRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("")
    public ResponseEntity<List<Map<String,String>>> getAllInvitations(){
        List<Map<String,String>>  mapList = new ArrayList<>();
        this.invitationsRepository.findAll()
                .forEach((invitation)->{
                    Events event = this.eventsRepository.findById(invitation.getEventId()).orElseThrow(()-> new IllegalArgumentException("Can't find event with id: "+invitation.getEventId()));
                    Map<String,String> myMap = this.constructMap(event,invitation);
                    mapList.add(myMap);
                });

        return new ResponseEntity<>(mapList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,String>> getInvitationById(@PathVariable("id") Long id){
        Optional<Invitations> invitation = this.invitationsRepository.findById(id);
        if(invitation.isPresent()){
            Events event = this.eventsRepository.findById(invitation.get().getEventId()).orElseThrow(()-> new IllegalArgumentException("Can't find event with id: "+invitation.get().getEventId()));
            Map<String,String> myMap = this.constructMap(event,invitation.get());

            return new ResponseEntity<>(myMap,HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String,String>>> getAllUsersInvitationsByStatus(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "email", required = false) String email){
        List<Map<String,String>>  mapList = new ArrayList<>();
        if(status!=null) {
            this.invitationsRepository.findByStatus(status)
                    .forEach((invitation) -> {
                        Events event = this.eventsRepository.findById(invitation.getEventId()).orElseThrow(() -> new IllegalArgumentException("Can't find event with id: " + invitation.getEventId()));
                        Map<String, String> myMap = this.constructMap(event, invitation);
                        mapList.add(myMap);
                    });
        }else if(email!=null){
            this.invitationsRepository.findByUserEmail(email)
                    .forEach((invitation) -> {
                        Events event = this.eventsRepository.findById(invitation.getEventId()).orElseThrow(() -> new IllegalArgumentException("Can't find event with id: " + invitation.getEventId()));
                        Map<String, String> myMap = this.constructMap(event, invitation);
                        mapList.add(myMap);
                    });
        }

        return new ResponseEntity<>(mapList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> saveOneInvitation(@RequestBody Invitations invitations){
        try{
            Optional<Invitations> invitationsOptional = Optional.ofNullable(this.invitationsRepository.findByUserEmailAndEventId(invitations.getUserEmail(), invitations.getEventId()));
            if(invitationsOptional.isPresent()){
                return new ResponseEntity<>("Sorry you have already requested to join this event", HttpStatus.BAD_REQUEST);
            }
            this.invitationsRepository.save(invitations);
            return new ResponseEntity<>("Send Invitation successfully !!!",HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/all")
    public ResponseEntity<String> saveAllInvitations(@RequestBody List<Invitations> invitationsList){
        try{
            this.invitationsRepository.saveAll(invitationsList);
            return new ResponseEntity<>("Send Invitations successfully !!!",HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInvitation(@PathVariable("id") Long id, @RequestBody Invitations invitations){
        Optional<Invitations> invitation = this.invitationsRepository.findById(id);
        if(invitation.isPresent()){
            invitation.get().setStatus(invitations.getStatus());
            this.invitationsRepository.save(invitation.get());

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable("id") Long id){
        try{
            this.invitationsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<List<String>> getListOfAttendees(@PathVariable("id") Long id){
        List<String> list = new ArrayList<>();
        List<Invitations> invitationsList = this.invitationsRepository.findByEventIdAndStatus(id,"accepted");
        invitationsList
                .forEach((item)-> {
                    String username = this.getUserName(item.getUserEmail());
                    if(username!=null){
                        list.add(username);
                    }
                });

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    public Map<String,String> constructMap(Events event,Invitations invitation){
        Users user = this.usersRepository.findByEmail(invitation.getUserEmail());

        Map<String,String> myMap = new HashMap<>();
        myMap.put("event_id",event.getId().toString());
        myMap.put("name",event.getName());
        myMap.put("description",event.getDescription());
        myMap.put("location",event.getLocation());
        myMap.put("date",event.getDate()+" "+event.getTime());

        myMap.put("id",invitation.getId().toString());
        myMap.put("status",invitation.getStatus());
        myMap.put("username",user.getUsername());
        myMap.put("user_email",invitation.getUserEmail());

        return myMap;
    }

    public String getUserName(String email){
        Users users = this.usersRepository.findByEmail(email);
        if(users != null){
            return users.getUsername();
        }

        return null;
    }

}
