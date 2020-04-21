package route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hello.Provider;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//路由订阅模型
public class Direct {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = Provider.getC();
        Channel channel = connection.createChannel();
//        通道绑定交换机
        channel.exchangeDeclare("logs_direct","direct");
        String routeKey = "info";
        channel.basicPublish("logs_direct",routeKey,null,"direct mod send message info".getBytes());
        channel.basicPublish("logs_direct","error",null,"direct mod send message error".getBytes());
        channel.basicPublish("logs_direct","",null,"direct mod send message all".getBytes());

        channel.close();
        connection.close();
    }
}
