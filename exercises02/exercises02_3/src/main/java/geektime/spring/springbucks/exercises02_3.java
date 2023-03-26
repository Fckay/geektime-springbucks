package geektime.spring.springbucks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class exercises02_3 implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(exercises02_3.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String url = "";
        log.info("============start================");
        url = "http://localhost:8181/coffee/getJson/1";
        RestTemplate client = new RestTemplate();
        ResponseEntity<String> jsonRes = client.exchange(url, HttpMethod.GET, new HttpEntity<String>("parameters"), String.class);
        log.info("Request getJson api:{}", jsonRes.getBody());
        url = "http://localhost:8181/coffee/getXml/1";
        ResponseEntity<String> xmlRes = client.exchange(url, HttpMethod.GET, new HttpEntity<String>("parameters"), String.class);
        log.info("Request getXml api:{}", xmlRes.getBody());
        log.info("============end================");
    }
}
