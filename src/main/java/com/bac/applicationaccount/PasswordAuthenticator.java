package com.bac.applicationaccount;

/**
 * A class to provide encryption comparison methods where a pasword salt is also
 * required.
 * 
 * @author Simon Baird
 *
 */
public interface PasswordAuthenticator {

	/**
	 * Encapsulation of a method to compare a password attempt against a
	 * supplied encrypted instance together with a password salt instance.
	 * 
	 * @param attemptedPassword
	 *            the password attempt to be encrypted before comparison with
	 *            the supplied value.
	 * @param encryptedPassword
	 *            the encrypted password for comparison purposes
	 * @param salt
	 *            the password salt to be used in the encryption process
	 * @return whether the encrypted value of the password attempt matches the
	 *         supplied encrypted value
	 */
	boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt);

	/**
	 * Encapsulation of a method to compare a password attempt against a
	 * supplied encrypted instance together with a password salt instance.
	 * 
	 * @param attemptedPassword
	 *            the password attempt to be encrypted before comparison with
	 *            the supplied value.
	 * @param encryptedPassword
	 *            the encrypted password for comparison purposes
	 * @param salt
	 *            the password salt to be used in the encryption process
	 * @return whether the encrypted value of the password attempt matches the
	 *         supplied encrypted value
	 */
	boolean authenticate(char[] attemptedPassword, byte[] encryptedPassword, byte[] salt);

	/**
	 * Return an encrypted value of the supplied password.
	 * 
	 * @param password
	 *            the plaintext password to be encrypted
	 * @param salt
	 *            the password salt to be used in the encryption process
	 * @return an array representing the encrypted plaintext password
	 */
	byte[] getEncryptedPassword(String password, byte[] salt);

	/**
	 * Return an encrypted value of the supplied password.
	 * 
	 * @param password
	 *            the plaintext password to be encrypted
	 * @param salt
	 *            the password salt to be used in the encryption process
	 * @return an array representing the encrypted plaintext password
	 */
	byte[] getEncryptedPassword(char[] password, byte[] salt);

	/**
	 * A convenience to create an appropriate random salt value
	 * 
	 * @return a password salt to be used in the encryption process
	 */
	byte[] generateSalt();

}