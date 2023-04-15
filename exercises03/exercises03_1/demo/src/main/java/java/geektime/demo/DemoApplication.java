package java.geektime.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;



@SpringBootApplication
@EnableBinding({ Source.class, Sink.class })
public class DemoApplication {

    @Autowired
    private Source source;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        DemoApplication app = new DemoApplication();
        app.sendMessage("Hello RabbitMQ");
//        app.sendMessage("Hello Kafka");
    }

    public void sendMessage(String message) {
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(Sink.INPUT)
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
