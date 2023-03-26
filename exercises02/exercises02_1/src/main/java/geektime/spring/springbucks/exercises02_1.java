package geektime.spring.springbucks;

import geektime.spring.springbucks.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
@SpringBootApplication
public class exercises02_1 implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(exercises02_1.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("============start================");
        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        Coffee coffee = ac.getBean("coffee", Coffee.class);
        log.info("coffee price: {}", coffee.getPrice().getNum());
        log.info("============end================");
    }
}
