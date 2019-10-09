import com.sjw.fastnetty.client.NettyClientBuilder;
import com.sjw.magi.client.MagiClient;
import com.sjw.magi.common.expection.MagiException;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;

import java.util.List;
import java.util.Scanner;

/**
 * @author shijiawei
 * @version ClientTest.java -> v 1.0
 * @date 2019/3/29
 */
public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        NettyClientBuilder nettyClientBuilder = new NettyClientBuilder();
        MagiClient magiClient = new MagiClient(nettyClientBuilder, "test");
        magiClient.start();

        //模拟容器环境
        Scanner scan = new Scanner(System.in);
        while (true) {
            String str1 = scan.nextLine();
            if ("0".equals(str1)) {
                try {
                    List<MagiClientNodeInfo> nodes = magiClient.getRegisterInfo();
                    nodes.stream().forEach(p -> {
                        System.out.println(p.toString());
                    });
                } catch (MagiException e) {
                    System.out.println(e.getMessage());
                }
            }
            if ("1".equals(str1)) {
                magiClient.ping();
            }
            if ("99".equals(str1)) {
                break;
            }
        }
        scan.close();
        magiClient.close();
    }
}
