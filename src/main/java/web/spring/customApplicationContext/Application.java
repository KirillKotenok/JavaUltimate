package web.spring.customApplicationContext;

import web.spring.customApplicationContext.context.ApplicationContext;
import web.spring.customApplicationContext.context.ApplicationContextImpl;
import web.spring.customApplicationContext.service.ConferenceService;
import web.spring.customApplicationContext.service.SpeakingService;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContextImpl("web.spring.customApplicationContext");
        SpeakingService service = applicationContext.getComponent(ConferenceService.class);
        service.letsTalk();
    }
}
