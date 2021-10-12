import java.util.*

fun loadEnvProps() {
	System.getenv().forEach { entry ->
		if (!rootProject.extra.has("${entry.key}")) {
			rootProject.extra.set("${entry.key}", entry.value)
		}
	}
}

loadEnvProps()
