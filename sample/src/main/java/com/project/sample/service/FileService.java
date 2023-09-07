package com.project.sample.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {


    //고유ID Version
    public String Insprofileimg(MultipartFile file) {
        String path = "E:\\workspace\\git\\vue-app\\src\\assets\\img\\fleamarket\\";
        //고유 ID생성하는 내장 함수 ==> 중복파일 방지
        String uuid = UUID.randomUUID().toString();
        // 고유 ID+파일이름으로 심어줌
        String fname = uuid+"_"+file.getOriginalFilename();
        System.out.println(path);

        File Folder = new File(path);

        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다. ("새폴더"만 생성)
                System.out.println("폴더가 생성완료.");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("폴더가 이미 존재합니다..");
        }
        File f = new File(path+fname);

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
        String path = "E:\\workspace\\git\\vue-app\\src\\assets\\img\\fleamarket";
        String filePath= path+filename;
        File f = new File(filePath);
        if(f.exists()) {f.delete();}
    }
}
