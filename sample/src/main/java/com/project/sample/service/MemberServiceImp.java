package com.project.sample.service;

import com.project.sample.common.AESImp;
import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;


@Service
public class MemberServiceImp implements MemberService {

    private final MemberDao dao;
    private final AESImp aes;
    @Autowired
    public MemberServiceImp(MemberDao dao,AESImp aes) {
        this.dao = dao;
        this.aes = aes;
    }




    @Value("${ctg.Encrypt.Solt}")
    private String CES;


    //이메일 중복검사
    @Override
    public int Check_SignUp_name(Member member) {
        return dao.Check_SignUp_name(member);
    }

    //이름/주민 중복검사
    @Override
    public int Check_SignUp_email(String email) {
        return dao.Check_SignUp_email(email);
    }

    //회원가입
    @Override
    public int Ins_Ctg_Member(Member member) throws IllegalAccessException {
        //사업자도 추가해야함..


        for(Field field : member.getClass().getDeclaredFields()){
            field.setAccessible(true);
            //각 field 타입확인
            String fieldType = String.valueOf(field.getType());
            // field이름
            String fieldName = field.getName();
            // 값
            String fieldValue= (String)field.get(member);
           
            
            if(fieldType.equals("class java.lang.String")){
                if(fieldName.equals("password")){
                    //return 값
                    aes.hashBcrypt(fieldValue);
                }else{
                    aes.encrypt(fieldValue);
                }
            }
        }

        return dao.Ins_Ctg_Member(member);
    }


}
