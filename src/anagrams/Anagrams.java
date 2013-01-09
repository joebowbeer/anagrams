package anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import static java.util.stream.Accumulators.groupBy;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        assert args.length == 1; // words path
        Path path = FileSystems.getDefault().getPath(args[0]);
        Function<String, String> key = s -> {
            char[] chars = s.toLowerCase().toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        };
        final Map<String, Collection<String>> map;
        try (BufferedReader r = Files.newBufferedReader(path, Charset.defaultCharset())) {
            map = r.lines().parallel().accumulate(groupBy(key));
        }
        map.values().stream().filter(v -> v.size() > 1).forEach(System.out::println);
    }
}
