package org.godot.utilities.utils;

import org.godot.utilities.core.callback.IAnyUtilsCallbacks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public final class MapUtils {

	private MapUtils() {
	}

	/**
	 * Generate map with all entries inside
	 *
	 * @param entries All entries to insert
	 * @param <K>     Key map type
	 * @param <V>     Value map type
	 * @return Returns a new map with all entries
	 */
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> of(Map.Entry<K, V> @NotNull ... entries) {
		Map<Object, Object> result = new HashMap<>();
		for (Map.Entry<?, ?> entry : entries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return (Map<K, V>) result;
	}

	/**
	 * Generate simple map entry
	 *
	 * @param key   Key value
	 * @param value Value of entry
	 * @param <K>   Generic key type
	 * @param <V>   Generic value type
	 * @return Returns a new entry instance
	 */
	@Contract(value = "_, _ -> new", pure = true)
	public static <K, V> Map.@NotNull Entry<K, V> entry(K key, V value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	/**
	 * Foreach method
	 *
	 * @param map      Target map
	 * @param callback Target execution callback
	 * @param <K>      Generic key type
	 * @param <V>      Generic value type
	 */
	public static <K, V> void foreach(@NotNull Map<K, V> map, IAnyUtilsCallbacks.IForeach<Map.Entry<K, V>> callback) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			callback.invoke(entry);
		}
	}

}
