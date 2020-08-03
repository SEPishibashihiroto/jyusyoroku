package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.SeachRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserController {
	/**
	 * ユーザー情報 Service
	 */
	@Autowired
	UserService userService;

	/**
	 * 住所一覧画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@GetMapping(value = "/Address/List")
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model,
			@ModelAttribute("SeachRequest") SeachRequest SeachRequest) {

		String SeachName = (SeachRequest.getSeachName() == null) ? "" : SeachRequest.getSeachName();

		Page<User> userPage = userService.getSeachUsers(SeachName, pageable);

		model.addAttribute("page", userPage);
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("SeachName", SeachName);
		model.addAttribute("SeachRequest", SeachRequest);

		return "Address/List";
	}

	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@GetMapping(value = "/Address/Add")
	public String displayAdd(@ModelAttribute("addUserRequest") UserRequest addUserRequest, Model model) {
		model.addAttribute("addUserRequest", addUserRequest);

		return "Address/Add";
	}

	/**
	 * ユーザー新規登録  エラー確認
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@PostMapping(value = "/Address/adderrcheck")
	public String adderrcheck(@Validated @ModelAttribute("addUserRequest") UserRequest addUserRequest,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("addUserRequest", addUserRequest);
			return "Address/Add";
		}
		model.addAttribute("addUserRequest", addUserRequest);
		return "Address/AddCheck";
	}

	/**
	 * ユーザー新規登録  登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@PostMapping(value = "/Address/create")
	public String create(@Validated @ModelAttribute("addUserRequest") UserRequest addUserRequest, BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("addUserRequest", addUserRequest);
		userService.create(addUserRequest);
		return "redirect:/Address/List";
	}

	/**
	 * 編集画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */

	@GetMapping(value = "/Address/{id}/Edit")
	public String displayEdit(@PathVariable int id, Model model) {
		User user = userService.findById(id);
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setTel((user.getTel().equals("")) ? user.getTel()
				: new StringBuilder(user.getTel()).insert(3, "-").insert(8, "-").toString());
		userUpdateRequest.setAddress(user.getAddress());

		model.addAttribute("editUserRequest", userUpdateRequest);
		return "Address/Edit";
	}

	/**
	 * 登録編集  エラー確認
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@PostMapping(value = "/Address/editerrcheck")
	public String editerrcheck(@Validated @ModelAttribute("editUserRequest") UserUpdateRequest editUserRequest,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("editUserRequest", editUserRequest);
			return "Address/Edit";
		}
		model.addAttribute("editUserRequest", editUserRequest);
		return "Address/EditCheck";
	}

	/**
	 * 登録編集  編集
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@PostMapping(value = "/Address/update")
	public String update(@Validated @ModelAttribute("editUserRequest") UserUpdateRequest editUserRequest,
			BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("editUserRequest", editUserRequest);

		userService.update(editUserRequest);
		return "redirect:/Address/List";
	}

	/**
	 * 削除画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@GetMapping(value = "/Address/{id}/Delete")
	public String displayDelete(@PathVariable int id, Model model) {
		User user = userService.findById(id);
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setTel((user.getTel().equals("")) ? user.getTel()
				: new StringBuilder(user.getTel()).insert(3, "-").insert(8, "-").toString());
		userUpdateRequest.setAddress(user.getAddress());
		userUpdateRequest.setDelete_flg(user.getDelete_flg());

		model.addAttribute("deleteUserRequest", userUpdateRequest);
		return "Address/Delete";
	}

	/**
	 * 削除  delete_flgを1にupdate
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@PostMapping(value = "/Address/delete")
	public String delete(@Validated @ModelAttribute("deleteUserRequest") UserUpdateRequest deleteUserRequest,
			BindingResult result,
			Model model) {
		String deleteflg = "1";

		deleteUserRequest.setDelete_flg(deleteflg);

		model.addAttribute("deleteUserRequest", deleteUserRequest);

		userService.delete(deleteUserRequest);
		return "redirect:/Address/List";
	}

}