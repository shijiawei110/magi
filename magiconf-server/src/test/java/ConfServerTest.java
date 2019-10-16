import com.sjw.fastnetty.server.NettyServerBuilder;
import com.sjw.magiconf.server.MagiConfServer;
import com.sjw.magiconf.server.MagiConfServerBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version ConfServerTest.java, v 0.1
 * @date 2019/2/28
 */
@Slf4j
public class ConfServerTest {
    public static void main(String[] args) {
        final MagiConfServerBuilder magiServerBuilder = new MagiConfServerBuilder();
        magiServerBuilder.setHost("127.0.0.1");

        final NettyServerBuilder nettyServerBuilder = new NettyServerBuilder();
        nettyServerBuilder.setTimeOutMills(3000L);
        //设置心跳检测最长断开时间为10秒
        nettyServerBuilder.setHeartBeatSeconds(10);

        final MagiConfServer magiServer = new MagiConfServer(magiServerBuilder, nettyServerBuilder);
        magiServer.start();
        magiServer.close();
    }
}
