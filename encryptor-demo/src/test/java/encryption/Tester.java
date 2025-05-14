package encryption;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class tester {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(2);

	private static String encryptionKey = "ABCDEFGH";
	private static String plainText = 
			  "In cryptography, a substitution cipher is a method of encoding by "
			+ "which units of plaintext are replaced with ciphertext, according to a "
			+ "fixed system; the \"units\" may be single letters (the most common), "
			+ "pairs of letters, triplets of letters, mixtures of the above, and so "
			+ "forth. The receiver deciphers the text by performing the inverse "
			+ "substitution.";

	private static String encryptedText = 
			  "WGVYXOBJOTOR GOHYOYOJXUCT RJAFKUUSGYJOHBJBOJWVBWNXCBTAOS NVUOYAO"
			+ "XYGGGXYWZJLOLYTWOKATO OJJVJTWOABOU Y JAGNROEAZXOWOUAAWETXBYOHJYH"
			+ "YAFRGUYAOEAOFJYXONXVAOOVBHOUNSAOSJYOOVAYUKVAOOLOOOOJOLTFGVYYCAVB"
			+ "B OOYYJBWOKVAAFOUVYJKVTXFGLOVYEKOOBTLLOVYOOA OOAFJAYOAYOBTYTBTRY"
			+ "CHYTOTAAGUTOTAY OOYJJXCTBOXAHVETOGBAAXOOAOKXP ANPVOWAOYKABOAWYOB"
			+ "SPCBOOOOUURJOOOOAAYOOOOOOAVUOOOOJJVYOOOOFOOROOOOYTYOOOOOXVJOOOOO";

	private static String decryptedText = 
			    "IN CRYPTOGRAPHY  A SUBSTITUTION CIPHER IS A METHOD OF ENCODING B"
			  + "Y WHICH UNITS OF PLAINTEXT ARE REPLACED WITH CIPHERTEXT  ACCORDI"
			  + "NG TO A FIXED SYSTEM  THE  UNITS  MAY BE SINGLE LETTERS  THE MOS"
			  + "T COMMON   PAIRS OF LETTERS  TRIPLETS OF LETTERS  MIXTURES OF TH"
			  + "E ABOVE  AND SO FORTH  THE RECEIVER DECIPHERS THE TEXT BY PERFOR"
			  + "MING THE INVERSE SUBSTITUTION                                   ";

	@Test
	public void test01_encrypt1() {
		System.out.println(Encryption.encrypt(plainText, encryptionKey));
		System.out.println("Expected:");
		System.out.println(encryptedText);
		assertTrue(Encryption.encrypt(plainText, encryptionKey).equals(encryptedText));
	}

	@Test
	public void test02_decrypt1() {
		System.out.println(Encryption.decrypt(encryptedText, encryptionKey));
		System.out.println("Expected:");
		System.out.println(decryptedText);
		assertTrue(Encryption.decrypt(encryptedText, encryptionKey).equals(decryptedText));
	}
}
