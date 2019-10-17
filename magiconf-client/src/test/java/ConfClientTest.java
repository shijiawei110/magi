import com.sjw.fastnetty.client.NettyClientBuilder;
import com.sjw.magi.common.expection.MagiException;
import com.sjw.magiconf.client.MagiConfClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author shijiawei
 * @version ConfServerTest.java, v 0.1
 * @date 2019/2/28
 */
@Slf4j
public class ConfClientTest {
    public static void main(String[] args) throws InterruptedException {
        NettyClientBuilder nettyClientBuilder = new NettyClientBuilder();
        MagiConfClient magiConfClient = new MagiConfClient(nettyClientBuilder, "test");
        magiConfClient.start();
        //模拟容器环境
        Scanner scan = new Scanner(System.in);
        while (true) {
            String str1 = scan.nextLine();
            if ("0".equals(str1)) {
                try {
                    Object v = magiConfClient.getSingleValue("testStr");
                    System.out.println("获取conf的值 : " + v);
                } catch (MagiException e) {
                    System.out.println(e.getMessage());
                }
            }
            if ("1".equals(str1)) {
                //测试ping
                magiConfClient.ping();
            }
            if ("99".equals(str1)) {
                break;
            }
        }
        System.out.println("magiconf客户端结束");
        scan.close();
        magiConfClient.close();
    }
}
