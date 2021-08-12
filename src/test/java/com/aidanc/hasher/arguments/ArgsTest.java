package com.aidanc.hasher.arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.beust.jcommander.JCommander;

class ArgsTest {

	@Test
	void testGetFile() {
		Args argsObj = new Args();
		String[] argsArr = new String[] { "-f", "test.txt", "-h", "123" };
		JCommander.newBuilder().addObject(argsObj).args(argsArr).build();

		assertNotNull(argsObj.getFile());
		assertNotNull(argsObj.getAlgorithm());
		assertFalse(argsObj.getAlgorithm().isEmpty());
		assertEquals(argsObj.getFile(), new File("test.txt"));
		assertFalse(argsObj.isSilent());
		assertEquals(argsObj.getHash(), "123");
	}

}
