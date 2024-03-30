package com.github.tobiasmiosczka.calendardiff;

import com.github.tobiasmiosczka.calendardiff.service.comparator.CalendarComparatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final CalendarComparatorService calendarComparatorService;

    @Autowired
    public AppStartupRunner(final CalendarComparatorService calendarComparatorService) {
        this.calendarComparatorService = calendarComparatorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        calendarComparatorService.backup();
    }
}
