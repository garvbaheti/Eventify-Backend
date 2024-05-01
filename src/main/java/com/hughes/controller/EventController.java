package com.hughes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.hughes.model.Event;
import com.hughes.services.EventService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody Event event) {
		try {
			Event createdEvent = eventService.createEvent(event);
			return ResponseEntity.ok(createdEvent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process event image");
		}
	}

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }


	@GetMapping("/{eventId}")
	public ResponseEntity<Event> getEventById(@PathVariable("eventId") int eventId) {
		Event event = eventService.getEventById(eventId);
		return ResponseEntity.ok(event);
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<Event> updateEvent(@PathVariable("eventId") int eventId, @RequestBody Event event) {
		Event updatedEvent = eventService.updateEvent(eventId, event);
		return ResponseEntity.ok(updatedEvent);
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") int eventId) {
		eventService.deleteEvent(eventId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{eventId}/rsvp")
	public ResponseEntity<Void> rsvpForEvent(@PathVariable("eventId") int eventId,
	        @RequestParam("userId") String userId) {
	    boolean isRSVPed = eventService.isRSVPed(eventId, userId);
	    if (isRSVPed) {
	        // If already RSVPed, de-register
	        eventService.cancelRSVP(eventId, userId);
	    } else {
	        // If not RSVPed, register
	        eventService.rsvpForEvent(eventId, userId);
	    }
	    return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{eventId}/rsvp")
	public ResponseEntity<Void> cancelRSVPForEvent(@PathVariable("eventId") int eventId,
	        @RequestParam("userId") String userId) {
	    eventService.cancelRSVP(eventId, userId);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{eventId}/rsvp")
	public ResponseEntity<Boolean> getRSVPStatus(@PathVariable("eventId") int eventId,
	        @RequestParam("userId") String userId) {
	    try {
	        // Check if the user with userId has RSVP'd for the event with eventId
	        boolean isRSVPed = eventService.isRSVPed(eventId, userId);
	        return ResponseEntity.ok(isRSVPed);
	    } catch (Exception e) {
	        // Handle any exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


}