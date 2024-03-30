package com.github.tobiasmiosczka.calendardiff.service.comparator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("services.calendar-comparator-service")
public class CalendarComparatorServiceConfig {

    private List<String> calendarIds;

    public List<String> getCalendarIds() {
        return calendarIds;
    }

    public void setCalendarIds(List<String> calendarIds) {
        this.calendarIds = calendarIds;
    }
}
