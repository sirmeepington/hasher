package com.aidanc.hasher;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
		Security.addProvider(new BouncyCastleProvider());
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
			if (arguments.shouldListAlgorithms()) {
				if (arguments.isSilent())
					return 0;
				listAlgorithms();
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

	private void listAlgorithms() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Currently available hashing algorithms:\n");
		final Set<String> algos = gatherAlgorithms();
		for (String s : algos) {
			if (s.contains(".")) {
				continue; // The ones with dots in are OID format.
			}
			sb.append(s);
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	// Not gonna lie, gotta thank StackOverflow for this one, holy shit.
	// https://stackoverflow.com/a/24983009
	private Set<String> gatherAlgorithms() {
		Set<String> algos = new HashSet<String>();
		final Provider[] providers = Security.getProviders();
		for (Provider prov : providers) {
			Set<Service> services = prov.getServices();
			for (Service svc : services) {
				if (svc.getType().equals(MessageDigest.class.getSimpleName())) {
					algos.add(svc.getAlgorithm());
				}
			}
			final Set<Object> keys = prov.keySet();
			for (Object key : keys) {
				final String prefix = "Alg.Alias." + MessageDigest.class.getSimpleName() + ".";
				if (key.toString().startsWith(prefix)) {
					String value = prov.get(key.toString()).toString();
					algos.add(value);
				}
			}
		}
		return algos;
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
