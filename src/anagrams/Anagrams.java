package anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        assert args.length == 1; // words path
        Map<String, List<String>> map = new HashMap<>();
        Path path = FileSystems.getDefault().getPath(args[0]);
        try (BufferedReader r = Files.newBufferedReader(path, Charset.defaultCharset())) {
            r.lines().forEach(s -> map.computeIfAbsent(key(s), k -> new ArrayList<>()).add(s));
        }
        map.values().stream().filter(v -> v.size() > 1).forEach(v -> System.out.println(v));
    }

    private static String key(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
