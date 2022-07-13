package web.spring.customApplicationContext.context;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import web.spring.customApplicationContext.annotation.Bean;
import web.spring.customApplicationContext.annotation.Component;
import web.spring.customApplicationContext.annotation.Inject;
import web.spring.customApplicationContext.exception.NoSuchBeanException;
import web.spring.customApplicationContext.exception.NoUniqueBeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.substring;

public class ApplicationContextImpl implements ApplicationContext {

    private final ConcurrentMap<String, Object> contextBeanStorage = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> contextComponentStorage = new ConcurrentHashMap<>();


    public ApplicationContextImpl(String packageName) {
        createBeansAndPutIntoContextStorage(packageName);
        injectBeans(packageName);
    }

    @SneakyThrows
    private void injectBeans(String packageName) {
        var reflections = new Reflections(packageName);
        var targetClasses = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : targetClasses) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    var componentInstance = clazz.getConstructor().newInstance();
                    field.setAccessible(true);
                    field.set(componentInstance, contextBeanStorage.get(field.getAnnotation(Inject.class).value()));
                    contextComponentStorage.put(clazz.getSimpleName(), componentInstance);
                }
            }
        }
    }

    @SneakyThrows
    private void createBeansAndPutIntoContextStorage(String packageName) {
        var reflections = new Reflections(packageName);
        var targetMethods = reflections.getMethodsAnnotatedWith(Bean.class);
        for (Method method : targetMethods) {
            var beanType = method.getReturnType();
            var instance = beanType.getConstructor().newInstance();
            var name = setNameToNewInstance(beanType, method.getAnnotation(Bean.class));
            contextBeanStorage.put(name, instance);
        }
    }

    private <T extends Annotation & Bean> String setNameToNewInstance(Class<?> targetClass, T annotation) {
        return annotation.value().isBlank() ?
                substring(targetClass.getSimpleName(), 0, 1).toLowerCase() + substring(targetClass.getSimpleName(), 1, targetClass.getSimpleName().length()) :
                annotation.value();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        var beans = contextBeanStorage.values().stream()
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
        var beanFromStorage = contextBeanStorage.get(name);

        if (!beanFromStorage.getClass().isAssignableFrom(beanType)) {
            throw new NoSuchBeanException();
        }

        return beanType.cast(beanFromStorage);
    }

    @Override
    public <T> Map<String, ? extends T> getAllBeans(Class<T> beanType) {
        return contextBeanStorage.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (T) entry.getValue()));
    }

    @Override
    public <T> T getComponent(Class<T> componentType) {
        var beans = contextComponentStorage.values().stream()
                .filter(c -> c.getClass().isAssignableFrom(componentType)).toList();

        if (beans.size() > 1) {
            throw new NoUniqueBeanException();
        }

        if (beans.isEmpty()) {
            throw new NoSuchBeanException();
        }

        return componentType.cast(beans.get(0));
    }

    @Override
    public <T> Map<String, ? extends T> getAllComponents(Class<T> componentType) {
        return null;
    }
}
