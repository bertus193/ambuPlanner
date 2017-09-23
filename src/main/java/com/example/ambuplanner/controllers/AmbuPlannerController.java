package com.example.ambuplanner.controllers;

import com.example.ambuplanner.model.AbstractNode;
import com.example.ambuplanner.model.App;
import com.example.ambuplanner.model.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("")
public class AmbuPlannerController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<AbstractNode>> getAllPositions() {
        return new ResponseEntity<>(App.getNodes(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init")
    public ResponseEntity initPositions() {
        App.initNodePositions();
        App.printMap();
        Map<AbstractNode> myMap = new Map<>();

        List<AbstractNode> path = myMap.findPath(0, 0, 5, 3);

        System.out.println(path.size() + " " + path);

		/*for (int i = 0; i < path.size(); i++) {
            System.out.print("(" + path.get(i).getxPosition() + ", " + path.get(i).getyPosition() + ") -> ");
		}*/
        return new ResponseEntity<>("iniciado", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public ResponseEntity index() {
        return new ResponseEntity<>("Greetings from Spring Boot!", HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(AmbuPlannerController.class, args);
    }
}
