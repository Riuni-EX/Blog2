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

@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	@GetMapping("/blog")
	public String getBlogList(Model model) {
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		} else {
			List<BlogEntity> blogList = blogService.selectAll(account.getUserId());
			model.addAttribute("blogList", blogList);
			model.addAttribute("accountName", account.getUserName());
			return "blog_list.html";
		}
	}

	@GetMapping("/blog/register")
	public String getBlogRegisterPage() {
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		} else {
			return "blog_register.html";
		}
	}

	@PostMapping("/blog/save")
	public String blogRegister(@RequestParam Long blogId, @RequestParam String blogTitle,
			@RequestParam String categoryName, @RequestParam String article, @RequestParam MultipartFile blogImage,
			Model model) {

		AccountEntity account = (AccountEntity) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		} else {
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (blogService.saveBlog(blogId, blogTitle, categoryName, fileName, article, account.getUserId())) {
				return "redirect:/blog";
			} else {
				if (blogId == null) {
					return "redirect:/blog/register";
				} else {
					model.addAttribute("blog",
							new BlogEntity(blogId, blogTitle, categoryName, fileName, article, account.getUserId()));
					return "redirect:/blog/edit";
				}
			}
		}
	}

}
