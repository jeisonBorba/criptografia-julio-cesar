package com.codenation.criptografia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChallengeResponseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("numero_casas")
	private int decimal;
	
	@JsonProperty("token")
	private String userToken;
	
	@JsonProperty("cifrado")
	private String cryptedText;
	
	@JsonProperty("decifrado")
	private String decryptedText;
	
	@JsonProperty("resumo_criptografico")
	private String cryptographicSummary;

}
