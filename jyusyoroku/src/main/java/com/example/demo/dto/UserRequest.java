package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class UserRequest implements Serializable {
	/**
	 * 名前
	 */
	@NotEmpty(message = "名前は必須項目です")
	@Size(max = 40, message = "名前は全角20文字以内で入力してください")
	private String name;
	/**
	 * 住所
	 */
	@NotEmpty(message = "住所は必須項目です")
	@Size(max = 80, message = "住所は全角40文字以内で入力してください")
	private String address;
	/**
	 * 電話番号
	 */
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "電話番号は「000-0000-0000」の形式で入力してください")
	private String tel;

	/*getter*/
	/*
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}
*/
}