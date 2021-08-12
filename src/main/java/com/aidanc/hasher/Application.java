package com.aidanc.hasher;

import com.aidanc.hasher.arguments.Args;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Application {

	private final Args arguments;
	private final Hasher hasher;
	private final String formatStr = "%-15s %s";

	public static void main(String[] args) {
		Application app = new Application();
		System.exit(app.run(args));
	}

	public Application() {
		arguments = new Args();
		hasher = new Hasher();
	}

	public int run(String[] args) {
		JCommander launchArgs = JCommander.newBuilder().addObject(arguments).build();
		launchArgs.setProgramName("hasher");
		try {
			launchArgs.parse(args);
			if (arguments.isHelp()) {
				launchArgs.usage();
				return 0;
			}
		} catch (ParameterException ex) {
			System.out.println(ex.getMessage());
			ex.getJCommander().usage();
			return 1;
		}

		print("Hashing using algorithm: \"" + arguments.getAlgorithm() + "\"...");
		final HashResult hashResult = hasher.hash(arguments.getFile(), arguments.getAlgorithm());

		if (!hashResult.isHashedSuccessfully()) {
			print("Failed to hash. See previous message(s).", true);
			return -1;
		}

		displayComplete(arguments, hashResult);

		return hashResult.hashMatches(arguments.getHash()) ? 0 : 1;
	}

	public void displayComplete(final Args arguments, final HashResult hashResult) {
		print("Complete.");
		print(String.format(formatStr, "Hashed:", arguments.getFile().getName()));
		print(String.format(formatStr, "Provided: ", arguments.getHash()));
		print(String.format(formatStr, "File Hash:", hashResult.getResultHash().toUpperCase()));
		print(String.format(formatStr, "Comparison: ",
				(hashResult.hashMatches(arguments.getHash()) ? "EQUAL" : "NOT EQUAL")));
	}

	public void print(final String string, boolean error) {
		if (arguments.isSilent())
			return;

		if (error) {
			System.err.println(string);
		} else {
			System.out.println(string);
		}
	}

	public void print(final String string) {
		print(string, false);
	}
}
