package web.spring.customApplicationContext.service;

import web.spring.customApplicationContext.annotation.Component;
import web.spring.customApplicationContext.annotation.Inject;

@Component
public class ConferenceService implements SpeakingService {

    @Inject("AfternoonGreetingService")
    private GreetingService greetingService;

    @Override
    public void letsTalk() {
        greetingService.greeting();
    }
}
