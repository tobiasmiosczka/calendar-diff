package com.github.tobiasmiosczka.calendardiff.service.backup.json;

import com.github.tobiasmiosczka.calendardiff.service.backup.BackupService;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@ConditionalOnProperty(prefix = BackupService.CONFIG_PREFIX, name = BackupService.CONFIG_NAME, havingValue = "JsonFileBackupService")
public class JsonFileBackupService implements BackupService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final DateTimeFormatter DATETIME_FORMATTER = ofPattern("yyyyMMddHHmmss", Locale.ENGLISH);

    private static final Object FILE_EXTENSION = ".json";

    private final Charset encoding;

    private final Path path;

    @Autowired
    public JsonFileBackupService(JsonFileBackupServiceConfig config) {
        this.path = config.getPath();
        this.encoding = config.getEncoding();
    }


    @Override
    public void backup(final String calendarId, final List<Event> events, final LocalDateTime timeLoaded) {
        try {
            tryBackup(calendarId, events, timeLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryBackup(final String calendarId, final List<Event> events, final LocalDateTime timeLoaded) throws IOException {
        final Path subDir = createSubDir(timeLoaded);
        final String json = JSON_FACTORY.toPrettyString(events);
        final Path filePath = Path.of(String.valueOf(subDir), toFileName(calendarId));
        Files.writeString(filePath, json, encoding, CREATE);
    }

    private Path createSubDir(final LocalDateTime timeLoaded) throws IOException {
        final Path subDir = Path.of(String.valueOf(path), timeLoaded.format(DATETIME_FORMATTER));
        if (!Files.exists(subDir)) {
            Files.createDirectories(subDir);
        }
        return subDir;
    }

    private static String toFileName(final String calendarId) {
        return calendarId + FILE_EXTENSION;
    }
}
