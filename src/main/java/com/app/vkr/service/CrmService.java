package com.app.vkr.service;

import com.app.vkr.entity.AppUser;
import com.app.vkr.repo.TodoRepo;
import com.app.vkr.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

	@Autowired
	private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

	public CrmService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<AppUser> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(stringFilter);
        }
    }

    public long countUser() {
        return userRepository.count();
    }

    public void deleteUser(AppUser user) {
        userRepository.delete(user);
    }

    public void saveUser(AppUser user) {
        if (user == null) {
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return;
        }
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
