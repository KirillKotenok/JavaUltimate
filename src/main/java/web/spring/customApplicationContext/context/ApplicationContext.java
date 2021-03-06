package web.spring.customApplicationContext.context;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String name, Class<T> beanType);

    <T> Map<String, ? extends T> getAllBeans(Class<T> beanType);

    <T> T getComponent(Class<T> componentType);

    <T> Map<String, ? extends T> getAllComponents(Class<T> componentType);
}
