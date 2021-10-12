package org.godot.utilities.engine;

import org.godot.utilities.core.Version;
import org.godot.utilities.core.error.InvalidEngineException;
import org.godot.utilities.core.ini.convertion.DataConvert;
import org.godot.utilities.core.error.InvalidTypeValueException;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.ContentGD;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Engine extends BaseEngine {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Current engine thread
	 */
	private EngineThread currentThread = null;

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Engine constructor. The main differences between {@link Engine} and {@link BaseEngine} are:
	 * <ul>
	 *     <li>{@link BaseEngine} is only used to storage base information of engine</li>
	 *     <li>{@link BaseEngine} is used when the engine location doesn't exist, but at some point it did.</li>
	 *     <li>{@link Engine} verify if the engine location exist, also check if the location is a valid executable file.</li>
	 *     <li>{@link Engine} determine the engine version by executing a special command</li>
	 * </ul>
	 *
	 * @param l Target engine location
	 * @throws IOException Error if file not exists or file is not a valid binary file
	 */
	public Engine(@NotNull Path l) throws IOException {
		super(l);
		// Check if location exists
		if (!exists())
			throw new FileNotFoundException(String.format("Location \"%s\" not exists.", location));
		// Check if location is a valid executable
		checkIfLocationIsValidBinary();
		checkIfLocationIsValidGodotFile();
		detectVersions();
	}

	/**
	 * Engine constructor.
	 * Pass another {@link BaseEngine} instance to create a new engine instance.
	 *
	 * @param engine Target engine base instance
	 * @throws IOException Error if location not exists or file is not a valid binary file
	 */
	public Engine(@NotNull BaseEngine engine) throws IOException {
		this(engine.getResourcePath());
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Get engine thread.
	 *
	 * @return Returns current instance of current engine or new instance
	 * @see EngineThread
	 */
	public EngineThread getEngineThread() {
		if (currentThread == null || !currentThread.isAlive()) {
			currentThread = new EngineThread(this);
		}
		// Return current thread
		return currentThread;
	}

	/**
	 * Object string representation.
	 *
	 * @return Object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return "Engine{" +
			"location=" + location +
			", rawVersion='" + rawVersion + '\'' +
			", isMono=" + isMono +
			", isEarlyAccess=" + isEarlyAccess +
			", isCustom=" + isCustom +
			", engineVersion=" + engineVersion +
			'}';
	}

	/* ------------------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Check if current engine location is a valid binary file.
	 * <p>
	 * I have not used the standard way of determining the type of files because it changes depending on
	 * the operating system it is running on.
	 * Also, I have not used it because many executable files have no extension on UNIX-like systems unlike Windows.
	 * <p>
	 * This way is too slow but ensures that the file is of the type required for its use.
	 *
	 * @throws IOException Error if target location is not a valid binary file
	 */
	private void checkIfLocationIsValidBinary() throws IOException {
		// Convert target file to url
		URL fileURL = getResourcePath().toUri().toURL();
		URLConnection connection = fileURL.openConnection();

		// Check mime types
		if (!ContentGD.allValidEngineExecutableMimeTypes.contains(connection.getContentType()))
			throw new IOException(String.format("Location \"%s\" is not a valid executable file.", getResourcePath()));
	}

	/**
	 * Determine if current location is a valid godot file.
	 * I don't have a better idea to do it. The only thing I do is check
	 * if there is a specific string in all the binary.
	 *
	 * @throws IOException Error if location is not valid
	 */
	private void checkIfLocationIsValidGodotFile() throws IOException {
		// Generate input stream
		InputStream stream = Files.newInputStream(getResourcePath());
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		// Storage line
		String line;
		boolean result = false;

		while ((line = reader.readLine()) != null) {
			Matcher matcher = ContentGD.magicExecutableData.matcher(line);
			if (matcher.find()) {
				result = true;
				break;
			}
		}

		reader.close();
		stream.close();

		// Check if is a valid executable
		if (!result)
			throw new InvalidEngineException(
				String.format("Location \"%s\" is not a valid Godot Executable.", getResourcePath())
			);
	}

	/**
	 * Detect engine version and determine some configurations.
	 *
	 * @throws IOException Error if location is not valid.
	 */
	private void detectVersions() throws IOException {
		// Generate process
		String[] command = Arr.of(getResourcePath().toString(), "--help");
		ProcessBuilder builder = new ProcessBuilder(command);
		// Generate pattern
		Pattern pattern = Pattern.compile(engineVersionRegexDetection, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		StringBuilder helpContent = new StringBuilder();

		// Start process
		Process process = builder.start();
		InputStream processIs = process.getInputStream();
		BufferedReader processReader = new BufferedReader(new InputStreamReader(processIs));
		String cLine;

		// Read result
		while ((cLine = processReader.readLine()) != null) {
			helpContent.append(cLine);
		}
		// Detect version
		Matcher versionMatcher = pattern.matcher(helpContent.toString());
		if (versionMatcher.find()) {
			rawVersion = versionMatcher.group();
			detectDirectVersion();
			detectVersionCharacteristics();
		}
		// Close and release all resources
		try {
			process.waitFor();
		} catch (InterruptedException err) {
			err.printStackTrace();
		} finally {
			processIs.close();
			processReader.close();
		}
	}

	/**
	 * Detect direct version object
	 */
	private void detectDirectVersion() {
		// Copy raw version
		String rv = rawVersion.toLowerCase();
		// Check if starts with "v"
		if (rv.startsWith("v"))
			rv = rv.substring(1, 2);
		else
			rv = rv.substring(0, 1);

		try {
			int code = DataConvert.parseInt(rv);
			engineVersion = Version.getFromCode(code);
		} catch (InvalidTypeValueException err) {
			engineVersion = Version.UNKNOWN;
		}
	}

	/**
	 * Detect version characteristics like:
	 * <ul>
	 *     <li>Is a <b>Mono</b> version</li>
	 *     <li>Is a custom build</li>
	 *     <li>Is an early access version</li>
	 * </ul>
	 */
	private void detectVersionCharacteristics() {
		// Check if is a mono version
		Pattern pattern = Pattern.compile(engineVersionMonoRegexDetection, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(rawVersion);

		isMono = matcher.find();
		// Check if is early access version
		pattern = Pattern.compile(engineVersionPreReleaseRegexDetection, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(rawVersion);

		isEarlyAccess = matcher.find();
		// Check if is a custom build
		pattern = Pattern.compile(engineVersionCustomBuildRegexDetection, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(rawVersion);

		isCustom = matcher.find();
	}

}
