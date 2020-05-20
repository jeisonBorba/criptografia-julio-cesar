package com.codenation.criptografia.config;

import java.net.URI;

import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@ConditionalOnProperty(value = {
		"codenation.url",
		"codenation.urlGetChallenge",
		"codenation.urlPostChallenge",
		"codenation.userToken"
}, matchIfMissing = false)
@Configuration
@Validated
@ConfigurationProperties(prefix = "codenation")
@Getter
@Setter
public class CodenationProperty {
	
	@NotNull(message = "O campo url da configuração do Codenation não pode ser nulo.")
	private URI url;
	
	@NotNull(message = "O campo urlGetChallenge da configuração do Codenation não pode ser nulo.")
	private URI urlGetChallenge;
	
	@NotNull(message = "O campo urlPostChallenge da configuração do Codenation não pode ser nulo.")
	private URI urlPostChallenge;	
	
	@NotNull(message = "O campo userToken da configuração do Codenation não pode ser nulo.")
	private String userToken;
}
