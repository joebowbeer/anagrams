package anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Accumulators;
import java.util.stream.Stream;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        assert args.length == 1; // words path
        Path path = FileSystems.getDefault().getPath(args[0]);
        try (BufferedReader r = Files.newBufferedReader(path, Charset.defaultCharset())) {
            anagrams(r.lines()).forEach(System.out::println);
        }
    }

    public static Stream<Collection<String>> anagrams(Stream<String> words) {
        Map<String, Collection<String>> map = words.accumulate(
                Accumulators.<String, String>groupBy(Anagrams::key));
        return map.values().parallelStream().filter(v -> v.size() > 1);
    } 

    private static String key(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static Collection<String> anagramWithMostLetters(Stream<String> words) {
        return anagrams(words).max(mostLetters).orElse(() -> null);
    }

    public static Collection<String> anagramWithMostWords(Stream<String> words) {
        return anagrams(words).max(mostWords).orElse(() -> null);
    }

    private static Comparator<Collection<String>> mostLetters = new MostLettersComparator();

    private static Comparator<Collection<String>> mostWords = new MostWordsComparator();

    private static class MostLettersComparator implements Comparator<Collection<String>> {
        @Override
        public int compare(Collection<String> o1, Collection<String> o2) {
            return o1.iterator().next().length() - o2.iterator().next().length();
        }
    }

    private static class MostWordsComparator implements Comparator<Collection<String>> {
        @Override
        public int compare(Collection<String> o1, Collection<String> o2) {
            return o1.size() - o2.size();
        }
    }
}
