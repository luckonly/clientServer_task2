import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class Server {
    public static void main(String[] args) {

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress("localhost", 12345));

            while (true) {

                try (SocketChannel socketChannel = serverChannel.accept()) {
                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                    while (socketChannel.isConnected()) {

                        int bytesCount = socketChannel.read(inputBuffer);
                        if (bytesCount == -1) break;
                        final String msg = new String(inputBuffer.array(), 0, bytesCount,
                                                StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        socketChannel.write(ByteBuffer.wrap((msg.replace(" ", "")).getBytes(
                                StandardCharsets.UTF_8)));

                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
