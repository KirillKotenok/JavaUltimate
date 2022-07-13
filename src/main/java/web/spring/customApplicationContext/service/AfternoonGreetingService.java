package web.spring.customApplicationContext.service;

public class AfternoonGreetingService implements GreetingService{
    @Override
    public void greeting() {
        System.out.println("Good afternoon dude!!!");
    }
}
