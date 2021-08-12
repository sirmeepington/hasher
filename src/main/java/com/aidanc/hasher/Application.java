package com.aidanc.hasher;

import com.aidanc.hasher.arguments.Args;
import com.beust.jcommander.JCommander;

public class Application {

	private Args arguments;
	private Hasher hasher;
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
		JCommander.newBuilder().addObject(arguments).build().parse(args);

		print("Hashing using algorithm: \"" + arguments.getAlgorithm() + "\"...");
		String fileHash = hasher.hash(arguments.getFile(), arguments.getAlgorithm());

		if (fileHash == null || fileHash.isEmpty()) {
			print("Failed to hash. See previous message(s).", true);
			return 1;
		}

		displayComplete(arguments, fileHash);

		boolean match = arguments.getHash().equalsIgnoreCase(fileHash);

		return match ? 0 : 1;
	}

	public void displayComplete(final Args arguments, final String fileHash) {
		print("Complete.");
		print(String.format(formatStr, "Hashed:", arguments.getFile().getName()));
		print(String.format(formatStr, "Provided: ", arguments.getHash()));
		print(String.format(formatStr, "File Hash:", fileHash.toUpperCase()));
		print(String.format(formatStr, "Comparison: ",
				(arguments.getHash().equalsIgnoreCase(fileHash) ? "EQUAL" : "NOT EQUAL")));
	}

	public void print(String string, boolean error) {
		if (arguments.isSilent())
			return;

		if (error) {
			System.err.println(string);
		} else {
			System.out.println(string);
		}
	}

	public void print(String string) {
		print(string, false);
	}
}
