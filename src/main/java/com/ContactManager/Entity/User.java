package com.ContactManager.Entity;

import java.util.List;

import jakarta.persistence.CascadeType; 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "register")
public class User {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	@Size(max = 12, message = "Maximum 12 character can be insert!!")
	@NotBlank(message = "Name field required ")
	private String name;
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email !!")
	private String email;
	@Size(max = 10, message = "Maximum 10 Number!!")
	private String phone;
	private String image;
	@Size(min = 6, message = "Minimum 6 character !!")
	private String password;
	private String role; 
	private boolean enable  ;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contact;

	public User() {
		super();
		//
	}

	public User(int id, String name, String email, String phone, String image, String password, String role,
			boolean enable, List<Contact> contact) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.contact = contact;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", image=" + image
				+ ", password=" + password + ", role=" + role + ", enable=" + enable + ", contact=" + contact + "]";
	}
}