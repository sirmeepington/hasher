package com.aidanc.hasher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class HasherTest {

	final String filename = "src/test/resources/test file.txt";
	final String validHash = "315F5BDB76D078C43B8AC0064E4A0164612B1FCE77C869345BFC94C75894EDD3";
	final String algorithm = "SHA-256";

	@Test
	public void testHash_ValidAlgorithm_NullFile() {
		final File file = null;

		HashResult outHash = new Hasher().hash(file, algorithm);

		assertNull(outHash.getResultHash());
		assertFalse(outHash.isHashedSuccessfully());
	}

	@Test
	public void testhash_ValidAlgorithm_ValidFile() {
		final File file = new File(filename);

		HashResult outHash = new Hasher().hash(file, algorithm);

		assertNotNull(outHash.getResultHash());
		assertTrue(outHash.isHashedSuccessfully());
		assertTrue(outHash.hashMatches(validHash));
	}

	@Test
	public void testhash_ValidAlgorithm_Directory() {
		final File file = new File("src/test/resources/");

		HashResult outHash = new Hasher().hash(file, algorithm);
		assertNull(outHash.getResultHash());
		assertFalse(outHash.isHashedSuccessfully());
		assertEquals(outHash.getFailedReason(), FailReason.INVALID_FILE);
	}

	@Test
	public void testhash_InvalidAlgorithm_ValidFile() {
		final String algorithm = "not a valid algorithm";
		final File file = new File(filename);

		HashResult outHash = new Hasher().hash(file, algorithm);
		assertNull(outHash.getResultHash());
		assertEquals(outHash.getFailedReason(), FailReason.INVALID_ALGORITHM);
		assertFalse(outHash.isHashedSuccessfully());
	}

}
