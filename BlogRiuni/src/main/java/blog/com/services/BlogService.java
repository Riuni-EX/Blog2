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

	// 指定された管理者IDに関連するすべてのブログを取得します。
	public List<BlogEntity> selectAll(Long adminId) {
		// 管理者IDがnullの場合はnullを返す
		if (adminId == null) {
			return null;
		} else {
			// すべてのブログを取得
			return blogDao.findAll();
		}
	}

//指定されたブログIDに対応するブログを取得します。
	public BlogEntity getBlogByBlogId(Long blogId) {
		// ブログIDがnullの場合はnullを返す
		if (blogId == null) {
			return null;
		} else {
			// ブログIDに対応するブログを取得
			return blogDao.findByBlogId(blogId);
		}
	}

	// ブログの保存
	public boolean saveBlog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		// 新規作成の場合
		if (accountId == null) {
			// タイトルが重複していないか確認
			if (blogDao.findByBlogTitle(blogTitle) == null) {
				// 保存
				blogDao.save(new BlogEntity(blogTitle, categoryName, blogImage, article, accountId));
				return true; // 保存が成功した場合はtrue、重複がある場合はfalse
			} else {
				// 重複がある場合は保存できない
				return false;
			}
		}
		// 更新処理を行う
		return updateBlog(blogTitle, categoryName, blogImage, article, accountId);
	}

	// ブログの更新
	public boolean updateBlog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		// 更新処理を行う
		blogDao.save(new BlogEntity(blogTitle, categoryName, blogImage, article, accountId));
		return true;
	}

//指定されたブログIDに対応するブログを削除します。
	public boolean deleteBlog(Long blogId) {
		// ブログIDがnullの場合は削除できない
		if (blogId == null) {
			return false;
		} else {
			// ブログを削除
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}

}