package com.github.tobiasmiosczka.calendardiff.service.backup.parquet;

import com.github.tobiasmiosczka.calendardiff.service.backup.BackupService;
import com.google.api.services.calendar.model.Event;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = BackupService.CONFIG_PREFIX, name = BackupService.CONFIG_NAME, havingValue = "ParquetFileBackupService")
public class ParquetFileBackupService implements BackupService {
    @Override
    public void backup(final String calendarId, final List<Event> events, final LocalDateTime timeLoaded) {

    }
}
