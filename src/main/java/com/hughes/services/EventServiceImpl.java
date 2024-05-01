package com.hughes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hughes.model.Event;
import com.hughes.repository.EventRepository;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event createEvent(Event event) {
		return eventRepository.createEvent(event);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.getAllEvents();
	}

	@Override
	public Event getEventById(int eventId) {
		return eventRepository.getEventById(eventId);
	}

	@Override
	public Event updateEvent(int eventId, Event eventDetails) {
		Event existingEvent = getEventById(eventId);
		existingEvent.setEventName(eventDetails.getEventName());
		existingEvent.setEventDate(eventDetails.getEventDate());
		existingEvent.setVenueName(eventDetails.getVenueName());
		existingEvent.setVenueCity(eventDetails.getVenueCity());
		existingEvent.setVenueState(eventDetails.getVenueState());
		existingEvent.setVenueCountry(eventDetails.getVenueCountry());
		existingEvent.setOrganizerName(eventDetails.getOrganizerName());
		existingEvent.setOrganizerPhone(eventDetails.getOrganizerPhone());
		existingEvent.setDescription(eventDetails.getDescription());
		existingEvent.setEventImage(eventDetails.getEventImage());
		return eventRepository.updateEvent(eventId, existingEvent);
	}

	@Override
	public void deleteEvent(int eventId) {
		eventRepository.deleteEvent(eventId);
	}

	@Override
	public void rsvpForEvent(int eventId, String userId) {
		eventRepository.rsvpForEvent(eventId, userId);
	}
	
	@Override
    public boolean isRSVPed(int eventId, String userId) {
        // Delegate to repository method
        return eventRepository.isRSVPed(eventId, userId);
    }

    @Override
    public void cancelRSVP(int eventId, String userId) {
        // Delegate to repository method
        eventRepository.cancelRSVP(eventId, userId);
    }
}