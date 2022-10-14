package com.lgl.gms.webapi.sample.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SampleController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index(Locale locale) {
		return "Greetings from Spring Boot - LGL-GMS Backend API Server!!!";
	}

}
