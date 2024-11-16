package com.ContactManager.Service;
 
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	public AmazonS3 amazonS3;

	@Value("${aws.s3.bucket.profile}")
	private String ContactBucket;

	@Value("${aws.s3.bucket.contact}")
	private String ProfileBucket;

	@Override
	public boolean UploadFileS3(MultipartFile file, Integer bucketType)   {
		// TODO Auto-generated method stub
		String bucketName = null;

		try {

			if (bucketType==0) {
				bucketName = ProfileBucket;
			} else  {
				bucketName = ContactBucket;
			}

			String originalFilename = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(file.getContentType());
			objectMetadata.setContentLength(file.getSize());

			PutObjectRequest PutObjectRequest = new PutObjectRequest(bucketName, originalFilename, inputStream, objectMetadata);

			PutObjectResult savedata = amazonS3.putObject(PutObjectRequest);
			
			if (!ObjectUtils.isEmpty(savedata)) {
				
				return true;
				
			}
		 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
