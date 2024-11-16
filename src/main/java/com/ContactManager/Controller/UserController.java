package com.ContactManager.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ContactManager.Entity.Contact;
import com.ContactManager.Entity.User;
import com.ContactManager.Service.UserImp;
import com.ContactManager.repository.ContactRepository;
import com.ContactManager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserImp imp;

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserRepository userrepo;

	@Autowired
	public ContactRepository contactRepository;

	@ModelAttribute
	public void msg(Model model, Principal principal) {
		if (principal != null) {
			String name = principal.getName();

			User username = userrepo.getUsername(name);
			model.addAttribute("username", username.getName());
			model.addAttribute("image", username.getImage() );
			
			
		}

	}

//	Home

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home");
		model.addAttribute("home", "active");

		return "index";

	}

//	Dashboard

	@RequestMapping("/user/dashboard")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "Dashboard");
		model.addAttribute("dashboard", "active");

		String name = principal.getName();

		User user = userrepo.getUsername(name);
 
		long countContact = contactRepository.countContact(user.getId());
		long countFavourite = contactRepository.countFavourite(user.getId());

		model.addAttribute("countContact", countContact);
		model.addAttribute("countFavourite", countFavourite);

		return "User/dashboard";

	}

//	Add Contact 

	@RequestMapping("/user/add-contact")
	public String addcontact(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("addcontact", "active");
		return "User/add_contact";

	}

//	Contact process 

	@PostMapping("/user/contact-process")
	public String contactprocess(@ModelAttribute("contact") Contact contact, @RequestParam("img") MultipartFile img,
			@RequestParam(value = "favorite", defaultValue = "false") boolean fav, Model model, Principal principal) {

		String name = principal.getName();
		User user = userrepo.getUsername(name);
		contact.setFavorite(fav);
		imp.addcontact(contact, user, img);

		model.addAttribute("msg", "Contact added Successfully ");
		model.addAttribute("addcontact", "active");

		return "User/add_contact";

	}

//	View Contact 

	@RequestMapping("/user/view-contact")
	public String viewcontact(@RequestParam(value = "page", defaultValue = "0") int page, Model model,
			Principal principal) {
		model.addAttribute("title", "View Contact");
		model.addAttribute("viewcontact", "active");

		String name = principal.getName();
		User user = userrepo.getUsername(name);

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Contact> contact = contactRepository.findByUser(user.getId(), pageRequest);

		model.addAttribute("contacts", contact);

		return "User/view_contact";
	}

//	Favorite Contact 

	@RequestMapping("/user/favorites")
	public String favoritecontact(@RequestParam(value = "page", defaultValue = "0") int page, Model model,
			Principal principal) {

		model.addAttribute("title", "Favorite Contact");
		model.addAttribute("viewcontact", "active");
		String name = principal.getName();
		User user = userrepo.getUsername(name);

		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Contact> favorite = contactRepository.favorite(user.getId(), pageRequest);

		model.addAttribute("contacts", favorite);

		return "User/favorites";
	}

//	delete contact

	@GetMapping("/user/delete-contact/{cid}")
	public String deleteContact(@PathVariable("cid") int cid, Model model,HttpSession session) {
		contactRepository.deleteById(cid);
		session.setAttribute("msg", "Contact Deleted Successfully ");
		return "redirect:/user/view-contact";
	}

//	Contact details 

	@RequestMapping("/user/contact-detail")
	public String Contactdetail(@RequestParam("cid") int cid, Model model, Principal principal) {

		model.addAttribute("title", "  Contact Details ");
		model.addAttribute("viewcontact", "active");
		String name = principal.getName();
		User user = userrepo.getUsername(name);
		Optional<Contact> byId = contactRepository.findById(cid);
		Contact contact = byId.get();
		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
		}

		return "User/contact_detail";
	}

//	Update contact form

	@RequestMapping("/user/update-contact")
	public String updatedcontact(@RequestParam("cid") int cid, Model model) {

		model.addAttribute("viewcontact", "active");
		Optional<Contact> byId = contactRepository.findById(cid);
		Contact contact = byId.get();

		if (contact.isFavorite()) {
			model.addAttribute("check", true);
		}

		model.addAttribute("contact", contact);

		return "User/update_contact";
	}

//	Updates contact 

	@PostMapping("/user/update-process")
	public String updateprocess(@ModelAttribute("contact") Contact contact, @RequestParam("img") MultipartFile img,
			@RequestParam(value = "favorite", defaultValue = "false") boolean fav, Model model, Principal principal,
			HttpSession session) {

		model.addAttribute("viewcontact", "active");

		try {
 
			session.setAttribute("msg", "Contact updated Successfully ");

			String name = principal.getName();
			User user = userrepo.getUsername(name);
			contact.setFavorite(fav);
			imp.updatecontact(contact, user, img);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "redirect:/user/contact-detail?cid=" + contact.getCid();

	}

//	User profile

	@RequestMapping("/user/profile")
	public String profile(Model model, Principal principal) {
		model.addAttribute("title", "My Profile");
		model.addAttribute("profile", "active");

		String name = principal.getName();
		User user = userrepo.getUsername(name);

		model.addAttribute("user", user);

		return "User/profile";
	}

//	Update profile form

	@RequestMapping("/user/update-profile")
	public String updatedprofile(Model model, Principal principal) {

		model.addAttribute("title", "Update Profile ");
		model.addAttribute("profile", "active");

		String name = principal.getName();
		User user = userrepo.getUsername(name);

		model.addAttribute("user", user);

		return "User/update_profile";
	}

//	Updates contact 

	@PostMapping("/user/profile-process")
	public String profileprocess(@ModelAttribute("user") User user, @RequestParam("img") MultipartFile img, Model model,
			Principal principal,HttpSession session) {

		model.addAttribute("profile", "active");

		try { 
			
			String name = principal.getName();  
			imp.updateprofile(user, img ,name ); 
			
			session.setAttribute("msg", "Profile updated Successfully ");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "redirect:/user/profile";

	}

//	Register Form

	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Register");
		model.addAttribute("register", "active");
		model.addAttribute("user", new User());
		return "register";
	}

//	Login

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login");
		model.addAttribute("login", "active");
		model.addAttribute("user", new User());
		return "login";
	}

//	Register process
	@PostMapping("/process")
	public String submitform(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam("profile") MultipartFile profile, Model model) {

		if (result.hasErrors()) {
			System.out.println(result);
			return "register";
		}

		model.addAttribute("user", user);

		List<User> allUser = userrepo.getAllUser();
		for (User user2 : allUser) {
			if (user2 != null && user2.getEmail().equals(user.getEmail())) {
				model.addAttribute("error", "Email already exists !! ");
				return "register";
			}
		}

		model.addAttribute("msg", "register Successfully ");
		imp.registerUser(user, profile);

		return "register";

	}

}
