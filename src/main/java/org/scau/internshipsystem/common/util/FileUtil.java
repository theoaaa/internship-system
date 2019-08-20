package org.scau.internshipsystem.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @program: internship-system
 * @description: 文件工具类
 * @author: guest
 * @create: 2019-08-19 15:41
 **/
@Slf4j
public class FileUtil {
    public static boolean setFileResponseHeader(HttpServletResponse response,String fileName){
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeFile(MultipartFile file,String fileName){
        File destFile = new File(fileName);
        //检查目标是否存在
        if(file == null){
            return false;
        }
        //检查父目录是否存在
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        //保存
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            log.info("文件保存成功...");
            e.printStackTrace();
        }
        return true;
    }

    public static boolean readFile(OutputStream os, String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            log.info("读取的文件不存在...");
            return false;
        }
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(fileName);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer,0,i);
                i = bis.read(buffer);
            }
            log.info("文件读取完成...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
