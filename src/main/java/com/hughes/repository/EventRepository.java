package com.hughes.repository;

import java.util.List;

import com.hughes.model.Event;
import com.hughes.model.Rsvp;

public interface EventRepository {
	Event createEvent(Event event);

	List<Event> getAllEvents();

	Event getEventById(int eventId);

	Event updateEvent(int eventId, Event event);

	void deleteEvent(int eventId);

	void rsvpForEvent(int eventId, String userId);

	public List<Rsvp> getRsvpsForEvent(int eventId);
	
	boolean isRSVPed(int eventId, String userId);

    void cancelRSVP(int eventId, String userId);
}