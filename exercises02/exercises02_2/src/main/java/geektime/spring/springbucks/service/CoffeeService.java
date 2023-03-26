package geektime.spring.springbucks.service;

import geektime.spring.springbucks.mapper.CoffeeMapper;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeCache;
import geektime.spring.springbucks.repository.CoffeeCacheRepository;
import geektime.spring.springbucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private CoffeeCacheRepository cacheRepository;

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(
                Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    // mybatis实现方法

    // save
    @Transactional(rollbackOn = Exception.class)
    public int saveCoffee(Coffee coffee){
        return coffeeMapper.save(coffee);
    }

    // delete
    @Transactional(rollbackOn = Exception.class)
    public int deleteCoffee(Long id){
        return coffeeMapper.deleteById(id);
    }
    // update
    @Transactional(rollbackOn = Exception.class)
    public int updateCoffee(Coffee coffee){
        return coffeeMapper.update(coffee);
    }

    /**
     * 根据id查询coffee
     * @param id id
     * @return Coffee
     */
    public Coffee findCoffeeById(Long id){
        return coffeeMapper.findById(id);
    }

    /**
     * 根据名称查询coffee
     * @param name 名称
     * @return Coffee
     */
    public Coffee findCoffeeByName(String name){
        Optional<CoffeeCache> cache = cacheRepository.findOneByName(name);
        if(cache.isPresent()) {
            CoffeeCache coffeeCache = cache.get();
            Coffee coffee = Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            return coffee;
        }else {
            Coffee coffee = coffeeMapper.findByName(name);
            if(coffee!=null){
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .id(coffee.getId())
                        .name(coffee.getName())
                        .price(coffee.getPrice())
                        .build();
                cacheRepository.save(coffeeCache);
            }
            return coffee;
        }
    }

    /**
     * 查询所有coffee
     * @return Coffee列表
     */
    public List<Coffee> findAllCoffee(){
        List<Coffee> coffeeList = coffeeMapper.findAll();
        List<CoffeeCache> coffeeCacheList = coffeeList.stream().map(coffee -> CoffeeCache.builder()
                .id(coffee.getId())
                .name(coffee.getName())
                .price(coffee.getPrice())
                .build()).collect(Collectors.toList());
//        cacheRepository.saveAll(coffeeCacheList);
        return coffeeList;
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return Coffee列表
     */
    public List<Coffee> findAllCoffeeWithParam(int pageNum, int pageSize){
        List<Coffee> coffeeList = coffeeMapper.findAllWithParam(pageNum, pageSize);
        // 将结果缓存到redis中
        List<CoffeeCache> coffeeCacheList = coffeeList.stream().map(coffee -> CoffeeCache.builder()
                .id(coffee.getId())
                .name(coffee.getName())
                .price(coffee.getPrice())
                .build()).collect(Collectors.toList());
        cacheRepository.saveAll(coffeeCacheList);
        return coffeeList;
    }

    /**
     * 使用RowBounds分页
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return Coffee列表
     */
    public List<Coffee> findAllCoffeeWithRowBounds(int pageNum, int pageSize){
        List<Coffee> coffeeList = coffeeMapper.findAllWithRowBounds(new RowBounds(pageNum, pageSize));
        List<CoffeeCache> coffeeCacheList = coffeeList.stream().map(coffee -> CoffeeCache.builder()
                .id(coffee.getId())
                .name(coffee.getName())
                .price(coffee.getPrice())
                .build()).collect(Collectors.toList());
        cacheRepository.saveAll(coffeeCacheList);
        return coffeeList;
    }

    /**
     * // 根据主键批量查询
     * @param ids 主键列表
     * @return Coffee列表
     */
    public List<Coffee> findAllCoffeeByIds(List<Long> ids){
        List<Coffee> coffeeList = coffeeMapper.findAllByIds(ids);
        List<CoffeeCache> coffeeCacheList = coffeeList.stream().map(coffee -> CoffeeCache.builder()
                .id(coffee.getId())
                .name(coffee.getName())
                .price(coffee.getPrice())
                .build()).collect(Collectors.toList());
        cacheRepository.saveAll(coffeeCacheList);
        return coffeeList;
    }

}
