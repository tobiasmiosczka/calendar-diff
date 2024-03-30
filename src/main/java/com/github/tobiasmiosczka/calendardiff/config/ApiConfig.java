package com.github.tobiasmiosczka.calendardiff.config;

import com.github.tobiasmiosczka.calendardiff.service.comparator.CalendarComparatorService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
@Configuration
public class ApiConfig {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final int PORT = 18888;

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

    private static final String APPLICATION_NAME = "calendar-diff";

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String ACCESS_TYPE_OFFLINE = "offline";

    private static final String USER_ME = "user";

    @Bean
    public Calendar buildService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        final InputStream in = CalendarComparatorService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType(ACCESS_TYPE_OFFLINE)
                .build();
        final LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(PORT)
                .build();
        return new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize(USER_ME);
    }
}
