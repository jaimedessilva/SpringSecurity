package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**Project: springSecurity
 * File: UserController.java
 * @author jaime
 * Em 11-05-2020 **/

@Controller
public class UserController {

	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/")
	public String login1 () {
		return "login";
	}
	@GetMapping("/login")
	public String login () {
		return "login";
	}
	@GetMapping("/user")
	public String user () {
		return "user";
	}
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping("/error")
    public String error() {
        return "/error";
    }
}
