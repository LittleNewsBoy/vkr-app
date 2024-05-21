//package com.app.vkr.controller;
//
//import com.app.vkr.entity.AppUser;
//import com.app.vkr.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/register")
//public class UserController {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@PostMapping("/createUser")
//	public AppUser createUser(@RequestBody AppUser appUser){
//		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
//		return userRepository.save(appUser);
//	}
//
//	@GetMapping("/returnAllUsers")
//	public List<AppUser> returnAllUsers(){
//		return userRepository.findAll();
//	}
//
//	@GetMapping("/find/{id}")
//
//	public Optional<AppUser> findById(@PathVariable("id") Long id){
//		return  userRepository.findById(id);
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public void deleteById(@PathVariable("id") Long id){
//		userRepository.deleteById(id);
//	}
//}

