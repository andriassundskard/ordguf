import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Ordguf {
	public static void main(String[] args) throws IOException {
		char[] input = "delfor".toCharArray();

		List<String> results = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get("alleord.txt"), StandardCharsets.UTF_8)) {
			stream.forEach(s -> {
				if (s.length() > 1 && s.length() <= input.length) {
					if (contains(input, s) && !results.contains(s)) {
						results.add(s);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.sort(results, Comparator.comparingInt(String::length));

		for (String s : results) {
			System.out.println(s);
		}
	}

	private static boolean contains(char[] input, String word) {
		Map<Integer, Character> foundMap = new HashMap<>();
		int i = 0;
		for (char c : input) {
			int fromIndex = getFromIndex(c, foundMap);
			if (word.indexOf(c, fromIndex) > -1) {
				int index = word.indexOf(c, fromIndex);
				if (foundMap.get(index) == null || !(foundMap.containsKey(index)) && foundMap.get(index).equals(c)) {
					foundMap.put(index, c);
					i++;
				}
			}
			if (i == word.length()) {
				return true;
			}
		}
		return false;
	}

	private static int getFromIndex(char c, Map<Integer, Character> foundMap) {
		int retVal = 0;
		if (foundMap.containsValue(c)) {
			for (Map.Entry<Integer, Character> entry : foundMap.entrySet()) {
				if (entry.getValue().equals(c) && entry.getKey() > retVal) {
					retVal = entry.getKey() + 1;
				}
			}
		}
		return retVal;
	}
}
