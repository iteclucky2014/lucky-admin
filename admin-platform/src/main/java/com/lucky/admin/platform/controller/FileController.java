package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.FileStorageManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(value = "files", tags = "文件上传下载信息管理")
@Controller
@RequestMapping("files")
public class FileController {

    @Autowired
    FileStorageManager fileStorageManager;

    @Value("${file.urlPrefix}")
    private String urlPrefix;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ApiResult upload(@RequestParam(name = "file") MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            fileStorageManager.save(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>();
        result.put("src", urlPrefix + "?fileName=" + fileName);
        result.put("name", file.getOriginalFilename());
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("上传成功！").data(result).build();

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition","attachment;filename=\"" + fileName + "\"");

        byte[] fileData = fileStorageManager.getFile(fileName);

        ResponseEntity<byte[]> picResp = new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        return picResp;
    }
}
