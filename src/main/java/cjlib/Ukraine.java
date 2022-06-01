package cjlib;

public class Ukraine {
    public void hello(@NotNull String helloMessage) {
        System.out.println(helloMessage);
    }

    @LogInvocation
    public void gloryToUkraine() {
        System.out.println("Slava Ukraini!");
    }
}
