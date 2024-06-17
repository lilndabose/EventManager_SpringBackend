package com.event_manager.eventsproject.services;

import com.event_manager.eventsproject.entity.Events;
import com.event_manager.eventsproject.repository.EventsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EventsService {


    private final EventsRepository eventRepository;

    public EventsService(EventsRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Events> findAll() {
        return eventRepository.findAll();
    }

    public Events findById(Long id) {
        return eventRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Event with id: "+id+" does not exist !!!"));
    }

    public Events save(Events event) {
        return eventRepository.save(event);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public Events saveEventWithImage(Events event, MultipartFile image) throws IOException {
        // Save the image file to the server and set the file path in the event
        String imagePath = "uploads/" + image.getOriginalFilename();
        Files.createDirectories(Paths.get("uploads/"));
        Files.copy(image.getInputStream(), Paths.get(imagePath));
        event.setImage(imagePath);
        return eventRepository.save(event);
    }
}
