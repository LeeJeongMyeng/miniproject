package com.project.sample.common;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCrypt;
@Service
public class AESImp implements AES{

    private static final String key = "aesEncryptionKey"; //16Byte == 128bit
    private static final String initVector = "encryptionIntVec"; //16Byte

    //인코더 생성
    private final Base64.Encoder enc = Base64.getEncoder();
    //디코더 생성
    private final Base64.Decoder dec = Base64.getDecoder();


    //암호화이전 field작업
    public Object MakeFeild(){



        return 0;
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

    //개인정보 가리기
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
    public String ProtectPhoneNumber(String pnum) {
        //문자 배열로 전환
        char[] Valuechars = pnum.toCharArray();
        for (int i = 5; i < Valuechars.length; i++){
                Valuechars[i] = '*';
        }
        //처음엔 String result ="";으로 했는데 값할당이 안됬음.
        return new String(Valuechars);
    }
    public String Protectaddress(String address) {
       String[] newAddress = address.split(" ");

        return newAddress[0]+" "+newAddress[1];
    }
}
