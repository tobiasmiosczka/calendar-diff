package com.github.tobiasmiosczka.calendardiff.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FetchService {

    private final int fetchSize;

    private static final String ORDER_BY_START_TIME = "startTime";

    private final Calendar service;

    public FetchService(final Calendar service, final FetchServiceConfig config) {
        this.service = service;
        this.fetchSize = config.getFetchSize();
    }

    public List<Event> getEvents(final String calendarId) throws IOException {
        final List<Event> result = new ArrayList<>();
        DateTime max = new DateTime(0);
        while (true) {
            final List<Event> fetchedEvents = getEvents(calendarId, max);
            result.addAll(fetchedEvents);
            if (fetchedEvents.size() < fetchSize) {
                break;
            }
            max = toDateTime(fetchedEvents.getLast().getStart());
        }
        return result;
    }

    private List<Event> getEvents(final String calendarId, final DateTime minTime) throws IOException {
        return service.events().list(calendarId)
                .setMaxResults(fetchSize)
                .setTimeMin(minTime)
                .setOrderBy(ORDER_BY_START_TIME)
                .setSingleEvents(true)
                .execute()
                .getItems();
    }

    private static DateTime toDateTime(final EventDateTime eventDateTime) {
        if (eventDateTime.getDateTime() != null) {
            return new DateTime(eventDateTime.getDateTime().getValue());
        }
        return new DateTime(eventDateTime.getDate().getValue());
    }

}
