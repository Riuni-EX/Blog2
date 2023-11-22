package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	public List<BlogEntity> selectAll(Long adminId) {
		if (adminId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	public BlogEntity getBlogByBlogId(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	public boolean saveBlog(Long blogId, String blogTitle, String categoryName, String BlogImage, String article,
			Long accountId) {
		if (blogId == null) {
			if (blogDao.findByBlogTitle(blogTitle) == null) {
				blogDao.save(new BlogEntity(blogTitle, categoryName, BlogImage, article, accountId));
				return true;
			} else {
				return false;
			}
		} else {
			// 更新処理
			blogDao.save(new BlogEntity(blogId, blogTitle, categoryName, BlogImage, article, accountId));
			return true;
		}
	}
}