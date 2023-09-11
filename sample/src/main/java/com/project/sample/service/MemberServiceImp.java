package com.project.sample.service;

import com.project.sample.common.AESImp;
import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


@Service
public class MemberServiceImp implements MemberService {

    private final MemberDao dao;
    private final AESImp aes;
    @Autowired
    public MemberServiceImp(MemberDao dao,AESImp aes) {
        this.dao = dao;
        this.aes = aes;
    }



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
//

        // Member 클래스 모든 필드를 가져 옴
        Field[] fields = member.getClass().getDeclaredFields();

        // 각 필드를 순회합니다.
        for (Field field : fields) {
            //private혹은 projected 필드에 접근할 수 있도록 허용
            field.setAccessible(true);
            String fieldName = field.getName();
            // 해당 필드가 String 타입이거나 필드명이 email이 아닐경우
            if (field.getType().equals(String.class) && !fieldName.equals("email")) {
                try {
                    // 필드명을 가져옴  맨앞글자 대문자+맨앞글자제외문자 => Name,Eamil
                    String methodName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    //System.out.println(methodName);

                    // 현재 필드의 get+methodName으로 getter호출
                    Method getter = member.getClass().getMethod("get" + methodName);
                    // 현재 필드의 setter 호출
                    Method setter = member.getClass().getMethod("set" + methodName, String.class);

                    // getter로 호출하여 현재 문자열 필드의 원래 값을 얻습니다.
                    String originalValue = (String) getter.invoke(member);

                    String newValue;

                    // 현재 문자열이 password라면 executeMethodA 실행, 그렇지 않다면 executeMethodB 실행
                    if ("password".equals(fieldName)) {
                        newValue =  aes.hashBcrypt(originalValue);
                        System.out.println("Bcrypt : "+methodName+" : " + newValue);
                    } else {
                        newValue = aes.encrypt(originalValue);
                        System.out.println("AES Encrypt : "+methodName+" : " + newValue);
                    }

                    // setter 메소들르 호출하여 현재 문자열필트에 새로운 값을 설정합니다.
                    setter.invoke(member, newValue);

                } catch (NoSuchMethodException e) {
                    // 해당하는 메서도가 없는 경우 처리
                    System.out.println("해당하는 메서도가 없음: " + e.getMessage());
                } catch (IllegalAccessException e) {
                    // 충분한 접근 권한이 없는 경우 처리
                    System.out.println("접근 권한 부족: " + e.getMessage());
                } catch (InvocationTargetException e) {
                    // 대상 객체 호출 중 예외 발생 시 처리
                    System.out.println("대상 객체 호출 예외: " + e.getMessage());
                }
            }
        }

        for (Field field : member.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName()+":"+field.get(member));
        }

        //DB에 저장
        return dao.Ins_Ctg_Member(member);
        //return 0;
    }

    @Override
    public Member SignIn_Ctg_Member(Member member) {

        System.out.println("LOGIN EMAIL : "+member.getEmail());
        System.out.println("LOGIN PWD : "+member.getPassword());
        //이메일 기준으로 회원정보 뽑아옴
        Member memberInfo = dao.SignIn_Ctg_Member(member);

        //회원 정보가 존재할경우
        if(memberInfo.getUserno() != null){
            //평문 pwd와 암호화 pwd비교
            String Origin_pwd = member.getPassword();
            String Hash_pwd = memberInfo.getPassword();
            boolean pwdCheck = aes.checkBcrypt(Origin_pwd, Hash_pwd);

            //일치한다면 이름을 암호화 해제하고 재할당
            if(pwdCheck){
                memberInfo.setName(aes.decrypt(memberInfo.getName()));
                memberInfo.setPassword(null);
            }else{
                memberInfo = null;
            }
        }

        return memberInfo;
    }

    @Override
    public Member SignIn_Ctg_Email(String email) {

        return dao.SignIn_Ctg_Email(email);
    }
    //email만받을 경우 조회



}
