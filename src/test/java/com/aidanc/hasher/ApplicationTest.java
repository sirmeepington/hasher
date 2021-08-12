package com.aidanc.hasher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.stefanbirkner.systemlambda.SystemLambda;

class ApplicationTest {

	final String filename = "src/test/resources/test file.txt";
	final String validHash = "315F5BDB76D078C43B8AC0064E4A0164612B1FCE77C869345BFC94C75894EDD3";
	private final PrintStream stdOut = System.out;
	private final ByteArrayOutputStream hijack = new ByteArrayOutputStream();

	@BeforeEach
	private void setup() {
		hijack.reset();
		System.setOut(new PrintStream(hijack));
	}

	@AfterEach
	private void teardown() {
		System.setOut(stdOut);
	}

	@Test
	void testMain_SystemExit_FailedToHash() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-f", "123", "-h", "123" });
		});
		assertEquals(status, -1);
	}

	@Test
	void testRun_Valid_Entries() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-f", filename, "-h", validHash });
		});
		assertEquals(0, status);
	}

	@Test
	void testPrintString() {
		Application app = new Application();
		app.print("Hello, world!");
		assertEquals("Hello, world!" + System.lineSeparator(), hijack.toString());
	}

	@Test
	void testSilent() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-s", "-f", filename, "-h", "123" });
		});
		assertTrue(hijack.toString().isEmpty());
		assertEquals(status, 1);
	}

	@Test
	void testSilentSuccess() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-s", "-f", filename, "-h", validHash });
		});
		assertTrue(hijack.toString().isEmpty());
		assertEquals(status, 0);
	}

}
