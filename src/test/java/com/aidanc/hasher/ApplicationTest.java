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
	private final ByteArrayOutputStream hijackSysOut = new ByteArrayOutputStream();
	private final PrintStream stdErr = System.err;
	private final ByteArrayOutputStream hijackSysErr = new ByteArrayOutputStream();

	@BeforeEach
	private void setup() {
		hijackSysOut.reset();
		System.setOut(new PrintStream(hijackSysOut));
		hijackSysErr.reset();
		System.setErr(new PrintStream(hijackSysErr));
	}

	@AfterEach
	private void teardown() {
		System.setOut(stdOut);
		System.setErr(stdErr);
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
		assertEquals("Hello, world!" + System.lineSeparator(), hijackSysOut.toString());
	}

	@Test
	void testSilent() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-s", "-f", filename, "-h", "123" });
		});
		assertTrue(hijackSysOut.toString().isEmpty());
		assertEquals(status, 1);
	}

	@Test
	void testHelpParam() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-help" });
		});
		assertEquals(0, status);
	}

	@Test
	void testMissingArgs_NoException() throws Exception {
		int statusOne = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "" });
		});
		assertEquals(1, statusOne);
		assertTrue(hijackSysErr.toString().isEmpty());
	}

	@Test
	void testSilentSuccess() throws Exception {
		int status = SystemLambda.catchSystemExit(() -> {
			Application.main(new String[] { "-s", "-f", filename, "-h", validHash });
		});
		assertTrue(hijackSysOut.toString().isEmpty());
		assertEquals(status, 0);
	}

}
