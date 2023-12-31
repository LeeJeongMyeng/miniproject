package com.project.sample.service;

import com.project.sample.common.AESImp;
import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.FleamarketDto;
import com.project.sample.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Service
public class MemberServiceImp implements MemberService {

    private final MemberDao dao;
    private final AESImp aes; //암호화
    @Autowired
    public MemberServiceImp(MemberDao dao,AESImp aes) {
        this.dao = dao;
        this.aes = aes;
    }

    //이메일 중복검사
    @Override
    public int Check_SignUp_email(String email) {
        return dao.Check_SignUp_email(email);
    }

    //사업자번호 중복검사
    @Override
    public int BN_Check(Member member) {
        member.setBusiness_number(aes.encrypt(member.getBusiness_number()));
        return dao.BN_Check(member);
    }



    //회원가입
    @Override
    public int Ins_Ctg_Member(Member member) throws IllegalAccessException {
        //사업자도 추가해야함..

        member.set_business(member.getBusiness_number() != null && !member.getBusiness_number().equals(""));
        System.out.println(member.is_business());
        // Member 클래스 모든 필드를 가져 옴
        Field[] fields = member.getClass().getDeclaredFields();

        // 각 필드를 순회합니다.
        for (Field field : fields) {
            //private혹은 projected 필드에 접근할 수 있도록 허용
            field.setAccessible(true);
            String fieldName = field.getName();
            // 해당 필드가 String 타입일경우
            if (field.getType().equals(String.class)) {
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
                    System.out.println(methodName);
                    System.out.println("origin_value: "+originalValue);
                    //값이 NULL이 아닐경우
                    boolean NullCheck = originalValue != null && !originalValue.equals("");

                    // 현재 문자열이 password라면 executeMethodA 실행, 그렇지 않다면 executeMethodB 실행
                    if(NullCheck){

                        String newValue;

                        if ("password".equals( fieldName )) {
                            newValue =  aes.hashBcrypt(originalValue);
                            System.out.println("Bcrypt : "+methodName+" : " + newValue);
                        } else {
                            newValue = aes.encrypt(originalValue);
                            System.out.println("AES Encrypt : "+methodName+" : " + newValue);
                        }
                        setter.invoke(member, newValue);

                    }
                    // setter 메소들르 호출하여 현재 문자열필트에 새로운 값을 설정합니다.


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
        //DB에 저장
        return dao.Ins_Ctg_Member(member);
    }

    //로그인
    @Override
    public String SignIn_Ctg_Member(Member member) {

        //암호화된 이메일과 비교해야하기 때문에 들어온 이메일 암호화 처리
        //이후 DB들어가기전 세팅
        member.setEmail(aes.encrypt(member.getEmail()));
        member.setMethod("Login");
        //데이터 불러오기
        Member memberInfo = dao.SignIn_Ctg_Member(member);
        String user_id;

        //회원 정보가 존재할경우
        if(memberInfo != null && memberInfo.getUser_id() != null){
            //평문 pwd와 암호화 pwd비교
            String Origin_pwd = member.getPassword();
            String Hash_pwd = memberInfo.getPassword();
            boolean pwdCheck = aes.checkBcrypt(Origin_pwd, Hash_pwd);

            //일치한다면 이름/이메일 복호화하고 재할당
            if(pwdCheck){
                //memberInfo.setName(aes.decrypt(memberInfo.getName()));
                //memberInfo.setEmail(aes.decrypt(memberInfo.getEmail()));
                memberInfo.setEmail(null);
                memberInfo.setPassword(null);
                user_id = memberInfo.getUser_id();
            }else{
               // memberInfo = null;
                user_id = null;
            }
        }else{
            //memberInfo = null;
            user_id = null;
        }
        return user_id;
    }

    //내정보 들고오기
    @Override
    public Member get_My_Info(Member member)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        //회원정보들오고는 mapper에서 if문용
        member.setMethod("MyInfo");
        //1.회원 정보 들고오기
        Member memberInfo = dao.SignIn_Ctg_Member(member);
        //비밀번호는 필요없으니까 null
        memberInfo.setPassword(null);

            //복호화 처리
        memberInfo = (Member) aes.get_Origin_Info(memberInfo);

        //데이터 별처리
        memberInfo.setName(aes.ProtectName(memberInfo.getName()));
        memberInfo.setEmail(aes.ProtectEmail(memberInfo.getEmail()));
        memberInfo.setPhone_number(aes.ProtectPhoneNumber(memberInfo.getPhone_number()));
        memberInfo.setAddress(aes.Protectaddress(memberInfo.getAddress()));


        return memberInfo;
//        return null;
    }

    //수정 전 기존 값과 동일한지 체크
    @Override
    public boolean Check_Password(Member member) {

        boolean result = false;
        try {

            String Encvalue = dao.Check_Password(member);
            String[] properties = {"Password","Phone_number","Business_number"};
            for (String property : properties) {
                Method getter = Member.class.getMethod("get" + property);
                String value = (String) getter.invoke(member);
                if (value != null) {

                    if(property.equals("Password")){
                        result = aes.checkBcrypt(value,Encvalue);
                    }else{
                        result = value.equals(aes.decrypt(Encvalue));
                    }
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
    //회원정보 수정
    @Override
    public int MyInfo_Update(Member member) {
        try {
            System.out.println("수정 is_active"+member.is_active());

            String[] properties = {"Password", "Address","Extra_address","Detail_address", "Phone_number", "Business_number"};
            for (String property : properties) {
                Method getter = Member.class.getMethod("get" + property);
                String value = (String) getter.invoke(member);

                if (value != null) {

                    if(property.equals("Password")){
                        value = aes.hashBcrypt(value);
                    }else{
                        value = aes.encrypt(value);
                    }
                    Method setter = Member.class.getMethod("set" + property, String.class);
                    setter.invoke(member, value);

                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("비번 업데이트 잘처리됨");
        return dao.MyInfo_Update(member);
    }
    //회원탈퇴
    @Override
    public int delete_Member(Member member) {
        return dao.delete_Member(member);
    }


}
