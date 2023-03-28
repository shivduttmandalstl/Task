package com.employee.management.system.Task.configuration;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.employee.management.system.Task.exception.UserNotFoundException;
import com.employee.management.system.Task.repository.TaskRepository;


@Component
public class UserInfoUserDetailsService implements UserDetailsService{
	@Autowired
    private TaskRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<String> userInfo = repository.getUserDetails(username);
        try {
			return userInfo.map(UserInfoUserDetails::new)
					.orElseThrow(()-> new UserNotFoundException("User Not Found"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return null;
		}
         

    }

}
