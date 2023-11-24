package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.AccountEntity;
import blog.com.models.entity.BlogEntity;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

//コントローラークラス
@Controller
public class BlogController {
	// ブログサービスの自動ワイヤリング
	@Autowired
	private BlogService blogService;
	// セッションの自動ワイヤリング
	@Autowired
	private HttpSession session;

	// ブログ一覧を取得するエンドポイント
	@GetMapping("/blog")
	public String getBlogList(Model model) {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";// アカウントが存在しない場合はログインページにリダイレクト
		} else {
			// ブログ一覧を取得し、モデルに追加
			List<BlogEntity> blogList = blogService.selectAll(account.getUserId());
			model.addAttribute("blogList", blogList);
			model.addAttribute("accountName", account.getUserName());
			return "blog_list.html";

		}
	}

	// ブログ登録ページを取得するエンドポイント
	@GetMapping("/blog/register")
	public String getBlogRegisterPage() {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";// アカウントが存在しない場合はログインページにリダイレクト
		} else {
			return "blog_register.html";
		}
	}

	// ブログを保存するエンドポイント
	@PostMapping("/blog/save")
	public String saveBlog(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam String article, @RequestParam MultipartFile blogImage, Model model) {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";// アカウントが存在しない場合はログインページにリダイレクト
		} else {
			// 現在の日時（年、月、日、時、分、秒）を表す文字列に、アップロードされたブログ画像の元のファイル名を連結しています。
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			try {
				// ブログ画像を保存
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ブログを保存
			if (blogService.saveBlog(blogTitle, categoryName, fileName, article, account.getUserId())) {
				return "redirect:/blog";
			} else {
				// 保存ができなかった場合の処理
				return "redirect:/blog/register";
			}
		}
	}

	@PostMapping("/blog/update")
	public String updateBlog(@RequestParam Long blogId, @RequestParam String blogTitle,
			@RequestParam String categoryName, @RequestParam String article, @RequestParam MultipartFile blogImage,
			Model model) {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";// アカウントが存在しない場合はログインページにリダイレクト
		} else {
			// ブログIDがnullでない場合のみ処理を行う
			if (blogId != null) {
				// 現在の日時（年、月、日、時、分、秒）を表す文字列に、アップロードされたブログ画像の元のファイル名を連結しています。
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
						+ blogImage.getOriginalFilename();
				try {
					// ブログ画像を保存
					Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				// ブログを更新
				if (blogService.updateBlog(blogTitle, categoryName, fileName, article, account.getUserId())) {
					return "redirect:/blog";
				} else {
					// 更新ができなかった場合の処理
					model.addAttribute("blog",
							new BlogEntity(blogId, blogTitle, categoryName, fileName, article, account.getUserId()));
					return "redirect:/blog/edit";
				}
			}
		}
		// ブログIDがnullの場合は何もせずにリダイレクト
		return "redirect:/blog";
	}

	// ブログ編集ページを取得するエンドポイント
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		} else {
			// アカウント名をモデルに追加
			model.addAttribute("accountName", account.getUserName());
			// ブログIDに対応するブログを取得
			BlogEntity blog = blogService.getBlogByBlogId(blogId);
			if (blog == null) {
				return "redirect:/blog";// ブログが存在しない場合はブログ一覧にリダイレクト
			} else {
				// ブログをモデルに追加し、編集ページに遷移
				model.addAttribute("blog", blog);
				return "blog_edit.html";
			}

		}
	}

	// ブログ削除するエンドポイント
	@PostMapping("/blog/delete")
	public String deleteBlog(@RequestParam Long blogId) {
		// アカウント情報をセッションから取得
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";// アカウントが存在しない場合はログインページにリダイレクト
		} else {
			// ブログを削除し、ブログ一覧にリダイレクト
			blogService.deleteBlog(blogId);
			return "redirect:/blog";
		}
	}

}
