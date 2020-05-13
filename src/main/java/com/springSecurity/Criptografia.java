package com.springSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**Project: springSecurity
 * File: Criptografia.java
 * @author jaime
 * Em 13-05-2020 **/

public class Criptografia {
	
	public static String cryptPassword(String senha) {
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
		return crypt.encode(senha);
	}
	
	/** @param args */
	public static void main(String[] args) {
		String senha ="123";
		Criptografia.cryptPassword(senha);
		System.out.println("Senha criptografada: "+ cryptPassword(senha));
	

	}

}
