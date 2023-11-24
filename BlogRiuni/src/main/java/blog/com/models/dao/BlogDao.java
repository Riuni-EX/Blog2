package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blog.com.models.entity.BlogEntity;

//Springによって管理される
@Repository
//トランザクションの管理を行う
@Transactional
public interface BlogDao extends JpaRepository<BlogEntity, Long> {
	// ブログエンティティを保存
	BlogEntity save(BlogEntity blogEntity);

	// すべてのブログエンティティを取得
	List<BlogEntity> findAll();

	// ブログIDでブログエンティティを検索
	BlogEntity findByBlogId(Long blogId);

	// ブログタイトルでブログエンティティを検索
	BlogEntity findByBlogTitle(String blogTitle);

	// ブログIDでブログエンティティを削除
	int deleteByBlogId(Long blogId);
}