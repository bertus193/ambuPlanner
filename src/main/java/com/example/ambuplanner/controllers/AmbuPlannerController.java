package com.example.ambuplanner.controllers;

import com.example.ambuplanner.model.AbstractNode;
import com.example.ambuplanner.model.App;
import com.example.ambuplanner.model.AppLaunch;
import com.example.ambuplanner.model.MapRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("")
public class AmbuPlannerController {

    @RequestMapping(value = "/generateMap", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity createMap(@RequestBody MapRequest map) {
        AppLaunch initApp = new AppLaunch();
        initApp.launch(map);
        return new ResponseEntity<>(App.mapsToJson(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/example")
    @CrossOrigin
    public ResponseEntity index() {
        String mapJSON = "[[1, null, null, null, null, null, null, null, null, 4], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, null, null, null, null, null, null, null, null, null], [null, \"B\", \"H\", 2, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"H\", \"B\", null], [null, null, null, null, null, null, null, 3, null, null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"H\", null, \"B\", \"B\", null],[5, null, null, null, null, null, null, 6, null, null]]";
        String notificationsJSON = "[[1, 4, 4], [5, 1, 7], [20, 4, 9]]";
        MapRequest map = new MapRequest(mapJSON, notificationsJSON);

        AppLaunch initApp = new AppLaunch();
        initApp.launch(map);
        return new ResponseEntity<>(App.mapsToJson(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init")
    public ResponseEntity initPositions() {
        List<AbstractNode> path = App.getMaps().get(0).findPath(0, 0, 5, 3);

        System.out.println(path.size() + " " + path);

        return new ResponseEntity<>("started", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public ResponseEntity home() {
        return new ResponseEntity<>("Greetings from Spring Boot!", HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(AmbuPlannerController.class, args);
    }
}
