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

//クラスに対して自動的に生成されるメソッドを提供します
@Data
//デフォルトの引数なしのコンストラクタを生成します。
@NoArgsConstructor
//全てのフィールドを引数とするコンストラクタを生成します。
@AllArgsConstructor
//@NonNullが付いているフィールドだけを引数とするコンストラクタを生成します。
@RequiredArgsConstructor
//JPAエンティティであることを示しています。
@Entity
//マッピングされるデータベースのテーブルの名前を指定しています。
@Table(name = "blog")
public class BlogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long blogId;

	@NonNull
	private String blogTitle;
	@NonNull
	private String categoryName;
	@NonNull
	private String blogImage;
	@NonNull
	private String article;
	@NonNull
	private Long accountId;

}
