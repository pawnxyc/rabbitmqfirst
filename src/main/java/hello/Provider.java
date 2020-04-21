package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    static Connection connection;
    @Test
    public void Send() throws IOException, TimeoutException {

        Channel ch1 = getC().createChannel();
//        通过channel来发送消息,第一个为交换机的名称，queue的名称，基本属性，消息体需要byte【】
        ch1.queueDeclare("hello",false,false,false,null);
        for (int i =1;i<=20;i++){
            ch1.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,(i+"hello my first mq queue").getBytes());
        }
        ch1.close();
        connection.close();
    }

   public static Connection getC() throws IOException, TimeoutException {
        ConnectionFactory con = new ConnectionFactory();
        con.setHost("192.168.1.198");
        con.setPort(5672);
        con.setVirtualHost("/ems");
        con.setUsername("ems");
        con.setPassword("123");
//       获取连接
        connection = con.newConnection();
//      把queue绑定道channel上面去。
//        参数分别是 队列名称，是否持久化道硬盘（重启mq之后还需要不需要），是否独占这个队列(允许别的con来连接)，
//        是否消费完成之后自动删除。
        return connection;
    }
}
