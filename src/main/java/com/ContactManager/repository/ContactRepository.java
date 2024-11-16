package com.ContactManager.repository;
 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ContactManager.Entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	@Query("select c from Contact c where c.user.id =:userId ")
	public  Page<Contact> findByUser(@Param("userId")int userId,Pageable pePageable );
 	
 	@Query("select count(c) from Contact c where c.user.id =:userId ")
 	long countContact(@Param("userId")int userId);
 	
 	@Query("select count(c) from Contact c where c.user.id =:userId and c.favorite = true ")
 	long countFavourite(@Param("userId")int userId);

 	@Query("select c from Contact c where c.user.id =:userId and c.favorite = true ")
 	public  Page<Contact> favorite(@Param("userId")int userId,Pageable pePageable );
}
