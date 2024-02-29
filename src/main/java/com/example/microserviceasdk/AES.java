package com.example.microserviceasdk;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	private String secretKey = "defaultSecretKey";
	private String salt = "defaultSalt";
	
	public AES() {
	}
	
	public AES(String secretKeyStr, String saltStr) {
		secretKey = secretKeyStr;
		salt = saltStr;
	}
	 
	public String encrypt(String strToEncrypt) 
	{
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Error while encrypting: " + e.toString());
	    }
	    return null;
	}
	
	public String decrypt(String strToDecrypt) {
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	    } 
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public static void main(String[] args) 
	{
	    AES aes = new AES();
	    
	    if (args.length == 3) {
            String plainText = args[0];
            String secretKey = args[1];
            String salt = args[2];
            aes = new AES(secretKey,salt);
            String encryptedString = aes.encrypt(plainText);
            System.out.println("=> ENCRYPTED PASSWORD : " + encryptedString);
        } else {
           // System.out.println("USAGE: java -cp SecureTomcatPass.jar com.truven.jsurs.bean.AES SecureTomcatStrongPass defaultSecretKey defaultSalt");
            //java -cp SecureTomcatPass.jar com.truven.jsurs.bean.AES pwd SecretKey Salt
            String originalString = "SecureTomcatStrongPass";
    	    String encryptedString = aes.encrypt(originalString) ;
    	    String decryptedString = aes.decrypt(encryptedString) ;
    	    System.out.println(aes.secretKey);
    	    System.out.println(aes.salt);
    	    System.out.println(originalString);
    	    System.out.println(encryptedString);
    	    System.out.println(decryptedString);
        }
	    
	}

}
