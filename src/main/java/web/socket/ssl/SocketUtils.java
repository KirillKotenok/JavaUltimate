package web.socket.ssl;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@UtilityClass
public class SocketUtils {

    @SneakyThrows
    public static Socket getSSLSocket(String host, int port) {
        return SSLSocketFactory.getDefault().createSocket(host, port);
    }

    @SneakyThrows
    public static Socket getSocket(String host, int port) {
        return new Socket(host, port);
    }

    public static String getHostFromUrl(String url) {
        return substringBetween(url, "//", "/");
    }

    public static String getBreakpointFromUrl(String url) {
        return substringAfter(url, getHostFromUrl(url));
    }

    @SneakyThrows
    public static String doPost(Socket socket, String breakPoint, String body) {
        @Cleanup var writer = new PrintWriter(socket.getOutputStream());
        @Cleanup var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var host = socket.getInetAddress().getHostName();

        writer.println(String.format("POST %s HTTP/1.1", breakPoint));
        writer.println("Connection: close");
        writer.println(String.format("Host:%s", host));
        writer.println("Accept: application/json");
        writer.println("Content-Type: application/json");
        writer.println(String.format("Content-Length: %s", body.length()));
        writer.println("");
        writer.println(body);
        writer.println("");
        writer.flush();

        return reader.lines().collect(joining("\n"));
    }

    @SneakyThrows
    public static String doGet(Socket socket, String breakPoint) {
        @Cleanup var writer = new PrintWriter(socket.getOutputStream());
        @Cleanup var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var host = socket.getInetAddress().getHostName();

        writer.println(String.format("GET %s HTTP/1.1", breakPoint));
        writer.println("Connection: close");
        writer.println(String.format("Host:%s", host));
        writer.println("");
        writer.flush();

        return reader.lines().collect(joining("\n"));
    }

    @SneakyThrows
    public static String doHead(Socket socket, String breakPoint) {
        @Cleanup var writer = new PrintWriter(socket.getOutputStream());
        @Cleanup var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var host = socket.getInetAddress().getHostName();

        writer.println(String.format("HEAD %s HTTP/1.1", breakPoint));
        writer.println("Connection: close");
        writer.println(String.format("Host:%s", host));
        writer.println("");
        writer.flush();

        return reader.lines().collect(joining("\n"));
    }
}
