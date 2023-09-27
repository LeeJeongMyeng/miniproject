package com.project.sample.common;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCrypt;
@Service
public class AESImp implements AES{
    @Value("${key}")
    private String key;
    @Value("${initVector}")
    private String initVector;
    //private static final String key = "aesEncryptionKey"; //16Byte == 128bit
    //private static final String initVector = "encryptionIntVec"; //16Byte

    //인코더 생성
    private final Base64.Encoder enc = Base64.getEncoder();
    //디코더 생성
    private final Base64.Decoder dec = Base64.getDecoder();


    //암호화이전 field작업
    public Object get_Origin_Info(Object a) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 필드명을 가져옴  맨앞글자 대문자+맨앞글자제외문자 => Name,Eamil
        Field[] fields = a.getClass().getDeclaredFields();
        for ( Field field : fields ) {
            //private혹은 projected 필드에 접근할 수 있도록 허용
            field.setAccessible(true);
            String fieldName = field.getName();
            //복호화가 필요없는 컬럼이 아닐경우
            if( fieldName.equals("name") || fieldName.equals("address") || fieldName.equals("phone_number") ||fieldName.equals("email")){
                //set/get을 위해 field이름의 앞글자를 대문자로 전환
                String methodName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                // 현재 필드의 get+methodName으로 getter호출
                Method getter = a.getClass().getMethod("get" + methodName);
                // 현재 필드의 setter 호출
                Method setter = a.getClass().getMethod("set" + methodName, String.class);
                // getter로 호출하여 현재 문자열 필드의 암호화값을 얻음
                String EncValue = (String) getter.invoke(a);
                //복호화
                String  Originvalue = decrypt(EncValue);
                //평문세팅
                setter.invoke(a,Originvalue);
            }
        }


        return a;
    }

    //AES양방향 암호화
    public String encrypt(String value) {
        try {
            // 초기화백터(IV) byte로 변경
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            // KEY도 byte로 변경
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            //cipher를 암호화 방식설정
            //AES, CBC모드, partial block 채우기
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // 암복호화 모드+iv와key세팅
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); // mode

            //실제로 암호화 하는 부분
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return enc.encodeToString(encrypted); //암호문을 base64로 인코딩하여 출력 해줌

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //AES양방향 복호화
    public String decrypt(String encrypted) {
        try {
            // 초기화백터(IV) byte로 변경
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            // KEY도 byte로 변경
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            //cipher => 암호화 방식 설정
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // 암복호화 모드+iv와 key할당
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //설정한 cipher를 가지고 암호문을 복호화
            byte[] original = cipher.doFinal(dec.decode(encrypted)); //base64 to byte

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Bcrypt 단방향 암호화
    public String hashBcrypt(String value) {
       return BCrypt.hashpw(value, BCrypt.gensalt());
    }
    //Bcrypt 단방향 암호화 체크
    public boolean checkBcrypt(String value,String Encvalue) {
        return BCrypt.checkpw(value, Encvalue);

    }

    //이름 별처리
    public String ProtectName(String name) {
        //문자 배열로 전환
        char[] Valuechars = name.toCharArray();
        if (name.length() == 2) {
            Valuechars[1] = '*';
        }else{
            for (int i = 1; i < Valuechars.length - 1; i++) {
                Valuechars[i] = '*';
            }
        }
        //처음엔 String result ="";으로 했는데 값할당이 안됬음.
        return new String(Valuechars);
    }
    //이메일 별처리
    public String ProtectEmail(String email) {
        //문자 배열로 전환
        char[] Valuechars = email.toCharArray();
        for (int i = 4; i < Valuechars.length -4; i++){
            if(Valuechars[i] != '@'){
                Valuechars[i] = '*';
            }
        }
        //처음엔 String result ="";으로 했는데 값할당이 안됬음.
        return new String(Valuechars);
    }

    //핸드폰번호 별처리
    public String ProtectPhoneNumber(String pnum) {
        //문자 배열로 전환
        char[] Valuechars = pnum.toCharArray();
        for (int i = 5; i < Valuechars.length; i++){
                Valuechars[i] = '*';
        }
        //처음엔 String result ="";으로 했는데 값할당이 안됬음.
        return new String(Valuechars);
    }

    //주소 구까지 처리
    public String Protectaddress(String address) {
       String[] newAddress = address.split(" ");

        return newAddress[0]+" "+newAddress[1];
    }
}
