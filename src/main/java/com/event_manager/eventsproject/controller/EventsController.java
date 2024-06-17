package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.entity.Events;
import com.event_manager.eventsproject.entity.Users;
import com.event_manager.eventsproject.services.EventsService;
import com.event_manager.eventsproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private UsersService usersService;

    private final EventsService eventService;

    public EventsController(EventsService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Events> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/upcoming/{user_id}")
    public List<Events> getUpcomingEvents(@PathVariable Long user_id) {
        List<Events> eventsList = new ArrayList<>();
         eventService.findAll()
                 .forEach((item)->{
                     List<Users> users = item.getUsers().stream().filter((itm)-> itm.getId() != user_id).toList();
                     if(!users.isEmpty()){
                         eventsList.add(item);
                     }
                 });

         return eventsList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getEventById(@PathVariable Long id) {
        Events events = eventService.findById(id);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping
    public Events createEvent(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("location") String location,
                              @RequestParam("date") String date,
                              @RequestParam("time") String time,
                              @RequestParam(value = "image") MultipartFile image,
                              @RequestParam("users") List<Long> userIds) throws IOException {
        Events event = new Events();
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setDate(date);
        event.setTime(time);

        List<Users> users = userIds.stream()
                .map(usersService::findById)
                .collect(Collectors.toList());
        event.setUsers(users);

        return eventService.saveEventWithImage(event, image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvent(@PathVariable Long id,
                                             @RequestParam("name") String name,
                                             @RequestParam("description") String description,
                                             @RequestParam("location") String location,
                                             @RequestParam("date") String date,
                                             @RequestParam("time") String time,
                                             @RequestParam("image") MultipartFile image) throws IOException {
        Events event =  eventService.findById(id);
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setDate(date);
        event.setTime(time);
        try {
            return ResponseEntity.ok().body(eventService.saveEventWithImage(event, image));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        Events event = eventService.findById(id);
        try{
            eventService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            eventService.deleteById(id);
            return ResponseEntity.internalServerError().build();
        }
    }
}
