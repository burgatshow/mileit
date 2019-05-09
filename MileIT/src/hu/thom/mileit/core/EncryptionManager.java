package hu.thom.mileit.core;

import java.io.Serializable;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.login.LoginException;

import com.ibm.websphere.security.auth.data.AuthData;
import com.ibm.websphere.security.auth.data.AuthDataProvider;

import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * Class to implement symmetric encryption mechanisms
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class EncryptionManager implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 2965476526353529592L;

	/**
	 * Fields for secret key and salt
	 */
	private String SECRET_KEY = null;
	private String SALT = null;

	/**
	 * Ciphers for encryption and decryption
	 */
	private IvParameterSpec initVectorParameterSpec = null;
	private Cipher encrypter = null;
	private Cipher decrypter = null;

	/**
	 * Logger instance
	 */
	private LogManager logger = new LogManager(EncryptionManager.class);

	/**
	 * Instance
	 */
	private static EncryptionManager em;

	/**
	 * Constructor
	 * 
	 * @return {@link DynaCacheManager}
	 */
	public static EncryptionManager getInstance() {
		if (em == null) {
			em = new EncryptionManager();
		}
		return em;
	}

	/**
	 * Constructor
	 */
	private EncryptionManager() {
		logger.logEnter("EncryptManager()");

		setupCiphers();

		logger.logExit("EncryptManager()");
	}

	private void setupCiphers() {
		logger.logEnter("setupCiphers()");
		if (SECRET_KEY == null) {
			logger.logDebug("setupCiphers()", LogMessages.LOG_SEC_F_SECRET);
			SECRET_KEY = getAuthenticationAlias("password_j2c_passkey");
			logger.logTrace("setupCiphers()", null, SECRET_KEY);
		}

		if (SALT == null) {
			logger.logDebug("setupCiphers()", LogMessages.LOG_SEC_F_SALT);
			SALT = getAuthenticationAlias("password_j2c_salt");
			logger.logTrace("setupCiphers()", null, SALT);
		}

		if (encrypter == null || decrypter == null) {
			try {
				initVectorParameterSpec = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

				SecretKey secretKey = secretKeyFactory.generateSecret(new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256));
				SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

				encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
				encrypter.init(Cipher.ENCRYPT_MODE, secretKeySpec, initVectorParameterSpec);

				decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
				decrypter.init(Cipher.DECRYPT_MODE, secretKeySpec, initVectorParameterSpec);

			} catch (Exception e) {
				logger.logException("EncryptManager()", e);
			}
		}
		logger.logExit("setupCiphers()");
	}

	/**
	 * Reads the secret key and salt set in runtime as a J2C authentication alias
	 * 
	 * @param authAliasName {@link String} the name of the authentication alias
	 * @return {@link String} the proper key identified by its name or null
	 *         otherwise
	 */
	private String getAuthenticationAlias(String authAliasName) {
		logger.logEnter("getAuthenticationAlias()");

		String key = null;
		if (authAliasName != null && !authAliasName.isEmpty()) {
			try {
				logger.logDebug("getAuthenticationAlias()", LogMessages.LOG_SEC_FFF_J2C, authAliasName);

				AuthData ad = AuthDataProvider.getAuthData(authAliasName);
				key = new String(ad.getPassword());

			} catch (LoginException e) {
				logger.logException("getAuthenticationAlias()", e);
			}
		}

		logger.logExit("getAuthenticationAlias()");
		return key;
	}

	/**
	 * Encrypts the received string
	 * 
	 * @param strToEncrypt {@link String} to be encrypted
	 * @return the encrypted {@link String} in {@link Base64} format, null if failed
	 *         or empty parameter received
	 */
	public String encrypt(String strToEncrypt) {
		logger.logEnter("encrypt()");

		String encryptedValue = null;
		if (strToEncrypt != null && !strToEncrypt.isEmpty()) {
			try {
				encryptedValue = Base64.getEncoder().encodeToString(encrypter.doFinal(strToEncrypt.getBytes("UTF-8")));
			} catch (Exception e) {
				logger.logException("encrypt()", e);
			}
		}

		logger.logExit("encrypt()");
		return encryptedValue;
	}

	/**
	 * Decrypts the received encrypted string
	 * 
	 * @param strToDecrypt {@link String} to be decrypted
	 * @return the decrypted {@link String}, null if failed or empty parameter
	 *         received
	 */
	public String decrypt(String strToDecrypt) {
		logger.logEnter("decrypt()");

		String decryptedValue = null;
		if (strToDecrypt != null && !strToDecrypt.isEmpty()) {
			try {
				decryptedValue = new String(decrypter.doFinal(Base64.getDecoder().decode(strToDecrypt)));
			} catch (Exception e) {
				logger.logException("decrypt()", e);
			}
		}
		logger.logExit("decrypt()");
		return decryptedValue;
	}
}
