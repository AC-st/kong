package com.gec.system.controller;

import com.gec.system.common.Result;
import com.gec.system.util.OssConfig;
import com.gec.system.util.OssTemplate;
import com.gec.system.util.VodTemplate;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/18 18:54
 */
@RestController
@Api(tags = "文件上传")
@RequestMapping("/admin/system/upload")
@CrossOrigin
public class SysUploadController {

	@Autowired
	private OssTemplate ossTemplate;

	@Autowired
	private VodTemplate vodTemplate;

	@RequestMapping("/uploadImage")
	public String uploadImages(MultipartFile uploadImage) throws IOException {
		String imageUrl = this.ossTemplate.upload(uploadImage.getOriginalFilename(), uploadImage.getInputStream());
		System.out.println("========================"+imageUrl);
		return imageUrl;
	}

	@RequestMapping("/uploadVideo")
	public String uploadVideo(MultipartFile uploadVideo) throws IOException {
		System.out.println("===========");
		String uploadVideoId = this.vodTemplate.uploadVideo(uploadVideo.getOriginalFilename(), uploadVideo.getInputStream());
		System.out.println(uploadVideoId);
		return uploadVideoId;
	}

}
