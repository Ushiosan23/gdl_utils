package org.godot.utilities.engine;

import org.godot.utilities.core.AudioDriver;
import org.godot.utilities.core.Version;
import org.godot.utilities.core.VideoDriver;
import org.godot.utilities.project.Project;
import org.godot.utilities.project.scene.Scene;
import org.godot.utilities.utils.AnyUtils;
import org.godot.utilities.utils.Arr;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.*;

@SuppressWarnings("deprecation")
public class EngineArgs {

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Engine instance
	 */
	private final Engine engine;

	/**
	 * Builder instance
	 */
	private final Builder builder;

	/**
	 * List array arguments
	 */
	private final List<String> args = new ArrayList<>();

	/* ------------------------------------------------------------------
	 *
	 * Constructors
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default constructor
	 *
	 * @param e Engine instance
	 * @param b Builder instance
	 */
	private EngineArgs(Engine e, Builder b) {
		engine = e;
		builder = b;
	}

	/**
	 * Generate command
	 *
	 * @return Generate array command
	 */
	public String[] generateCommand() {
		// First element be always the engine
		args.add(engine.getResourcePath().toString());
		// Check if it has scenes
		if (!builder.builderArgs.containsKey("currentScene"))
			builder.builderArgs.put("editor", true);
		// Iterate all elements
		for (Map.Entry<String, Object> entry : builder.builderArgs.entrySet()) {
			iterationItem(entry);
		}
		// Get command array
		return args.toArray(String[]::new);
	}

	/* ------------------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Iterate all items
	 *
	 * @param entry Map entry
	 */
	@Contract(pure = true)
	private void iterationItem(Map.@NotNull Entry<String, Object> entry) {
		// Storage value
		Object value = entry.getValue();
		// Check value
		switch (entry.getKey()) {
			case "editor":
				AnyUtils.tryCast(value, this::configureEditor);
				break;
			case "videoDriver":
				AnyUtils.tryCast(value, this::configureVideoDriver);
				break;
			case "audioDriver":
				AnyUtils.tryCast(value, this::configureAudioDriver);
				break;
			case "size":
				AnyUtils.tryCast(value, this::configureSizeEditor);
				break;
			case "position":
				AnyUtils.tryCast(value, this::configurePosition);
				break;
			case "fixedFPS":
				AnyUtils.tryCast(value, this::configureFixedFPS);
				break;
			case "alwaysOnTop":
				AnyUtils.tryCast(value, this::configureAlwaysOnTop);
				break;
			case "project":
				AnyUtils.tryCast(value, this::configureProject);
				break;
			case "currentScene":
				AnyUtils.tryCast(value, this::configureCurrentScene);
				break;
		}
	}

	/**
	 * Configure editor status
	 *
	 * @param value Target status
	 */
	@SuppressWarnings("unused")
	private void configureEditor(boolean value) {
		args.add("-e");
	}

	/**
	 * Configure engine video driver
	 *
	 * @param driver Target video driver
	 */
	private void configureVideoDriver(VideoDriver driver) {
		switch (engine.engineVersion) {
			case V1:
			case V2:
				args.add("-vd");
				break;
			case V3:
				args.add("--video-driver");
				break;
			default:
				return;
		}
		args.add(driver.driverName);
	}

	/**
	 * Configure engine audio driver
	 *
	 * @param driver Target audio driver
	 */
	private void configureAudioDriver(AudioDriver driver) {
		switch (engine.engineVersion) {
			case V1:
			case V2:
				args.add("-ad");
				break;
			case V3:
				args.add("--audio-driver");
				break;
			default:
				return;
		}
		args.add(driver.driverName);
	}

	/**
	 * Configure engine window size
	 *
	 * @param size Target editor size
	 */
	private void configureSizeEditor(Dimension size) {
		switch (engine.engineVersion) {
			case V1:
			case V2:
				args.add("-r");
				break;
			case V3:
				args.add("--resolution");
				break;
			default:
				return;
		}

		args.add(String.format("%dx%d", size.width, size.height));
	}

	/**
	 * Configure engine window position
	 *
	 * @param point Target editor position
	 */
	private void configurePosition(Point point) {
		switch (engine.engineVersion) {
			case V2:
				args.add("-p");
				args.add(String.format("%dx%d", point.x, point.y));
				break;
			case V3:
				args.add("--position");
				args.add(String.format("%d,%d", point.x, point.y));
				break;
		}
	}

	/**
	 * Configure engine fixed fps
	 *
	 * @param fps Target fps
	 */
	private void configureFixedFPS(int fps) {
		if (engine.engineVersion == Version.V3) {
			args.add("--fixed-fps");
			args.add(String.format("%d", fps));
		}
	}

	/**
	 * Configure engine window
	 *
	 * @param top Always on top state
	 */
	private void configureAlwaysOnTop(boolean top) {
		if (engine.engineVersion == Version.V3) {
			args.add("--always-on-top");
		}
	}

	/**
	 * Configure project element
	 *
	 * @param proj Project instance
	 */
	private void configureProject(Project proj) {
		switch (engine.engineVersion) {
			case V1:
			case V2:
				args.add("-path");
				break;
			case V3:
				args.add("--path");
				break;
			default:
				return;
		}
		args.add(proj.getResourcePath().toString());
	}

	/**
	 * Configure current scene element
	 *
	 * @param cs Project current scene
	 */
	private void configureCurrentScene(@Nullable Scene cs) {
		// Check if scene is null
		if (cs == null) return;
		// Check engine version
		if (engine.engineVersion.versionCode >= cs.version.versionCode) {
			args.add(cs.getResourcePath().toString());
		}
	}

	/* ------------------------------------------------------------------
	 *
	 * Builder
	 *
	 * ------------------------------------------------------------------ */

	public static class Builder {

		/**
		 * Default builder map
		 */
		Map<String, Object> builderArgs = new HashMap<>();

		/**
		 * Set engine video driver
		 *
		 * @param driver Target driver
		 * @return Current builder
		 */
		public Builder setVideoDriver(@NotNull VideoDriver driver) {
			builderArgs.put("videoDriver", driver);
			return this;
		}

		/**
		 * Set engine audio driver
		 *
		 * @param driver Target driver
		 * @return Current builder
		 */
		public Builder setAudioDriver(@NotNull AudioDriver driver) {
			builderArgs.put("audioDriver", driver);
			return this;
		}

		/**
		 * Set current project
		 *
		 * @param project      Target project
		 * @param currentScene Current project scene
		 * @return Current builder
		 */
		public Builder setProject(@NotNull Project project, @Nullable Scene currentScene) {
			builderArgs.put("project", project);
			AnyUtils.requireNotNull(currentScene, item -> builderArgs.put("currentScene", item));
			return this;
		}

		/**
		 * Set current project
		 *
		 * @param project Target project
		 * @return Current builder
		 */
		public Builder setProject(@NotNull Project project) {
			return setProject(project, null);
		}

		/**
		 * Set project scene
		 *
		 * @param currentScene Target scene
		 * @return Current builder
		 */
		public Builder setScene(@Nullable Scene currentScene) {
			builderArgs.put("currentScene", currentScene);
			return this;
		}

		/**
		 * Set editor position
		 *
		 * @param position Target position
		 * @return Current builder
		 */
		public Builder setPosition(@NotNull Point position) {
			builderArgs.put("position", position);
			return this;
		}

		/**
		 * Set editor size
		 *
		 * @param size Target size
		 * @return Current builder
		 */
		public Builder setSize(@NotNull Dimension size) {
			builderArgs.put("size", size);
			return this;
		}

		/**
		 * Set editor fullscreen state
		 *
		 * @param value Target state
		 * @return Current builder
		 */
		public Builder setFullScreen(boolean value) {
			builderArgs.put("fullscreen", value);
			return this;
		}

		/**
		 * Set editor always on top state
		 *
		 * @param value Target state
		 * @return Current builder
		 */
		public Builder setAlwaysOnTop(boolean value) {
			builderArgs.put("alwaysOnTop", value);
			return this;
		}

		/**
		 * Set engine fixed FPS
		 *
		 * @param fps Target rate
		 * @return Current builder
		 */
		public Builder setFixedFPS(int fps) {
			builderArgs.put("fixedFPS", fps);
			return this;
		}

		/**
		 * @return Current builder
		 */
		public Builder openEditor() {
			builderArgs.put("editor", true);
			return this;
		}

		/**
		 * Generate engine arguments
		 *
		 * @param engine Target engine instance
		 * @return Returns an {@link EngineArgs} instance
		 * @see EngineArgs
		 */
		public EngineArgs build(Engine engine) {
			Map<String, Object> copyArgs = new LinkedHashMap<>();
			String[] comparatorKeys = Arr.of("editor", "videoDriver", "audioDriver", "size", "position",
				"fixedFPS", "alwaysOnTop", "project", "currentScene");

			// Sort keys
			for (String key : comparatorKeys) {
				if (builderArgs.containsKey(key)) {
					copyArgs.put(key, builderArgs.get(key));
				}
			}

			// Replace old arguments
			builderArgs = copyArgs;
			return new EngineArgs(engine, this);
		}

	}

}
