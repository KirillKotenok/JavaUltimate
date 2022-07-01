package web.socket.simple;

import lombok.Cleanup;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.commons.lang3.StringUtils.substringBetween;

@Data
public class HttpServerSocket {
    private ServerSocket serverSocket;
    private static final int DEFAULT_PORT = 8899;
    private static final ExecutorService EXECUTOR_POOL = Executors.newFixedThreadPool(50);
    private static boolean isRunning = true;

    @SneakyThrows
    public HttpServerSocket() {
        this.serverSocket = new ServerSocket(DEFAULT_PORT);
    }

    @SneakyThrows
    public HttpServerSocket(int port) {
        this.serverSocket = new ServerSocket(port);
    }

    @SneakyThrows
    public void startServer() {
        System.out.println("----------\nServer start working\n----------");
        while (isRunning) {
            var acceptedSocket = serverSocket.accept();
            EXECUTOR_POOL.submit(() -> processClientRequest(acceptedSocket));
        }
    }

    public void stopServer() {
        isRunning = false;
        System.out.println("----------\nServer stop\n----------");
    }

    @SneakyThrows
    private void processClientRequest(Socket clientSocket) {
        System.out.printf("----------\nServer process request from: %s\n----------\n", clientSocket.getInetAddress());
        @Cleanup var inputStream = clientSocket.getInputStream();
        @Cleanup var reader = new BufferedReader(new InputStreamReader(inputStream));
        @Cleanup var outputStream = clientSocket.getOutputStream();
        @Cleanup var writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        var httpMetadata = reader.readLine();
        var hostMetadata = reader.readLine();

        if (isValidRequest(httpMetadata, hostMetadata)) {
            writer.write(String.format("Hello, %s!!!", substringBetween(httpMetadata, "name=", " ")));
            writer.flush();
        } else {
            writer.write(String.format("Bad request: %s", HttpStatus.BAD_REQUEST));
            writer.flush();
        }
    }

    private boolean isValidRequest(String httpMetadata, String hostMetadata) {
        return HttpValidator.validate(httpMetadata)
                || HostValidator.validate(serverSocket.getInetAddress().getHostAddress(), hostMetadata);
    }
}
