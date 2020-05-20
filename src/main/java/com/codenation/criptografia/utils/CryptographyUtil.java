package com.codenation.criptografia.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptographyUtil {
	
	private CryptographyUtil() {}
	
	public static String decrypt(int key, String cryptedText) {
		if (cryptedText == null || cryptedText.equals("")) {
			throw new IllegalArgumentException("The crypted text must not be null");
		}
		
		String textLower = cryptedText.toLowerCase();
    	StringBuilder decryptedText = new StringBuilder();
		
        for (char character : textLower.toCharArray()){
            if (Character.isLetter(character)){
                char caracterDescripto = (char) (character - key);
                decryptedText.append(caracterDescripto);
                continue;
            } 
            
            decryptedText.append(character);
        }
    	
    	return decryptedText.toString();
	}
	
	public static String convertToSHA1(String value) {
		return DigestUtils.sha1Hex(value);
	}

}
