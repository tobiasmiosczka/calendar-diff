package com.github.tobiasmiosczka.calendardiff.service.backup;

import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface BackupService {

    String CONFIG_PREFIX = "services.backup-service";
    String CONFIG_NAME = "implementation";

    void backup(final String calendarId, final List<Event> events, final LocalDateTime timeLoaded);

}
