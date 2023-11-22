package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AccountDao;
import blog.com.models.entity.AccountEntity;


@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;

	public boolean createAccount(String email, String name, String password) {
		if (accountDao.findByUserEmail(email) == null) {
			accountDao.save(new AccountEntity(email, name, password));
			return true;
		} else {
			return false;
		}
	}

	public AccountEntity checkLogin(String email, String password) {
		AccountEntity accountEntity = accountDao.findByUserEmailAndPassword(email, password);
		if (accountEntity == null) {
			return null;
		} else {
			return accountEntity;
		}
	}
}