package cjlib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Proxy {
    public static void main(String[] args) {
        Ukraine ukraineService = createMethodNullableProxy(Ukraine.class);
        ukraineService.hello(null); // logs nothing to the console
        ukraineService.gloryToUkraine(); // logs method invocation to the console
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback((MethodInterceptor) (object, method, args, methodProxy) -> {
            if (method.isAnnotationPresent(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method %s of the class %s]\n", method.getName(), targetClass.getSimpleName());
            }
            return methodProxy.invokeSuper(object, args);
        });

        return (T) enhancer.create();
    }

    public static <T> T createMethodNullableProxy(Class<T> targetClass) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);

        enhancer.setCallback((MethodInterceptor) (object, method, args, methodProxy) -> {
            for (Method m : object.getClass().getDeclaredMethods()) {
                Parameter[] parameters = m.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].isAnnotationPresent(NotNull.class) && args[i] == null) {
                        throw new NullPointerException(String.format("Method %s parameter annotated by @NotNull annotation. " +
                                "You must set correct parameter!", m.getName()));
                    }
                }
            }
            return methodProxy.invokeSuper(object, args);
        });
        return (T) enhancer.create();
    }
}
