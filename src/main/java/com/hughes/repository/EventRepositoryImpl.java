package com.hughes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.hughes.model.Event;
import com.hughes.model.Rsvp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Event createEvent(Event event) {
        String sql = "INSERT INTO events (event_name, event_date, venue_name, venue_city, venue_state, venue_country, organizer_name, organizer_phone, description, event_image, created_by_user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getEventName());
            ps.setObject(2, event.getEventDate());
            ps.setString(3, event.getVenueName());
            ps.setString(4, event.getVenueCity());
            ps.setString(5, event.getVenueState());
            ps.setString(6, event.getVenueCountry());
            ps.setString(7, event.getOrganizerName());
            ps.setString(8, event.getOrganizerPhone());
            ps.setString(9, event.getDescription());
            ps.setString(10, event.getEventImage());
            ps.setString(11, event.getCreatedByUserId());
            return ps;
        });
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        String sql = "SELECT * FROM events";
        return jdbcTemplate.query(sql, eventRowMapper);
    }

    @Override
    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        return jdbcTemplate.queryForObject(sql, eventRowMapper, eventId);
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        String sql = "UPDATE events SET event_name = ?, event_date = ?, venue_name = ?, venue_city = ?, venue_state = ?, venue_country = ?, organizer_name = ?, organizer_phone = ?, description = ?, event_image = ? WHERE event_id = ?";
        jdbcTemplate.update(sql, event.getEventName(), event.getEventDate(), event.getVenueName(), event.getVenueCity(), event.getVenueState(), event.getVenueCountry(), event.getOrganizerName(), event.getOrganizerPhone(), event.getDescription(), event.getEventImage(), eventId);
        return event;
    }

    @Override
    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        jdbcTemplate.update(sql, eventId);
    }

    @Override
    public void rsvpForEvent(int eventId, String userId) {
        String sql = "INSERT INTO rsvps (event_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, eventId, userId);
    }

    @Override
    public List<Rsvp> getRsvpsForEvent(int eventId) {
        String sql = "SELECT * FROM rsvps WHERE event_id = ?";
        return jdbcTemplate.query(sql, rsvpRowMapper, eventId);
    }

    private RowMapper<Event> eventRowMapper = (rs, rowNum) -> {
        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setEventName(rs.getString("event_name"));
        event.setEventDate(rs.getDate("event_date").toLocalDate()); // Convert java.sql.Date to LocalDate
        event.setVenueName(rs.getString("venue_name"));
        event.setVenueCity(rs.getString("venue_city"));
        event.setVenueState(rs.getString("venue_state"));
        event.setVenueCountry(rs.getString("venue_country"));
        event.setOrganizerName(rs.getString("organizer_name"));
        event.setOrganizerPhone(rs.getString("organizer_phone"));
        event.setDescription(rs.getString("description"));
        event.setEventImage(rs.getString("event_image"));
        event.setCreatedByUserId(rs.getString("created_by_user_id"));
        event.setCreatedAt(rs.getTimestamp("created_at"));
        event.setUpdatedAt(rs.getTimestamp("updated_at"));
        return event;
    };


    private RowMapper<Rsvp> rsvpRowMapper = (rs, rowNum) -> {
        Rsvp rsvp = new Rsvp();
        rsvp.setRsvpId(rs.getInt("rsvp_id"));
        rsvp.setEventId(rs.getInt("event_id"));
        rsvp.setUserId(rs.getString("user_id"));
        return rsvp;
    };
    
    @Override
    public boolean isRSVPed(int eventId, String userId) {
        String sql = "SELECT COUNT(*) FROM rsvps WHERE event_id = ? AND user_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, eventId, userId);
        return count > 0;
    }

    @Override
    public void cancelRSVP(int eventId, String userId) {
        String sql = "DELETE FROM rsvps WHERE event_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, eventId, userId);
    }
}
