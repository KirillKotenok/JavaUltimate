package web.spring.customApplicationContext.context;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import web.spring.customApplicationContext.annotation.Bean;
import web.spring.customApplicationContext.exception.NoSuchBeanException;
import web.spring.customApplicationContext.exception.NoUniqueBeanException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.substring;

public class ApplicationContextImpl implements ApplicationContext {

    private final ConcurrentMap<String, Object> contextStorage = new ConcurrentHashMap<>();

    @SneakyThrows
    public ApplicationContextImpl(String packageName) {
        var reflections = new Reflections(packageName);
        var targetClasses = reflections.getTypesAnnotatedWith(Bean.class);

        for (Class<?> targetClass : targetClasses) {
            var instance = targetClass.getConstructor().newInstance();
            var name = getNameToNewInstance(targetClass);
            contextStorage.put(name, instance);
        }
    }

    private String getNameToNewInstance(Class<?> targetClass) {
        return targetClass.getAnnotation(Bean.class).value().isBlank() ?
                substring(targetClass.getSimpleName(), 0, 1).toLowerCase() + substring(targetClass.getSimpleName(), 1, targetClass.getSimpleName().length()) :
                targetClass.getAnnotation(Bean.class).value();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        var beans = contextStorage.values().stream()
                .filter(c -> c.getClass().isAssignableFrom(beanType))
                .collect(Collectors.toList());

        if (beans.size() > 1) {
            throw new NoUniqueBeanException();
        }

        if (beans.isEmpty()) {
            throw new NoSuchBeanException();
        }

        return beanType.cast(beans.get(0));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        var beanFromStorage = contextStorage.get(name);

        if (!beanFromStorage.getClass().isAssignableFrom(beanType)) {
            throw new NoSuchBeanException();
        }

        return beanType.cast(beanFromStorage);
    }

    @Override
    public <T> Map<String, ? extends T> getAllBeans(Class<T> beanType) {
        return contextStorage.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (T) entry.getValue()));
    }
}
