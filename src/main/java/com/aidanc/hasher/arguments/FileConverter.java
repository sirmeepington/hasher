package com.aidanc.hasher.arguments;

import java.io.File;

import com.beust.jcommander.IStringConverter;

/**
 * Utility class to convert a given <code>String</code> to a {@link File} when
 * parsed by Jcommander. <br/>
 * Creates a {@link File} object via the main constructor.
 * 
 * <pre>
 * {@code
 * 	return new File(value);
 * }
 * </pre>
 * 
 * @author aidan.crooks
 *
 */
public class FileConverter implements IStringConverter<File> {

	@Override
	public File convert(String value) {
		if (value == null || value.isEmpty())
			return null;
		return new File(value);
	}

}
