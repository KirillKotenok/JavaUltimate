package web.spring.customApplicationContext;

import web.spring.customApplicationContext.context.ApplicationContext;
import web.spring.customApplicationContext.context.ApplicationContextImpl;
import web.spring.customApplicationContext.service.GreetingService;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContextImpl("web.spring.customApplicationContext");
        var beans = applicationContext.getAllBeans(GreetingService.class);
        beans.entrySet().stream()
                .forEach(entry -> {
                    System.out.println("Name: " + entry.getKey() + "\nInstance: " + entry.getValue()
                            + "\n");
                    entry.getValue().greeting();
                });
    }
}
