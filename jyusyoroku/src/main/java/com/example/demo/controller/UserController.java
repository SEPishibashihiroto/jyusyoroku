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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
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
	public String displayAdd(Model model) {
		model.addAttribute("addUserRequest", new UserRequest());
		return "Address/Add";
	}

	/**
	 * 登録確認画面を表示
	 * @param model Model
	 * @return 登録画面
	 */
	@PostMapping(value = "/Address/AddCheck")
	public String displayAddCheck(@ModelAttribute("addUserRequest") UserRequest addUserRequest,Model model) {
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
	UserRequest editreq;

	@PostMapping(value = "/Address/Edit")
	public String displayEdit(Model model) {
		editreq = new UserRequest();
		model.addAttribute("userRequest", editreq);
		return "Address/Edit";
	}

	/**
	 * 編集確認画面を表示
	 * @param model Model
	 * @return 編集画面
	 */
	@PostMapping(value = "/Address/EditCheck")
	public String displayEditCheck(Model model) {
		model.addAttribute("userRequest", editreq);
		return "Address/EditCheck";
	}

	/**
	 * 削除画面を表示
	 * @param model Model
	 * @return 住所一覧画面
	 */
	@PostMapping(value = "/Address/Delete")
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
	public String adderrcheck(@Validated @ModelAttribute UserRequest addUserRequest, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "Address/Add";
		}
		return "Address/AddCheck";
	}

	@RequestMapping(value = "/Address/create", method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute UserRequest userRequest) {
		// ユーザー情報の登録
		userService.create(userRequest);
		return "Address/List";
	}
}