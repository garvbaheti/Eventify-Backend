package com.hughes.services;

import java.util.List;

import com.hughes.model.Event;
import com.hughes.model.Rsvp;

public interface EventService {
	Event createEvent(Event event);

	List<Event> getAllEvents();

	Event getEventById(int eventId);

	Event updateEvent(int eventId, Event event);

	void deleteEvent(int eventId);

	void rsvpForEvent(int eventId, String userId);

	boolean isRSVPed(int eventId, String userId);

	void cancelRSVP(int eventId, String userId);
}