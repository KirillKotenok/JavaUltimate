package web.socket;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Demo {
    @SneakyThrows
    public static void main(String[] args) {
        HttpServerSocket httpServerSocket = new HttpServerSocket();
        Thread t1 = new Thread(httpServerSocket::startServer);
        t1.start();
        Thread.sleep(3000);

        @Cleanup Socket clientSocket = new Socket(httpServerSocket.getServerSocket().getInetAddress(), httpServerSocket.getServerSocket().getLocalPort());
        @Cleanup var writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        @Cleanup var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer.println("GET /hello?name=Kirill HTTP/1.1");
        writer.println("Host: localhost");
        writer.flush();

        httpServerSocket.stopServer();
        t1.join(2000);
        reader.lines().forEach(System.out::println);
    }
}
