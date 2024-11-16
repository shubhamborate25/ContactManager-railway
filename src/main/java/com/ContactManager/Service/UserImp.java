package com.ContactManager.Service;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ContactManager.Entity.Contact;
import com.ContactManager.Entity.User;
import com.ContactManager.repository.ContactRepository;
import com.ContactManager.repository.UserRepository;

@Service
public class UserImp implements UserService {

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public UserRepository userrepo;
	@Autowired
	public ContactRepository contactrepo;
	@Autowired
	private FileService fileService;

	@Value("${aws.s3.bucket.profile}")
	private String ContactBucket;

	@Value("${aws.s3.bucket.contact}")
	private String ProfileBucket;

//	Register User 

	@Override
	public User registerUser(User user, MultipartFile img) {

		try {
			if (img.isEmpty()) {
				user.setImage("default.png");
			} else {
//				user.setImage(img.getOriginalFilename());
//				File file = new ClassPathResource("static/img").getFile();
//				Path path = Paths.get(file.getAbsolutePath() + File.separator + img.getOriginalFilename());
//				System.out.println(path);
//				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				String imageUrl = getImageUrl(img,0);
				user.setImage( imageUrl);
				fileService.UploadFileS3(img,0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		user.setEnable(true);
		user.setRole("USER");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		User save = userrepo.save(user);

		System.out.println(save);
		return save;

	}

//	Add Contact 

	@Override
	public User addcontact(Contact contact, User user, MultipartFile img) {

		try {
			if (img.isEmpty()) {
				contact.setImgurl("default.png ");

			} else {

				

//				File file = new ClassPathResource("static/img").getFile();
//				Path path = Paths.get(file.getAbsolutePath() + File.separator + img.getOriginalFilename());
//				System.out.println(path);
//				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				String imageUrl = getImageUrl(img, 1);
				contact.setImgurl( imageUrl);
				fileService.UploadFileS3(img,1);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		contact.setUser(user);
		user.getContact().add(contact);
		User save = userrepo.save(user);
		System.out.println(contact);

		return save;
	}

//	Update Contact

	@Override
	public Contact updatecontact(Contact contact, User user, MultipartFile img) {
		// TODO Auto-generated method stub

		Contact save = null;

		try {

			Contact oldcontact = contactrepo.findById(contact.getCid()).get();

			if (!img.isEmpty()) {

//				File deletefile = new ClassPathResource("static/img").getFile();
//
//				File file2 = new File(deletefile, oldcontact.getImgurl());
//				file2.delete();
//
//				File file = new ClassPathResource("static/img").getFile();
//				Path path = Paths.get(file.getAbsolutePath() + File.separator + img.getOriginalFilename());
//				System.out.println(path);
//				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//				contact.setImgurl(img.getOriginalFilename());
				
				
				String imageUrl = getImageUrl(img, 1);
				contact.setImgurl( imageUrl);
				fileService.UploadFileS3(img,1);
				

			}

			else {
				contact.setImgurl(oldcontact.getImgurl());
			}

			contact.setUser(user);
			contact.setCid(oldcontact.getCid());
			save = this.contactrepo.saveAndFlush(contact);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return save;
	}

//	Update Profile 

	@Override
	public User updateprofile(User user, MultipartFile img, String name) {
		// TODO Auto-generated method stub

		User save = null;

		try {

			User olduser = userrepo.getUsername(name);

			if (!img.isEmpty()) { 
				
//				File deletefile = new ClassPathResource("static/img").getFile();
//
//				File file2 = new File(deletefile, olduser.getImage());
//				file2.delete();
//
//				File file = new ClassPathResource("static/img").getFile();
//				Path path = Paths.get(file.getAbsolutePath() + File.separator + img.getOriginalFilename());
//				System.out.println(path);
//
//				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); 
//				user.setImage(img.getOriginalFilename());
				 

				String imageUrl = getImageUrl(img,0);
				user.setImage( imageUrl);
				fileService.UploadFileS3(img,0);

			}

			else {
				user.setImage(olduser.getImage());
			}

			user.setId(olduser.getId());
			user.setEnable(true);
			user.setRole("USER");
			user.setPassword(olduser.getPassword());
			user.setContact(olduser.getContact());
			save = userrepo.saveAndFlush(user);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return save;
	}

	
	public String getImageUrl(MultipartFile file, Integer bucketType) {

		String bucketName = null;

		if (bucketType == 0) {
			bucketName = ProfileBucket;
		} else   {
			bucketName =  ContactBucket;
		} 
//			https://contact-manager-contact.s3.eu-north-1.amazonaws.com/download.png

		String url = "https://" + bucketName + ".s3.eu-north-1.amazonaws.com/" + file.getOriginalFilename();
		return url;

	}

	 
}
