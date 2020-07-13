package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {
	/**
	 * ユーザー情報 Repository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public List<User> searchAll() {

		return userRepository.findAll();
	}

	/**
	 * ユーザー情報 新規登録
	 * @param user ユーザー情報
	 */
	public void create(UserRequest addUserRequest) {
		User user = new User();
		user.setName(addUserRequest.getName());
		user.setAddress(addUserRequest.getAddress());
		user.setTel((addUserRequest.getTel().equals("")) ? addUserRequest.getTel()
				: addUserRequest.getTel().replace("-", ""));
		userRepository.save(user);
	}

	/**
	   * ユーザー情報 主キー検索
	   * @return 検索結果
	   */
	public User findById(int id) {
		return userRepository.findById(id).get();
	}

	/**
	 * ユーザー情報 更新
	 * @param user ユーザー情報
	 */
	public void update(UserUpdateRequest userUpdateRequest) {
		User user = findById(userUpdateRequest.getId());
		user.setAddress(userUpdateRequest.getAddress());
		user.setName(userUpdateRequest.getName());
		user.setTel((userUpdateRequest.getTel().equals("")) ? userUpdateRequest.getTel()
				: userUpdateRequest.getTel().replace("-", ""));
	}

	/**
	 * ユーザー情報 削除
	 * @param user ユーザー情報
	 */
	public void delete(UserUpdateRequest deleteUserRequest) {
		User user = findById(deleteUserRequest.getId());
		user.setDelete_flg(deleteUserRequest.getDelete_flg());
	}
}