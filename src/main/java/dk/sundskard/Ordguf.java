package dk.sundskard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
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
	public static void main(String[] args) throws IOException, URISyntaxException {
		new Ordguf().run();
	}

	public void run() throws IOException, URISyntaxException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Bogstaver: ");
			char[] input = reader.readLine().toCharArray();

			if (input.length == 0) {
				break;
			}

			List<String> results = new ArrayList<>();

			try (Stream<String> stream = Files.lines(
							Paths.get(this.getClass().getClassLoader().getResource("alleord.txt").toURI()),
							StandardCharsets.UTF_8)) {
				stream.forEach(s -> {
					if (s.length() > 1 && s.length() <= input.length) {
						if (this.contains(input, s) && !results.contains(s)) {
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
	}

	private boolean contains(char[] input, String word) {
		Map<Integer, Character> foundMap = new HashMap<>();
		int i = 0;
		for (char c : input) {
			int fromIndex = this.getFromIndex(c, foundMap);
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

	private int getFromIndex(char c, Map<Integer, Character> foundMap) {
		int retVal = -1;
		if (foundMap.containsValue(c)) {
			for (Map.Entry<Integer, Character> entry : foundMap.entrySet()) {
				if (entry.getValue().equals(c) && entry.getKey() > retVal) {
					retVal = entry.getKey() + 1;
				}
			}
		}
		return retVal < 0 ? 0 : retVal;
	}
}
