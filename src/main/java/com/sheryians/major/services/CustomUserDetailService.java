package com.sheryians.major.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sheryians.major.model.CustomeUserDetails;
import com.sheryians.major.model.User;
import com.sheryians.major.repository.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = repository.findUserByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException("User Toh nahi mila bro -~-"));
		return user.map(CustomeUserDetails::new).get();
	}

}
