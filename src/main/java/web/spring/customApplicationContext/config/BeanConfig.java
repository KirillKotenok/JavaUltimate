package web.spring.customApplicationContext.config;

import web.spring.customApplicationContext.annotation.Bean;
import web.spring.customApplicationContext.service.AfternoonGreetingService;
import web.spring.customApplicationContext.service.EveningGreetingService;
import web.spring.customApplicationContext.service.GreetingService;
import web.spring.customApplicationContext.service.MorningGreetingService;

public class BeanConfig {
    @Bean
    public GreetingService getMorningService() {
        return new MorningGreetingService();
    }

    @Bean("evening-service")
    public GreetingService getEveningService() {
        return new EveningGreetingService();
    }

    @Bean("a f t e r n o o n")
    public GreetingService getAfternoonService() {
        return new AfternoonGreetingService();
    }
}
