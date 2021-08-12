package com.aidanc.hasher;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * An enumeration type explaining the reason why the hashing operation fails.
 * 
 * If the operation succeeded without issus, the given value will be
 * {@link #NONE}.
 * 
 * @author aidan.crooks
 *
 */
public enum FailReason {

	/**
	 * No failure occurred, the operation completed successfully.
	 */
	NONE,
	/**
	 * The given algorithm was invalid or not supported by the {@link MessageDigest}
	 * class.
	 */
	INVALID_ALGORITHM,
	/**
	 * The specified file is invalid or does not exist.
	 */
	INVALID_FILE,
	/**
	 * An {@link IOException} was thrown, and the hashing could not complete
	 * successfully.
	 */
	IO_FAILURE;

}
