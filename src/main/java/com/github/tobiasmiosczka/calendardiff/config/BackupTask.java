package com.github.tobiasmiosczka.calendardiff.config;

import com.github.tobiasmiosczka.calendardiff.service.comparator.CalendarComparatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class BackupTask {

    private final CalendarComparatorService calendarComparatorService;

    @Autowired
    public BackupTask(final CalendarComparatorService calendarComparatorService) {
        this.calendarComparatorService = calendarComparatorService;
    }

    @Scheduled(cron = "@hourly")
    public void backup() throws IOException {
        calendarComparatorService.backup();
    }
}
