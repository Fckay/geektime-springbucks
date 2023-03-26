package geektime.spring.springbucks.controller;

import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    //通过id获取coffee返回json
    @GetMapping(path = "/getJson/{id}", produces = "application/json")
    public Coffee getListForJson(@PathVariable Long id) {
        return coffeeService.findCoffeeById(id);
    }

    // 通过id获取coffee返回xml
    @GetMapping(path = "/getXml/{id}", produces = "application/xml")
    public Coffee getListForXml(@PathVariable Long id) {
        return coffeeService.findCoffeeById(id);
    }
}
