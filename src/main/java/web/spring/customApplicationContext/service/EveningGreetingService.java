package web.spring.customApplicationContext.service;

import web.spring.customApplicationContext.annotation.Bean;

/*@Bean(value = "evening")*/
public class EveningGreetingService implements GreetingService{
    @Override
    public void greeting() {
        System.out.println("Good evening dude!!!");
    }
}
