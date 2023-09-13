package com.project.sample.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class FileService {
    //String path = "E:\\workspace\\git\\vue-app\\src\\assets\\img\\fleamarket\\";
      String path = "C:\\Users\\TA9\\git\\vue-app\\src\\assets\\img\\fleamarket";

    //고유ID Version
    public String Insprofileimg(MultipartFile file) {
        //집
        //노트북

        //고유 ID생성하는 내장 함수 ==> 중복파일 방지
        String uuid = UUID.randomUUID().toString();
        // 고유 ID+파일이름으로 심어줌
        String fname = uuid+"_"+file.getOriginalFilename().replace(" ","");;
        System.out.println(path);

        File Folder = new File(path);

        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다. ("새폴더"만 생성)
                System.out.println("폴더 생성완료.");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("폴더가 이미 존재합니다..");
        }
        File f = new File(path+"\\"+fname);

        try {
        file.transferTo(f); // 필수예외 처리 (IO발생)
        } catch (IllegalStateException e) {
            System.out.println("파일업로드 예외1:"+e.getMessage());
        } catch (IOException e) {
            System.out.println("파일업로드 예외2:"+e.getMessage());
        }

        return fname;
    }

    //파일 삭제처리
    public void DeleteFile(String filename) {

        String filePath= path+filename;
        System.out.println(filePath);
        File f = new File(filePath);
        if(f.exists()) {f.delete();}
    }



}
