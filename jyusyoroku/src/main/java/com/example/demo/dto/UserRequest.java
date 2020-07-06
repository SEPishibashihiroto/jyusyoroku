package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class UserRequest implements Serializable {
	/**
	 * 名前
	 */
	@NotEmpty(message = "名前を入力してください")
	private String name;
	/**
	 * 住所
	 */
	private String address;
	/**
	 * 電話番号
	 */
	private String tel;
}