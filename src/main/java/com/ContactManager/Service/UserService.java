package com.ContactManager.Service;

import org.springframework.web.multipart.MultipartFile;

import com.ContactManager.Entity.Contact;
import com.ContactManager.Entity.User;

public interface UserService {
	
	
	public User registerUser(User users,MultipartFile img); 
	
	public User addcontact(Contact contact,User user ,MultipartFile file);
	
	public Contact updatecontact(Contact contact,User user ,MultipartFile file);
	
	public User updateprofile(User user,MultipartFile file,String name);
	 

}
