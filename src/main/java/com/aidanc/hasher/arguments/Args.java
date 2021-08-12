package com.aidanc.hasher.arguments;

import java.io.File;

import com.beust.jcommander.Parameter;

/**
 * POJO class to hold launch arguments parsed by Jcommander.
 * 
 * @author aidan.crooks
 *
 */
public class Args {

	/**
	 * Input file, uses custom converter {@link FileConverter}. Required.
	 */
	@Parameter(names = { "-f",
			"-file" }, description = "File location", required = true, converter = FileConverter.class)
	private File file;

	@Parameter(names = { "-s", "-silent" }, description = "Silences all messags. Result is the exit code.")
	private boolean silent = false;

	/**
	 * Hashing algorithm to use. Defaults to <code>SHA-256</code>
	 */
	@Parameter(names = { "-a", "-algorithm", "-algo" }, description = "Hashing algorithm")
	private String algorithm = "SHA-256";

	/**
	 * Input hash to compare result against. Required.
	 */
	@Parameter(names = { "-h", "-hash" }, description = "Hash to compare against", required = true)
	private String hash;

	/**
	 * The input file. Gathered from a file location via {@link #file} Jcommander
	 * launch argument parser.
	 * 
	 * @return The input file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * The hashing algorithm in use. If not provided during the launch arguments,
	 * this defaults to SHA-256.
	 * 
	 * @return The hashing algorithm in use.
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Returns whether or not the application should run in silent mode. This
	 * prevents all messages from showing and has the comparison as the exit code.
	 * 
	 * @return A boolean showing if the application should run in silent mode.
	 */
	public boolean isSilent() {
		return silent;
	}

	/**
	 * The input hash given.
	 * 
	 * @return The input hash specified.
	 */
	public String getHash() {
		return hash;
	}

}
