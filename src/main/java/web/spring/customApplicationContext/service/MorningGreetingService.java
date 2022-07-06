package web.spring.customApplicationContext.service;

import web.spring.customApplicationContext.annotation.Bean;

/*@Bean(value = "morning")*/
public class MorningGreetingService implements GreetingService{
    @Override
    public void greeting() {
        System.out.println("Good morning dude!!!");
    }
}
