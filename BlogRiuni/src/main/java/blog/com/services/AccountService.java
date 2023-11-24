package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AccountDao;
import blog.com.models.entity.AccountEntity;

@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;

	// 新しいアカウントを作成します。
	public boolean createAccount(String email, String name, String password) {
		// メールアドレスが存在しない場合、新しいアカウントを保存
		if (accountDao.findByUserEmail(email) == null) {
			accountDao.save(new AccountEntity(email, name, password));
			return true;
		} else {
			// 既に存在する場合は作成できない
			return false;
		}
	}

//ログイン情報を検証し、アカウントエンティティを返します。
	public AccountEntity checkLogin(String email, String password) {
		// メールアドレスとパスワードでアカウントを検索
		AccountEntity accountEntity = accountDao.findByUserEmailAndPassword(email, password);
		if (accountEntity == null) {
			return null;
		} else {
			// 該当するアカウントがない場合は null を返す
			return accountEntity;
		}
	}
}