package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

//コントローラークラス
@Controller
public class RegisterController {
	// ブログサービスの自動ワイヤリング
	@Autowired
	private AccountService accountService;

	// 登録ページを取得
	@GetMapping("/register")
	public String getRegisterPage() {
		return "account_register.html";
	}

	// アカウントの登録を試みる
	@PostMapping("/account/register")
	public String register(@RequestParam String userName, @RequestParam String userEmail,
			@RequestParam String password) {
		// アカウントを作成し、成功した場合はログインページにリダイレクト
		if (accountService.createAccount(userName, userEmail, password)) {
			return "redirect:/login";
		} else {
			return "redirect:/register";// 登録に失敗した場合は登録ページに戻る
		}
	}
}
