package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blog.com.models.entity.BlogEntity;


@Repository
@Transactional
public interface BlogDao extends JpaRepository<BlogEntity, Long> {
	// save
	BlogEntity save(BlogEntity blogEntity);

	// SELECT * FROM blog
	List<BlogEntity> findAll();

	// SELECT * FROM blog WHERE blog_id=?
	BlogEntity findByBlogId(Long blogId);

	BlogEntity findByBlogTitle(String blogTitle);

	// 削除
	int deleteByBlogId(Long blogId);
}