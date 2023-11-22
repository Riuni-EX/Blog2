package blog.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//@Data
//デフォルトの引数なしのコンストラクタを生成します。
//@NoArgsConstructor
//全てのフィールドを引数とするコンストラクタを生成します。
//@AllArgsConstructor
//@NonNullが付いているフィールドだけを引数とするコンストラクタを生成します。

//JPAエンティティであることを示しています。
//@Entity
//@Table(name = "account")

//クラスに対して自動的に生成されるメソッドを提供します
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
//マッピングされるデータベースのテーブルの名前を指定しています。
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@NonNull
	private String userName;

	@NonNull
	private String userEmail;

	@NonNull
	private String password;

}