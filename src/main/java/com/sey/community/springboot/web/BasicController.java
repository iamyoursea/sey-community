package com.sey.community.springboot.web;
import com.sey.community.springboot.web.dto.BasicResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/hi")
    public String hi() {
        return "Hi";
    }

    @GetMapping("/hi/dto")
    public BasicResponseDTO hellDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new BasicResponseDTO(name, amount);
    }
}