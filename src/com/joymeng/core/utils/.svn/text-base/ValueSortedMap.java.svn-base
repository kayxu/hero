package com.joymeng.core.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 可按键、值排序的Map
 * 
 * @author gejing
 * 
 * @param <K>
 * @param <V>
 */
public class ValueSortedMap<K, V> implements Map<K, V> {
	private List<K> keys = new ArrayList<K>();
	private List<V> values = new ArrayList<V>();

	private int size = 0;

	public ValueSortedMap(int initSize) {
		keys = new ArrayList<K>(initSize);
		values = new ArrayList<V>(initSize);
	}

	public ValueSortedMap() {
		this(10);
	}

	public ValueSortedMap(Map<? extends K, ? extends V> map) {
		for (K k : map.keySet()) {
			keys.add(k);
			values.add(map.get(k));
		}
		size = map.size();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size > 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	public int indexOfValue(V value) {
		return values.indexOf(value);
	}

	public int indexOfKey(K key) {
		return keys.indexOf(key);
	}

	@Override
	public V get(Object key) {
		int index = keys.indexOf(key);
		if (index >= 0) {
			return values.get(index);
		} else {
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		int index = keys.indexOf(key);
		if (index >= 0) {
			return values.set(index, value);
		} else {
			keys.add(key);
			values.add(value);
			size++;
			return value;
		}
	}

	@Override
	public V remove(Object key) {
		int index = keys.indexOf(key);
		if (index >= 0) {
			size--;
			keys.remove(index);
			return values.remove(index);
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K k : m.keySet()) {
			keys.add(k);
			values.add(m.get(k));
		}
	}

	@Override
	public void clear() {
		keys.clear();
		values.clear();
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		return new LinkedHashSet<K>(keys);
	}

	public List<V> values() {
		return values;
	}

	/**
	 * 洗牌
	 */
	public void shuffle() {
		Random r = new Random();
		int idx = 0;
		for (int i = size; i > 1; i--) {
			idx = r.nextInt(i);
			keys.set(idx, keys.set(i - 1, keys.get(idx)));
			values.set(idx, values.set(i - 1, values.get(idx)));
		}
	}

	/**
	 * 键排序
	 * 
	 * @param comparator
	 */
	public void sortKeys(Comparator<K> comparator) {
		if (size == 0)
			return;
		K min = null;
		int minIdx = -1;
		K t = null;
		for (int i = 0; i < size - 1; i++) {
			min = keys.get(i);
			for (int j = i + 1; j < size; j++) {
				t = keys.get(j);
				if (comparator.compare(min, t) > 0) {
					min = t;
					minIdx = j;
				}
			}
			if (minIdx > 0) {
				keys.set(minIdx, keys.set(i, keys.get(minIdx)));
				values.set(minIdx, values.set(i, values.get(minIdx)));
			}
			minIdx = -1;
		}
	}

	/**
	 * 值排序
	 * 
	 * @param comparator
	 */
	public void sortValues(Comparator<V> comparator) {
		if (size == 0)
			return;
		V min = null;
		int minIdx = 0;
		V t = null;
		int c;
		for (int i = 0; i < size - 1; i++) {
			min = values.get(i);
			for (int j = i + 1; j < size; j++) {
				t = values.get(j);
				c = comparator.compare(min, t);
				if (c > 0) {
					min = t;
					minIdx = j;
				}
			}
			values.set(minIdx, values.set(i, values.get(minIdx)));
			keys.set(minIdx, keys.set(i, keys.get(minIdx)));
		}
	}

	/**
	 * 二分法插入
	 * 
	 * @param key
	 * @param value
	 * @param cpr
	 */
	public void binaryPut(K key, V value, Comparator<V> cpr) {
		// 寻找中间点，对比中间点和中间点前的值大小。
		remove(key);
		// 插入到 >=中间点前 <=中间点之间
		int low = 0;
		int high = size - 1;
		int mid = -1;
		V midVal;
		int _mid = -2;
		V _midVal;
		int cmp1 = 0;
		int cmp2 = 0;
		while (low <= high) {
			mid = (low + high) >>> 1;
			_mid = mid - 1;
			midVal = values.get(mid);
			cmp1 = cpr.compare(midVal, value);

			if (cmp1 < 0) {
				if (_mid < 0) {
					mid += 1;
					break;
				} else {
					_midVal = values.get(_mid);
					cmp2 = cpr.compare(_midVal, value);
					if (cmp2 >= 0) {
						break;
					}
				}
				low = mid + 1;
			} else if (cmp1 > 0) {
				high = mid - 1;
			} else
				break; // key found
		}
		if (mid >= 0) {
			values.add(mid, value);
			keys.add(mid, key);
			size++;
		}
	}

	@Override
	public String toString() {
		if (size == 0)
			return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (int i = 0; i < size; i++) {
			sb.append(keys.get(i));
			sb.append('=');
			sb.append(values.get(i));
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		return sb.append('}').toString();
	}

	public static void main(String[] args) {
		ValueSortedMap<Integer, Integer> map = new ValueSortedMap<Integer, Integer>();
		for (int i = 9; i < 16; i += 2) {
			map.put(i, i);
		}
		System.out.println(map);
		Map<Integer, Integer> temp = new ValueSortedMap<Integer, Integer>(map);
		Comparator<Integer> cpr = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};
		map.sortKeys(cpr);
		System.out.println(map);
		System.out.println(map.keySet());
		for (int k : temp.keySet()) {
			if (temp.get(k) != map.get(k)) {
				System.out.println("DDDDDD    " + k);
			}
		}
		for (int i = 0; i < 5; i++) {
		}
		System.out.println(map);
	}

	/**
	 * do not use it! always return null;
	 */
	@Deprecated
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new IllegalAccessError("do not use it! always return null;");
	}

	class DreamMapEntry implements Entry<K, V> {

		@Override
		public K getKey() {
			return null;
		}

		@Override
		public V getValue() {
			return null;
		}

		@Override
		public V setValue(V value) {
			return null;
		}

	}

}
