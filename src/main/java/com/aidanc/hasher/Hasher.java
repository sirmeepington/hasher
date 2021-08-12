package com.aidanc.hasher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing operations. <br/>
 * 
 * @author aidan.crooks
 */
public class Hasher {

	/**
	 * Hashes the given <code>file</code> using the given <code>hashAlso</code>.
	 * Returns a <code>String</code> of the resulting hash if succeeded, or
	 * <code>null</code> if either the <code>hashAlgo</code> is unknown or
	 * unavailable or if an <code>IOException</code> occurs during the hashing.
	 * 
	 * @param file     A {@link File} to get the hash of.
	 * @param hashAlgo The hashing algorithm to use.
	 * @return A <code>String</code> of the file's hash if the hashing succeeds,
	 *         otherwise <code>null</code>
	 */
	public HashResult hash(final File file, final String hashAlgo) {
		String resultHash = null;
		FailReason fail = FailReason.NONE;

		try {
			MessageDigest digest = MessageDigest.getInstance(hashAlgo);
			resultHash = consume(digest, file);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Unknown hashing algorithm: " + hashAlgo);
			fail = FailReason.INVALID_ALGORITHM;
		} catch (FileNotFoundException fileEx) {
			System.err.println("File does not exist.");
			fail = FailReason.INVALID_FILE;
		} catch (IOException ex) {
			System.err.println("IOException caught when hashing file.");
			fail = FailReason.IO_FAILURE;
		}

		return new HashResult(resultHash, fail == FailReason.NONE, fail);
	}

	/**
	 * Feeds the bytes of the given <code>file</code> and to the given
	 * <code>digest</code>, and then returns the resulting bytes as a
	 * <code>String</code>.
	 * 
	 * This method is a less-safe version of {@link #hash(File, String)} as it
	 * throws the exception instead of handling it safely.
	 * 
	 * @param digest The {@link MessageDigest} to feed the bytes into and retrieved
	 *               the digested bytes from.
	 * @param file   The {@link File} reference to open and feed into the given
	 *               <code>digest</code> object.
	 * @return A <code>String</code> representing the hex value of the bytes given
	 *         from the {@link MessageDigest} after digestion.
	 * @throws IOException If the file cannot be read from a {@link FileInputStream}
	 *                     this is thrown, and no result or hashing occurs.
	 */
	public String consume(final MessageDigest digest, final File file) throws FileNotFoundException, IOException {
		if (file == null || !file.isFile())
			throw new FileNotFoundException();
		try (FileInputStream stream = new FileInputStream(file)) {
			final byte[] bytes = new byte[1024];
			int count = 0;
			while ((count = stream.read(bytes)) != -1) {
				digest.update(bytes, 0, count);
			}
		}

		final byte[] digested = digest.digest();
		final StringBuilder sb = new StringBuilder();
		for (byte b : digested) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}
