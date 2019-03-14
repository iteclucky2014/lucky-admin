package com.lucky.admin.platform.common;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageManager {
	byte[] getFile(String fileName) throws Exception;
	String save(String fileName, byte[] fileData) throws Exception;
	String save(String fileName, MultipartFile file) throws Exception;

}
