package cn.sambo.difference.platform.common;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageManager {
	String UNIFIED_IMAGE_URL_PREFIX = "/images/";
	byte[] getPicture(String picName) throws Exception;
	String save(String picName, byte[] picData) throws Exception;
	String saveWithWatermark(String picName, byte[] picData) throws Exception;
	String replace(String picName, byte[] newPicData) throws Exception;
	String replaceWithWatermark(String picName, byte[] newPicData) throws Exception;
	String save(String picName, MultipartFile picData) throws Exception;
	String saveWithWatermark(String picName, MultipartFile picData) throws Exception;
	String replace(String picName, MultipartFile newPicData) throws Exception;
	void delete(String picName) throws Exception;
	String saveBase64(String picName, String picData) throws Exception;
	String saveBase64WithWatermark(String picName, String picData) throws Exception;
	String getPictureBase64(String picName) throws Exception;
}
