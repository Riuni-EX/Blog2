package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.AccountEntity;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

//コントローラークラス
@Controller
public class LoginController {
	// ブログサービスの自動ワイヤリング
	@Autowired
	private AccountService accountService;
	// セッションの自動ワイヤリング
	@Autowired
	private HttpSession session;

	// ログインページを取得
	@GetMapping("/login")
	public String getLoginPage() {
		return "account_login.html";
	}

	// アカウントのログインを試みる
	@PostMapping("/account/login")
	public String login(@RequestParam String userEmail, @RequestParam String password) {
		// アカウントを確認し、セッションに設定
		AccountEntity account = accountService.checkLogin(userEmail, password);
		if (account == null) {
			return "redirect:/login";// ログインに失敗した場合はログインページにリダイレクト
		} else {
			session.setAttribute("account", account);// セッションにアカウントを設定し、ブログページにリダイレクト
			return "redirect:/blog";
		}
	}

	@Controller
	public class AccountController {
		// 登録ページを表示
		@GetMapping("/account/register")
		public String showRegisterPage() {
			return "account_register";
		}
	}

	// ログアウト処理
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();// セッションを無効化し、ログインページにリダイレクト
		return "redirect:/login";
	}
}
