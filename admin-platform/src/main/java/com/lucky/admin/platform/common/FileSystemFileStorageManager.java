package com.lucky.admin.platform.common;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;


@Component
public class FileSystemFileStorageManager implements FileStorageManager {

	@Value("${file.store.path}")
	private String fileStorePath;

	@PostConstruct
	public void init() {
		File dir = new File(fileStorePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@Override
	public byte[] getFile(String fileName) throws Exception {

		String picPath = FilenameUtils.concat(fileStorePath, fileName);
		InputStream is = new FileInputStream(picPath);
		byte[] picData = StreamUtils.copyToByteArray(is);
		is.close();
		return picData;
	}

	@Override
	public String save(String fileName, byte[] fileData) throws Exception {
		String picPath = FilenameUtils.concat(fileStorePath, fileName);
		OutputStream os = new FileOutputStream(picPath);
		StreamUtils.copy(fileData, os);
		os.close();
		return fileName;
	}

	@Override
	public String save(String fileName, MultipartFile file) throws Exception {
		String picPath = FilenameUtils.concat(fileStorePath, fileName);
		file.transferTo(new File(picPath));
		return fileName;
	}
}
