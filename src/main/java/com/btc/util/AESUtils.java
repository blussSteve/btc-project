package com.btc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtils {
	static{
		Security.addProvider(new BouncyCastleProvider());//添加BouncyCastle的实现， 放static块中
	}
	//下面是一些常量
	/**
	   * IV大小.
	   */
	  private static final int IV_SIZE = 16;

	  /**
	   * BC包中AES算法名.
	   */
	  public static final String ALGORITHM_LONG_NAME = "AES/CBC/PKCS7Padding";

	  /**
	   * BC包中AES算法名.
	   */
	  public static final String ALGORITHM_SHORT_NAME = "AES";

	  /**
	   * BC Provider名称.
	   */
	  public static final String PROVIDER_NAME = "BC";
	  
	  private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

	//获得加密器的函数
	private static Cipher generateCipher(final int mode, final byte[] key,
	      final byte[] ivp) throws NoSuchAlgorithmException,
	      NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
	      InvalidAlgorithmParameterException {
	    Cipher res = null;
	    final SecretKey secretkey = new SecretKeySpec(key, ALGORITHM_SHORT_NAME);
	    final IvParameterSpec ivparameter = new IvParameterSpec(ivp);
	    res = Cipher.getInstance(ALGORITHM_LONG_NAME, PROVIDER_NAME);
	    res.init(mode, secretkey, ivparameter);

	    return res;
	  }

	//java安全加密的部分对随机数又要求，普通的随机数是不行的，需要特殊处理，应该是长度、算法上有区别，而且好像存储也不一样。使用的方法如下：
	/**
	   * 获得密钥.
	   * @return 密钥.
	   */
	  public static byte[] generateKey() {
	    byte[] res = null;
	    KeyGenerator keyGen = null;
	    SecretKey key = null;
	    try {
	      keyGen = KeyGenerator.getInstance(ALGORITHM_SHORT_NAME, PROVIDER_NAME);
	      keyGen.init(128);
	      key = keyGen.generateKey();
	      res = key.getEncoded();
	    } catch (NoSuchAlgorithmException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchProviderException e) {
	      LOGGER.error(e.getMessage(), e);
	    }
	    return res;
	  }

	//Java的cipher可以完成加密和解密两种功能，处理过程如下
	/**
	   * 处理加密解密过程.
	   * @param input
	   *          输入.
	   * @param cipher
	   *          cipher.
	   * @return 结果.
	   */
	  private static byte[] process(final byte[] input, final Cipher cipher) {

	    byte[] res = null;
	    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	    CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);

	    try {
	      cOut.write(input);
	      cOut.flush();
	      cOut.close();
	      res = bOut.toByteArray();
	    } catch (IOException e) {
	      LOGGER.error(e.getMessage(), e);
	    }
	    return res;
	  }

	//加密和解密接口

	  /**
	   * 加密.
	   * @param data
	   *          加密的数据.
	   * @param key
	   *          密钥.
	   * @param iv
	   *          CBC算法所需初始矩阵.
	   * @return 加密结果.
	   */
	  public static byte[] encrypt(
	      final byte[] data, final byte[] key, final byte[] iv
	  ) {
	    byte[] res = null;
	    try {
	      res = process(data, generateCipher(Cipher.ENCRYPT_MODE, key, iv));
	    } catch (InvalidKeyException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchAlgorithmException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchProviderException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchPaddingException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (InvalidAlgorithmParameterException e) {
	      LOGGER.error(e.getMessage(), e);
	    }
	    return res;
	  }

	  /**
	   * 解密.
	   * @param data
	   *          解密的数据.
	   * @param key
	   *          密钥.
	   * @param iv
	   *          CBC算法所需初始矩阵.
	   * @return 解密结果.
	   */
	  public static byte[] decrypt(
	      final byte[] data, final byte[] key, final byte[] iv
	  ) {
	    byte[] res = null;
	    try {
	      res = process(data, generateCipher(Cipher.DECRYPT_MODE, key, iv));
	    } catch (InvalidKeyException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchAlgorithmException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchProviderException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (NoSuchPaddingException e) {
	      LOGGER.error(e.getMessage(), e);
	    } catch (InvalidAlgorithmParameterException e) {
	      LOGGER.error(e.getMessage(), e);
	    }
	    return res;
	  }
}
