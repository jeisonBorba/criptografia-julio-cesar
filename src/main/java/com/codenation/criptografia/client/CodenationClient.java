package com.codenation.criptografia.client;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.codenation.criptografia.config.CodenationProperty;
import com.codenation.criptografia.model.ChallengeResponseDTO;
import com.codenation.criptografia.model.ResultChallengeResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConditionalOnBean(value = CodenationProperty.class)
public class CodenationClient extends RestTemplate {
	
	private CodenationProperty codenationProperty;
	
	@Autowired
	public CodenationClient(CodenationProperty codenationProperty) {
		this.codenationProperty = codenationProperty;
	}

	public ChallengeResponseDTO getChallenge() throws Exception {
        UriComponents uriBuilder = UriComponentsBuilder
        		.fromUriString(this.getRequestChallengUrl())
                .queryParam("token", this.codenationProperty.getUserToken())
                .build();

        try {
        	
	        ResponseEntity<ChallengeResponseDTO> response = getForEntity(uriBuilder.toUriString(), ChallengeResponseDTO.class);
	        return response.getBody();
	        
        } catch (Exception e) {
        	String message = "Error to get challenge from codenation.";
        	log.error(message.concat(" URL - [{}]"), uriBuilder.toUriString(), e);
        	
        	throw new Exception(message);
        }
	}

	public ResultChallengeResponseDTO submitChallenge(File file) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		FileSystemResource content = new FileSystemResource(file);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("answer", content);
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        UriComponents uriBuilder = UriComponentsBuilder
        		.fromUriString(this.getSendChallengUrl())
                .queryParam("token", this.codenationProperty.getUserToken())
                .build();
        
        try {
        	
        	ResponseEntity<ResultChallengeResponseDTO> response = postForEntity(uriBuilder.toString(), requestEntity, ResultChallengeResponseDTO.class);
        	return response.getBody();
        	
        } catch (Exception e) {
        	String message = "Error to submit challenge to codenation.";
        	log.error(message.concat(" URL - [{}]"), uriBuilder.toUriString(), e);
        	
        	throw new Exception(message);
        }
		
	}
	
	private String getRequestChallengUrl() {
		String codenationUrl = this.codenationProperty.getUrl().toString();
		return codenationUrl.concat(this.codenationProperty.getUrlGetChallenge().toString());
	}

	private String getSendChallengUrl() {
		String codenationUrl = this.codenationProperty.getUrl().toString();
		return codenationUrl.concat(this.codenationProperty.getUrlPostChallenge().toString());
	}
}
