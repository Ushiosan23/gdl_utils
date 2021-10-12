# Versions

- [0.1.0](https://github.com/Ushiosan23/gdl_utils/releases/tag/v0.1.0) __(Latest)__

## Information

- __0.1.0__
	- Can detect any type of godot engine
		- Get engine version
		- Detect if engine is a __Mono__ version
		- Detect if engine is an __"Early access"__ version
		- Detect if engine is a __"Custom compilation"__
	- Can read any godot project (engine.cfg, project.godot)
		- Get real project name
		- Get real project icon
		- Get main scene
		- Get project configuration
		- You cannot change this configuration directly
	- Can read any godot scene (only *.tscn scenes)
		- Get scene metadata
		- You cannot change this configuration directly
	- Can read godot resources (*.tres, *.shader)
		- Get resource type
		- Get resource metadata
		- You cannot change this configuration directly
	- Can execute engine instance
		- Execute only the engine
		- Execute engine with target project
			- Check if the engine and project are compatibles
		- Execute engine with custom scene
		- Open editor in specific location
		- Execute different versions of the engine
