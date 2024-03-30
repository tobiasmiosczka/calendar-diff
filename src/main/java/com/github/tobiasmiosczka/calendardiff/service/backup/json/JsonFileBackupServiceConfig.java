package com.github.tobiasmiosczka.calendardiff.service.backup.json;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.nio.file.Path;

@Configuration
@ConfigurationProperties("services.backup-service.json-file-backup-service")
public class JsonFileBackupServiceConfig {
    private Charset encoding;
    private Path path;

    public JsonFileBackupServiceConfig() {

    }

    public Charset getEncoding() {
        return encoding;
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
