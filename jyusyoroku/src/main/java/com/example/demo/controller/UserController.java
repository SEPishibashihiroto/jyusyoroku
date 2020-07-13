package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String displayList(Model model) {
		List<User> userlist = userService.searchAll();
		model.addAttribute("userlist", userlist);
		return "Address/List";
	}

	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@GetMapping(value = "/Address/Add")
	public String displayAdd1(Model model) {
		model.addAttribute("addUserRequest", new UserRequest());
		return "Address/Add";
	}

	/**
	 * 登録確認画面を表示
	 * @param model Model
	 * @return 登録画面
	 */
	@PostMapping(value = "/Address/AddCheck")
	public String displayAddCheck(@ModelAttribute("addUserRequest") UserRequest addUserRequest, Model model) {
		model.addAttribute("addUserRequest", addUserRequest);
		model.addAttribute("name", addUserRequest.getName());
		model.addAttribute("address", addUserRequest.getAddress());
		model.addAttribute("tel", addUserRequest.getTel());
		return "Address/AddCheck";
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
		userUpdateRequest.setTel(user.getTel());
		userUpdateRequest.setAddress(user.getAddress());

		model.addAttribute("editUserRequest", userUpdateRequest);
		return "Address/Edit";
	}

	/**
	 * 編集確認画面を表示
	 * @param model Model
	 * @return 編集画面
	 */
	@PostMapping(value = "/Address/EditCheck")
	public String displayEditCheck(@ModelAttribute("editUserRequest") UserUpdateRequest editUserRequest, Model model) {
		model.addAttribute("editUserRequest", editUserRequest);
		model.addAttribute("id", editUserRequest.getId());
		model.addAttribute("name", editUserRequest.getName());
		model.addAttribute("address", editUserRequest.getAddress());
		model.addAttribute("tel", editUserRequest.getTel());
		return "Address/EditCheck";
	}

	/**
	 * 削除画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@GetMapping(value = "/Address/Delete")
	public String displayDelete(Model model) {
		return "Address/Delete";
	}

	/**
	 * ユーザー新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@RequestMapping(value = "/Address/adderrcheck", method = RequestMethod.POST)
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
			model.addAttribute("name", addUserRequest.getName());
			model.addAttribute("address", addUserRequest.getAddress());
			model.addAttribute("tel", addUserRequest.getTel());
			return "Address/Add";
		}
		model.addAttribute("addUserRequest", addUserRequest);
		model.addAttribute("name", addUserRequest.getName());
		model.addAttribute("address", addUserRequest.getAddress());
		model.addAttribute("tel", addUserRequest.getTel());
		return "Address/AddCheck";
	}

	@RequestMapping(value = "/Address/create", method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute("addUserRequest") UserRequest addUserRequest, BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("addUserRequest", addUserRequest);
		model.addAttribute("name", addUserRequest.getName());
		model.addAttribute("address", addUserRequest.getAddress());
		model.addAttribute("tel", addUserRequest.getTel());
		System.out.println(addUserRequest.getName());
		System.out.println(addUserRequest.getAddress());
		System.out.println(addUserRequest.getTel());
		userService.create(addUserRequest);
		return "redirect:/Address/List";
	}

	/**
	 * 登録編集
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@RequestMapping(value = "/Address/editerrcheck", method = RequestMethod.POST)
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
			model.addAttribute("name", editUserRequest.getName());
			model.addAttribute("address", editUserRequest.getAddress());
			model.addAttribute("tel", editUserRequest.getTel());
			return "Address/Edit";
		}
		model.addAttribute("editUserRequest", editUserRequest);
		model.addAttribute("name", editUserRequest.getName());
		model.addAttribute("address", editUserRequest.getAddress());
		model.addAttribute("tel", editUserRequest.getTel());
		return "Address/EditCheck";
	}

	@RequestMapping(value = "/Address/update", method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute("editUserRequest") UserUpdateRequest editUserRequest,
			BindingResult result,
			Model model) {
		// ユーザー情報の登録
		model.addAttribute("editUserRequest", editUserRequest);
		model.addAttribute("name", editUserRequest.getName());
		model.addAttribute("address", editUserRequest.getAddress());
		model.addAttribute("tel", editUserRequest.getTel());

		userService.update(editUserRequest);
		return "redirect:/Address/List";
	}
}