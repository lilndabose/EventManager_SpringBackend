package com.event_manager.eventsproject.controller;

import com.event_manager.eventsproject.dto.EventsRequestDto;
import com.event_manager.eventsproject.entity.Events;
import com.event_manager.eventsproject.entity.EventsRequest;
import com.event_manager.eventsproject.repository.EventsRequestRepository;
import com.event_manager.eventsproject.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
public class EventsRequestController {

    @Autowired
    private EventsRequestRepository eventsRequestRepository;

    private final EventsService eventService;

    public EventsRequestController(EventsService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/request/{user_id}")
    public ResponseEntity<List<EventsRequestDto>> getAllEvents(@PathVariable Long user_id) {
        List<EventsRequestDto> eventsList = new ArrayList<>();
        List<EventsRequest> eventsRequests = eventsRequestRepository.getAllUsersRequests(user_id);

        if(eventsRequests == null){
            throw new IllegalArgumentException("Sorry no event request found with user_id "+user_id);
        }

        eventsRequests.forEach((eventRequest)->{
            Events event = eventService.findById(eventRequest.getEvent_id());
                    eventsList.add(new EventsRequestDto(
                            eventRequest.getId(),
                            event.getName(),
                            event.getDescription(),
                            event.getLocation(),
                            event.getDate(),
                            event.getTime(),
                            event.getImage(),
                            eventRequest.getStatus()
                    ));
                });

        return new ResponseEntity<>(eventsList,HttpStatus.OK);
    }

    @PostMapping("/events/request")
    public ResponseEntity<EventsRequest> saveNewRequest(@RequestBody EventsRequest body){
        if(eventsRequestRepository.requestExist(body.getUser_id(), body.getEvent_id()).isEmpty()){
            EventsRequest eventsRequest = eventsRequestRepository.save(body);
            if(eventsRequest.toString().contains("null")){
                return new ResponseEntity<>(eventsRequest,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(eventsRequest,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/events/request/{id}")
    public ResponseEntity<EventsRequest> updateEventRequestById(@PathVariable Long id,@RequestBody EventsRequest body) {
        EventsRequest eventsRequest = eventsRequestRepository.findById(body.getId()).orElseThrow(()-> new RuntimeException("No request with this id "+body.getId()+" not found"));
        eventsRequest.setStatus(body.getStatus());
        eventsRequestRepository.save(eventsRequest);

        return new ResponseEntity<>(eventsRequest, HttpStatus.OK);
    }


    @DeleteMapping("/events/request/{id}")
    public ResponseEntity<Void> deleteEventsRequest(@PathVariable Long id) {
        EventsRequest eventsRequest = eventsRequestRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("No Events with id "+id+ " found"));
        eventsRequestRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
