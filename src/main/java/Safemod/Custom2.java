package Safemod;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hello.Provider;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Custom2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel ch = Provider.getC().createChannel();
        ch.queueDeclare("hello",false,false,false,null);
        ch.basicQos(1);//一次接受一条消息，不会平均分配
        ch.basicConsume("hello",false,new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Message body ======" + new String(body));
                ch.basicAck(envelope.getDeliveryTag(),false);//手动确认消息处理完毕
            }

        });
    }
}
