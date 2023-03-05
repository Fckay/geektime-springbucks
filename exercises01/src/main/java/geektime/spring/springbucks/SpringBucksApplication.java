package geektime.spring.springbucks;

import geektime.spring.springbucks.converter.BytesToMoneyConverter;
import geektime.spring.springbucks.converter.MoneyToBytesConverter;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeOrder;
import geektime.spring.springbucks.model.OrderState;
import geektime.spring.springbucks.repository.CoffeeRepository;
import geektime.spring.springbucks.service.CoffeeOrderService;
import geektime.spring.springbucks.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableRedisRepositories
public class SpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;
	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private CoffeeOrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(
				Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("All Coffee: {}", coffeeRepository.findAll());

		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		if (latte.isPresent()) {
			CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
		}
		log.info("============start================");
		log.info("saveCoffee: {}", coffeeService.saveCoffee(new Coffee("coke", Money.parse("CNY 66.0"))));
		log.info("findCoffeeById: {}", coffeeService.findCoffeeById(1L));
		log.info("findCoffeeByName: {}", coffeeService.findCoffeeByName("latte"));
		log.info("inCacheFindCoffeeByName: {}", coffeeService.findCoffeeByName("latte"));
		log.info("findAllCoffee: {}", coffeeService.findAllCoffee());
		log.info("findAllCoffeeWithParam: {}", coffeeService.findAllCoffeeWithParam(1,2));
		log.info("findAllCoffeeWithRowBounds: {}", coffeeService.findAllCoffeeWithRowBounds(1,2));
		log.info("findAllCoffeeByIds: {}", coffeeService.findAllCoffeeByIds(Arrays.asList(1L,2L)));
		log.info("updateCoffee: {}", coffeeService.updateCoffee(new Coffee("coke",Money.parse("CNY 88.0"))));
		log.info("deleteCoffee: {}", coffeeService.deleteCoffee(1L));
		log.info("============end================");
	}
}

