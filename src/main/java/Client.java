import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 12345);

            try (Scanner scanner = new Scanner(System.in)) {

                final SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(socketAddress);
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                while (true) {
                    System.out.println("Введите строку содержащую пробелы...");
                    String msg;
                    msg = scanner.nextLine();

                    if ("end".equals(msg)) break;

                    socketChannel.write(
                            ByteBuffer.wrap(
                                    msg.getBytes(StandardCharsets.UTF_8)));
                    Thread.sleep(1);
                    int bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

//        }
    }
}
