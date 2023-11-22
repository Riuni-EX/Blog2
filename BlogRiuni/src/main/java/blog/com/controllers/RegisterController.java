package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

@Controller
public class RegisterController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/register")
	public String getRegisterPage() {
		return "account_register.html";
	}

	@PostMapping("/account/register")
	public String register(@RequestParam(required = true) String userName, @RequestParam(required = true) String userEmail,
			@RequestParam(required = true) String password) {
		if (accountService.createAccount(userName, userEmail, password)) {
			return "redirect:/login";
		} else {
			return "redirect:/register";
		}
	}
}
