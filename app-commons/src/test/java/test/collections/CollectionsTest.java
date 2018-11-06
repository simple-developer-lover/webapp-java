package test.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionsTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> dumps(List<T> list1, List<T> list2) {
		List<T> result = new ArrayList<>();
		if (list1 == null || list1.isEmpty() || list2 == null || list2.isEmpty()) {
			return result;
		}
		Map<Integer, T> maps = (list1.size() > list2.size() ? list2 : list1).stream()
				.collect(Collectors.toMap(t -> t.hashCode(), t -> t));
		List<T> list = list1.size() > list2.size() ? list1 : list2;
		for (T t : list) {
			if (t == null) {
				continue;
			}
			T t1 = maps.get(t.hashCode());
			if ((t1 != null && t instanceof Comparable && ((Comparable) t).compareTo(t1) == 0)
					|| (t == t1 || t.equals(t1))) {
				result.add(t);
			} else {
				continue;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list1.add(i);
		}
		List<Integer> list2 = new ArrayList<>();
		for (int i = 5; i < 15; i++) {
			list2.add(i);
		}
		for (Integer i : dumps(list1, list2)) {
			System.out.println(i);
		}
	}
}
