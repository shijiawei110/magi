import com.sjw.magi.network.server.NettyServerBuilder;
import com.sjw.magi.server.MagiServer;
import com.sjw.magi.server.MagiServerBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version ServerTest.java, v 0.1
 * @date 2019/2/28
 */
@Slf4j
public class ServerTest {
    public static void main(String[] args) {
        final MagiServerBuilder magiServerBuilder = new MagiServerBuilder();
        magiServerBuilder.setHost("127.0.0.1");

        final NettyServerBuilder nettyServerBuilder = new NettyServerBuilder();
        nettyServerBuilder.setTimeOutMills(3000L);

        final MagiServer magiServer = new MagiServer(magiServerBuilder,nettyServerBuilder);
        magiServer.start();
    }
}
