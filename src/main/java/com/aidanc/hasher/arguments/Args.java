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
			"--file" }, description = "Location of the file to hash.", required = true, converter = FileConverter.class)
	private File file;

	@Parameter(names = { "-s", "--silent" }, description = "Silences all messages. Result is the exit code.")
	private boolean silent = false;

	@Parameter(names = { "-help", "--help" }, help = true, description = "Shows the available parameters.")
	private boolean help;

	/**
	 * Hashing algorithm to use. Defaults to <code>SHA-256</code>
	 */
	@Parameter(names = { "-a", "--algorithm", "-algo" }, description = "Hashing algorithm to use")
	private String algorithm = "SHA-256";

	/**
	 * Input hash to compare result against. Required.
	 */
	@Parameter(names = { "-h", "--hash" }, description = "Hash to compare against", required = true)
	private String hash;

	@Parameter(names = { "-la",
			"--list-algorithms" }, description = "Lists all the hashing algorithms available.", help = true)
	private boolean listAlgorithms;

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

	/**
	 * Has the help flag been passed to the application?
	 * 
	 * @return Whether or not the help flag has been passed.
	 */
	public boolean isHelp() {
		return help;
	}

	/**
	 * Returns whether or not normal functionality should be disabled and that the
	 * program should list off the available hashing algorithms.
	 * 
	 * @return Whether to list the algorithms or proceed normally.
	 */
	public boolean shouldListAlgorithms() {
		return listAlgorithms;
	}

}
