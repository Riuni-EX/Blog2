package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.AccountEntity;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private HttpSession session;

	@GetMapping("/login")
	public String getLoginPage() {
		return "account_login.html";
	}

	@PostMapping("/account/login")
	public String login(@RequestParam String userEmail, @RequestParam String password) {
		AccountEntity account = accountService.checkLogin(userEmail, password);
		if (account == null) {
			return "redirect:/login";
		} else {
			session.setAttribute("account", account);
			return "redirect:/blog";
		}
	}
	 @GetMapping("/account/registers")
	    public String showRegisterPage() {
	        return "account_register";
	    }
		@GetMapping("/logout")
		public String logout() {
			session.invalidate();
			return "redirect:/login";
		}
}