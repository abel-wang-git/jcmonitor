package com.zkyf.service;

import com.zkyf.ctrl.Wb;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;


/**
 * Created by Administrator on 2017/11/1.
 */
@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private Croe croe;
    @Resource
    private Wb wb;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Value("${jc.ips}") private  String host;
    @Override
    public void run(String... strings) throws Exception {

          String[] hosts = host.split(",");
        for (String ip: hosts) {
            List list=croe.findHost(decrypt("123",ip));
            if(list.size()==0){
                croe.insertHost(2,decrypt("123",ip));
            }
            Ping ping = new Ping();
            ping.setIp(ip);
            ping.setTsCtrl(wb);
            ping.setJdbcTemplate(jdbcTemplate);
            Thread thread = new Thread(ping);
            thread.start();
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("192.168.1.1","123").toString());
        System.out.println(decrypt("123","/TsI6RHijh4+n9jIlfgSQg=="));

    }

    /**
     * 加密
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {
        try {
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            keygen.init(128, random);
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] byte_encode=content.getBytes("utf-8");
            byte [] byte_AES=cipher.doFinal(byte_encode);
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param encodeRules
     * @param content
     * @return
     */
    public static String decrypt(String encodeRules,String content){
        try {
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
