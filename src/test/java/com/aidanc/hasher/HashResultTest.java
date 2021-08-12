package com.aidanc.hasher;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HashResultTest {

	@Test
	void testHashResult() {
		HashResult result = new HashResult("123", true, FailReason.NONE);

		assertTrue(result.isHashedSuccessfully());
		assertNotNull(result);
	}

	@Test
	void testGetResultHash() {
		HashResult result = new HashResult("123", true, FailReason.NONE);

		assertNotNull(result);
		assertEquals("123", result.getResultHash());

	}

	@Test
	void testHashMatches() {

		HashResult result = new HashResult("123", true, FailReason.NONE);

		assertNotNull(result);
		assertEquals("123", result.getResultHash());
		assertTrue(result.hashMatches("123"));
		assertDoesNotThrow(() -> result.hashMatches(null));
		assertFalse(result.hashMatches(null));
		assertFalse(result.hashMatches(" "));
		assertFalse(result.hashMatches(""));

	}

	@Test
	void testHashMatches_FailedHash() {
		HashResult failResult = new HashResult("123", false, FailReason.NONE);
		assertNotNull(failResult);
		assertFalse(failResult.hashMatches("123"));
		assertDoesNotThrow(() -> failResult.hashMatches(null));
		assertFalse(failResult.hashMatches(null));
		assertFalse(failResult.hashMatches(""));
	}

	@Test
	void testIsHashedSuccessfully_Success() {
		HashResult result = new HashResult("123", true, FailReason.NONE);
		assertTrue(result.isHashedSuccessfully());
	}

	@Test
	void testIsHashedSuccessfully_Fail() {
		HashResult result = new HashResult("123", false, FailReason.NONE);
		assertFalse(result.isHashedSuccessfully());
	}

	@Test
	void testGetFailedReason_NoFail() {
		HashResult result = new HashResult("123", true, FailReason.NONE);
		assertEquals(result.getFailedReason(), FailReason.NONE);
	}

	@Test
	void testGetFailedReason_Fail() {
		HashResult result = new HashResult("123", false, FailReason.INVALID_FILE);
		assertEquals(result.getFailedReason(), FailReason.INVALID_FILE);
	}

	@Test
	void testGetFailedReason_Success_FailReason() {
		HashResult result = new HashResult("123", true, FailReason.INVALID_FILE);
		assertNotEquals(result.getFailedReason(), FailReason.INVALID_FILE);
	}

}
