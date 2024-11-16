package com.ContactManager.Custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ContactManager.Entity.User;
import com.ContactManager.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User username2 = userrepo.getUsername(username);
		if (username2==null) {
			throw new  UsernameNotFoundException("Could not found Users");
		}
		
		CustomUserDetails custome=new CustomUserDetails(username2); 
		return custome;
	}

}
