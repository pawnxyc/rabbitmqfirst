package hello;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class User {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel ch =Provider.getC();
        ch.basicConsume("hello",true,new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Message body ======" + new String(body));
            }

        });
    }
}
