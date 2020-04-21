package route;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hello.Provider;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class User2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel ch =Provider.getC().createChannel();
        ch.exchangeDeclare("logs_direct","direct");
        final String queueName = ch.queueDeclare().getQueue();
//        临时队列基于路由的绑定。
        ch.queueBind(queueName,"logs_direct","info");
        ch.basicConsume(queueName,true,new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Message body ======" + new String(body));
            }

        });
    }
}
