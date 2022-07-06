package web.spring.customApplicationContext.service;

import web.spring.customApplicationContext.annotation.Bean;

/*@Bean()*/
public class AfternoonGreetingService implements GreetingService{
    @Override
    public void greeting() {
        System.out.println("Good afternoon dude!!!");
    }
}
