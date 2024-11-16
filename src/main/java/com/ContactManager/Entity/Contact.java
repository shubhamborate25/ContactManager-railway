package com.ContactManager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int cid;
	private String name;
	private String email;
	private String phone;
	private String work;
	private String imgurl;
	@Column(length = 1000)
	private String description;
	private boolean favorite;
	@ManyToOne()
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	 

	public Contact(int cid, String name, String email, String phone, String work, String imgurl, String description,
			boolean favorite, User user) {
		super();
		this.cid = cid;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.work = work;
		this.imgurl = imgurl;
		this.description = description;
		this.favorite = favorite;
		this.user = user;
	}



	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	

//	@Override
//	public String toString() {
//		return "Contact [cid=" + cid + ", name=" + name + ", email=" + email + ", phone=" + phone + ", work=" + work
//				+ ", image=" + imgurl + ", description=" + description + ", user=" + user + "]";
//	}

}
