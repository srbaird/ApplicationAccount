package com.bac.applicationaccount;

public interface PasswordAuthenticator {
	
	boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt);
	
	boolean authenticate(char[] attemptedPassword, byte[] encryptedPassword, byte[] salt);
	
	byte[] getEncryptedPassword(String password, byte[] salt);
	
	byte[] getEncryptedPassword(char[] password, byte[] salt);
	
	byte[] generateSalt();

}