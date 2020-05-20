package com.codenation.criptografia.utils;

import java.io.File;
import java.io.FileWriter;

import com.codenation.criptografia.model.ChallengeResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IOUtil {
	
	private static final String FILE_URI = "./src/main/resources/answer.json";
	
	private IOUtil() {}
	
	public static void writeFile(ChallengeResponseDTO challengeResponseDTO) {
		try (FileWriter fileWriter = new FileWriter(FILE_URI)) {
			
			fileWriter.write(getJsonAsString(challengeResponseDTO));
			
		} catch (JsonProcessingException e) {
			log.error("Error while converting Object to JSON", e);
		} catch (Exception e) {
			log.error("Error while writing file", e);
		}
	}
	
	public static File getFile() {
		return new File(FILE_URI);
	}
	
	private static String getJsonAsString(ChallengeResponseDTO challengeResponseDTO) throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		return obj.writeValueAsString(challengeResponseDTO); 
	}



}
