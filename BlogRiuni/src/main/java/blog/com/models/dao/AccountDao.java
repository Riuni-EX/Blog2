package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.AccountEntity;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {

	// 保存処理
	AccountEntity save(AccountEntity accountEntity);

	// SELECT ＊ FROM account WHERE user_email=?
	AccountEntity findByUserEmail(String userEmail);

	// SELECT ＊ FROM account WHERE user_email=? AND password = ?
	AccountEntity findByUserEmailAndPassword(String userEmail, String password);

}
