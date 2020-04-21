package Safemod;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hello.Provider;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Custom1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel ch = Provider.getC().createChannel();
        ch.queueDeclare("hello",false,false,false,null);
        ch.basicQos(1);
        ch.basicConsume("hello",false,new DefaultConsumer(ch){
            @lombok.SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Thread.sleep(2000);
                System.out.println("Message body ======" + new String(body));
                ch.basicAck(envelope.getDeliveryTag(),false);
            }

        });
    }
}
