package com.sk.springcloud.security;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String RESOURCE_ID = "v1";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${keyFile}")
	private String keyFile;
	@Value("${password}")
	private String password;
	@Value("${alias}")
	private String alias;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
				//.tokenStore(tokenStore())
				//.accessTokenConverter(jwtAccessTokenConverter())
				.authenticationManager(authenticationManager);
				//.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("javainuse-client").secret(bCryptPasswordEncoder.encode("javainuse-secret"))
				.authorizedGrantTypes("password", "refresh_token").scopes("read", "write");
		;
	}

	/*@Bean
	public TokenStore tokenStore() {
		//return new JwtTokenStore(jwtAccessTokenConverter());
		return new JdbcTokenStore(dataSource);
	}*/
}
