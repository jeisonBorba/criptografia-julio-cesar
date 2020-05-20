package com.codenation.criptografia;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codenation.criptografia.client.CodenationClient;
import com.codenation.criptografia.model.ChallengeResponseDTO;
import com.codenation.criptografia.model.ResultChallengeResponseDTO;
import com.codenation.criptografia.utils.CryptographyUtil;
import com.codenation.criptografia.utils.IOUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CriptografiaApplication {
	
	@Autowired
	private CodenationClient codenationClient;

	public static void main(String[] args) {
		SpringApplication.run(CriptografiaApplication.class, args);
	}

	@PostConstruct
	public void run() throws Exception {
		log.debug("Requesting challeng from codenation");
		ChallengeResponseDTO challengeResponse = this.codenationClient.getChallenge();
		
		IOUtil.writeFile(challengeResponse);
		log.debug("Challenge: ", challengeResponse);
		
		String decryptedText = CryptographyUtil.decrypt(challengeResponse.getDecimal(), challengeResponse.getCryptedText());
		challengeResponse.setDecryptedText(decryptedText);
		challengeResponse.setCryptographicSummary(CryptographyUtil.convertToSHA1(decryptedText));
		
		IOUtil.writeFile(challengeResponse);
		log.debug("Challenge decrypted: ", challengeResponse);
		
		log.debug("Sending challenge to codenation");
		ResultChallengeResponseDTO resultResponse = this.codenationClient.submitChallenge(IOUtil.getFile());
		log.debug("Challenge result: ", resultResponse);
		
	}

}
