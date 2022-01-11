package RIO.Watcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;
  private boolean waitingForConnection = true;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    waitingForConnection = false;
  }

  public boolean waitingForConnection() {
    return this.waitingForConnection;
  }

  public Socket currentConnection() {
    if (this.clientSocket == null) {
      waitingForConnection = true;
    }

    return this.clientSocket;
  }

  public void waitForConnection() throws IOException {
    clientSocket = serverSocket.accept();
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    waitingForConnection = false;
  }

  public void sendData(String io) {
    out.println(io);
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
  }
}
