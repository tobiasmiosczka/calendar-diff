package com.github.tobiasmiosczka.calendardiff.service.comparator;

import com.github.tobiasmiosczka.calendardiff.service.FetchService;
import com.github.tobiasmiosczka.calendardiff.service.backup.BackupService;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalendarComparatorService {

    private final BackupService backupManager;

    private final FetchService fetchManager;

    private final List<String> calendarIds;

    @Autowired
    public CalendarComparatorService(
            final CalendarComparatorServiceConfig config,
            final FetchService fetchManager,
            final BackupService backupManager) {
        this.backupManager = backupManager;
        this.fetchManager = fetchManager;
        this.calendarIds = config.getCalendarIds();
    }

    public void backup() throws IOException {
        final LocalDateTime now = LocalDateTime.now();
        for (String calendarId : calendarIds) {
            final List<Event> events = fetchManager.getEvents(calendarId);
            backupManager.backup(calendarId, events, now);
        }
    }
}
