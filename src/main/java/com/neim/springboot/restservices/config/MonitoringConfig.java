package com.neim.springboot.restservices.config;

import io.micrometer.appoptics.AppOpticsConfig;
import io.micrometer.appoptics.AppOpticsMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.Nullable;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {

    AppOpticsConfig appopticsConfig = new AppOpticsConfig() {
        @Override
        public String apiToken() {
            return "evWrc8Tnisk_7OKsWRviRkLF3xXQ6o4r71F1eGhIgR3uZy5xuvh7A9ZQ87xe62ViClX0G0Q";
        }

        @Override
        @Nullable
        public String get(String k) {
            return null;
        }
    };
    MeterRegistry registry = new AppOpticsMeterRegistry(appopticsConfig, Clock.SYSTEM);
}
