package ex.geektime.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableBinding({ Source.class, Sink.class })
@Component
public class DemoApplication {

    @Autowired
    private Source source;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void sendMessage(String message) {
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(Sink.INPUT)
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
