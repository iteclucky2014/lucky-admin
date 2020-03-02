package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.UserMapper;
import com.lucky.admin.platform.util.QRCodeUtil;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public User getUserByUsername(String username) throws Exception {
		User user = userMapper.getUserByUsername(username);
		if (user != null && (user.getPickcode() == null || "".equals(user.getPickcode()))) {
			//生成二维码
			BufferedImage image = QRCodeUtil.encode(user.getUsername(), null, false);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
			ImageIO.write(image, "JPG", baos);//写入流中
			byte[] bytes = baos.toByteArray();//转换成字节
			BASE64Encoder encoder = new BASE64Encoder();
			String jpg_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
			jpg_base64 = jpg_base64.replaceAll("\r", "").replaceAll("\n", "");//删除 \r\n
			user.setPickcode("data:image/jpeg;base64," + jpg_base64);
		}
		return user;
	}
	
	public List<User> getUserByCondition(User user) {
		return userMapper.getUserByCondition(user);
	}
	
	public Long getUserCountByCondition(User user) {
		return userMapper.getUserCountByCondition(user);
	}
	
	@Transactional
	public int createUser(User user) {
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword().trim()));
		return userMapper.createUser(user);
	}

	@Transactional
	public int modifyUser(User user) {
		return userMapper.modifyUser(user);
	}

	@Transactional
	public int batchDel(List<User> list) {
		return userMapper.batchDel(list);
	}

	public List<User> getRoles(User user) {
		return userMapper.getRoles(user);
	}

	public Long getRolesCount(User user) {
		return userMapper.getRolesCount(user);
	}

	@Transactional
	public int disRole(User user) {
		return userMapper.disRole(user);
	}
}
