package com.project.sample.common;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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

    //AES양방향 암호화
    public String encrypt(String value) {
        try {
            // 초기화백터(IV) byte로 변경
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            // KEY도 byte로 변경
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            //cipher를 만들
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); //AES, CBC모드, partial block 채우기
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
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
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
}
