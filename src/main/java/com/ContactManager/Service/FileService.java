package com.ContactManager.Service;
 

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public boolean UploadFileS3(MultipartFile file,Integer bucketType)  ;
}
