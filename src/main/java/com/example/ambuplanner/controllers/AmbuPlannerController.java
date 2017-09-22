package com.example.ambuplanner.controllers;

import com.example.ambuplanner.model.App;
import com.example.ambuplanner.model.Coordinate;
import com.example.ambuplanner.model.Position;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@SpringBootApplication
@RestController
@RequestMapping("")
public class AmbuPlannerController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Position>> getAllPositions() {
		System.out.println(App.avaiablePositionsAround(new Coordinate(0,0)));
		return new ResponseEntity<>(App.getPositions(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init")
	public ResponseEntity initPositions() {
		App.initPositions();
		App.printMap();
		return new ResponseEntity<>("iniciado", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home")
	public ResponseEntity index() {
		return new ResponseEntity<>("Greetings from Spring Boot!",HttpStatus.OK);
	}

	public static void main(String[] args) {
		SpringApplication.run(AmbuPlannerController.class, args);
	}
}
