package com.aidanc.hasher.arguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;

import org.junit.jupiter.api.Test;

class FileConverterTest {

	final String filename = "src/test/resources/test file.txt";

	@Test
	void testConvert_ValidFile() {

		FileConverter converter = new FileConverter();
		File test = converter.convert(filename);
		File file = new File(filename);
		assertEquals(test, file);
	}

	@Test
	void testConvert_NullFile() {
		FileConverter converter = new FileConverter();
		assertDoesNotThrow(() -> converter.convert(null));
		assertNull(converter.convert(null));
	}

	@Test
	void testConvert_EmptyFilename() {
		FileConverter converter = new FileConverter();
		assertDoesNotThrow(() -> converter.convert(""));
		assertNull(converter.convert(""));
	}

}
