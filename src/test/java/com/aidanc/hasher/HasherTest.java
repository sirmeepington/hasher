package com.aidanc.hasher;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import org.junit.jupiter.api.Test;

public class HasherTest {

	final String filename = "src/test/resources/test file.txt";

	@Test
	public void testHash_ValidAlgorithm_NullFile() {

		final String algorithm = "SHA-256";
		final File file = null;

		String outHash = new Hasher().hash(file, algorithm);

		assertNull(outHash);
	}

	@Test
	public void testhash_ValidAlgorithm_ValidFile() {
		final String algorithm = "SHA-256";

		final File file = new File(filename);

		String outHash = new Hasher().hash(file, algorithm);

		assertNotNull(outHash);
	}

	@Test
	public void testhash_ValidAlgorithm_Directory() {
		final String algorithm = "SHA-256";
		final File file = new File("src/test/resources/");

		String outHash = new Hasher().hash(file, algorithm);

		assertNull(outHash);
	}

	@Test
	public void testhash_InvalidAlgorithm_ValidFile() {
		final String algorithm = "not a valid algorithm";
		final File file = new File(filename);

		String outHash = new Hasher().hash(file, algorithm);
		assertNull(outHash);
	}

	@Test()
	public void testhash_ValidAlgorithm_ValidFile_ForcedIOException() throws IOException {
		try (final RandomAccessFile fileAccess = new RandomAccessFile(filename, "rw")) {
			try (FileLock lock = fileAccess.getChannel().lock()) {
				Hasher hasher = new Hasher();
				String out = hasher.hash(new File(filename), "SHA-256");
				assertNull(out);
			}
		}
	}

}
