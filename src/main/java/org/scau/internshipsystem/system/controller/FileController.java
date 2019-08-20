package org.scau.internshipsystem.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.common.util.Captcha;
import org.scau.internshipsystem.common.util.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @program: internship-system
 * @description: 文件上传，下载
 * @author: guest
 * @create: 2019-08-19 15:11
 **/
@RequestMapping("file")
@RestController
@Slf4j
public class FileController {
    private  static final String INTERNSHIP_FILE_DIR = "D:/work/internshipFile/";
    private static final String OFFER_FILE_DIR = "D:/work/offerFile/";

    /**
     * 验证码
     * @param request
     * @return
     */
    @GetMapping("/captcha")
    public JsonResult getCaptcha(HttpServletRequest request){
        return JsonResult.success(new Captcha().getRandcode(request));
    }

    /**
     * 实习文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload/internship")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file) {
        upload(file,INTERNSHIP_FILE_DIR);
        return JsonResult.success("文件上传成功");
    }

    /**
     * 实习文件下载
     * @param fileName
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/download/internship")
    public JsonResult downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        download(response,INTERNSHIP_FILE_DIR + fileName);
        return null;
    }

    /**
     * offer文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload/offer")
    public JsonResult uploadOffer(@RequestParam("file") MultipartFile file){
        upload(file,OFFER_FILE_DIR);
        return JsonResult.success("文件上传成功");
    }

    /**
     * offer文件下载
     * @param fileName
     * @param response
     * @return
     */
    @GetMapping("/download/offer")
    public JsonResult downloadOffer(@RequestParam("filename") String fileName, HttpServletResponse response){
        download(response,OFFER_FILE_DIR + fileName);
        return null;
    }

    public boolean download(HttpServletResponse response,String fileName){
        String oldName = fileName.substring(fileName.indexOf("_"));
        FileUtil.setFileResponseHeader(response,oldName);
        try {
            FileUtil.readFile(response.getOutputStream(),fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean upload(MultipartFile file,String dirName){
        if(file.isEmpty()){
            return false;
        }
        String realName = dirName + UUID.randomUUID() + file.getOriginalFilename();
        FileUtil.writeFile(file,realName);
        return true;
    }

}
