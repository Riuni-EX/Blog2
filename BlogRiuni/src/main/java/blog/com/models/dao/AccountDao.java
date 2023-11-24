package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.AccountEntity;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {
	// アカウントエンティティを保存
	AccountEntity save(AccountEntity accountEntity);

	// メールアドレスでアカウントを検索
	AccountEntity findByUserEmail(String userEmail);

	// メールアドレスとパスワードでアカウントを検索
	AccountEntity findByUserEmailAndPassword(String userEmail, String password);

}
