package com.aidanc.hasher;

/**
 * Immutable object for a result of running the {@link Hasher}.
 * 
 * @author aidan.crooks
 *
 */
public class HashResult {

	private final String resultHash;

	private final boolean hashedSuccessfully;

	private final FailReason failedReason;

	public HashResult(String resultHash, boolean hashedSuccessfully, FailReason failedReason) {
		super();
		this.resultHash = resultHash;
		this.hashedSuccessfully = hashedSuccessfully;
		this.failedReason = failedReason;
	}

	/**
	 * Retrieves the resulting hash. This will be <code>null</code> if the hashing
	 * failed for the given {@link FailReason}.
	 * 
	 * @return The resulting hash, or <code>null</code> if the hashing failed.
	 */
	public String getResultHash() {
		return resultHash;
	}

	/**
	 * Returns whether or not the hash given and the resulting hash are equal. This
	 * will always return false if hashing failed (if
	 * {@link #isHashedSuccessfully()} returns false).
	 * 
	 * @return A boolean showing whether or not the given hash matches the resulting
	 *         hash.
	 */
	public boolean hashMatches(String ourHash) {
		if (!isHashedSuccessfully())
			return false;

		if (ourHash == null || ourHash.isEmpty())
			return false;

		return ourHash.equalsIgnoreCase(resultHash);
	}

	/**
	 * A boolean determining whether or not the given hash operation succeeded. If
	 * this is true, the given {@link #resultHash} will be null. For more
	 * information why the hashing failed, see the {@link #failedReason}.
	 * 
	 * @return A boolean whether the hash operation succeeded.
	 */
	public boolean isHashedSuccessfully() {
		return hashedSuccessfully;
	}

	/**
	 * Retrieves the {@link FailReason} of this failed hashing operation.
	 * 
	 * @return An enumeration instance showing why this operation failed.
	 */
	public FailReason getFailedReason() {
		if (hashedSuccessfully)
			return FailReason.NONE;
		return failedReason;
	}

}
