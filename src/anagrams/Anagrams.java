package anagrams;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingByConcurrent;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        assert args.length == 1; // path-to-words
        Path path = FileSystems.getDefault().getPath(args[0]);
        anagrams(Files.lines(path)).forEach(System.out::println);
    }

    public static Stream<List<String>> anagrams(Stream<String> words) {
        return words.parallel().collect(groupingByConcurrent(Anagrams::key))
                .values().parallelStream().filter(v -> v.size() > 1);
    } 

    public static Stream<String> anagramsFor(String word, Stream<String> words) {
        String keyWord = key(word);
        return words.parallel().filter(s -> key(s).equals(keyWord) && !s.equals(word));
    }

    public static String anyAnagramFor(String word, Stream<String> words) {
        return anagramsFor(word, words).findAny().orElse(null);
    }

    private static String key(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static Collection<String> anagramWithMostLetters(Stream<String> words) {
        return anagrams(words).max(mostLetters).orElse(null);
    }

    public static Collection<String> anagramWithMostWords(Stream<String> words) {
        return anagrams(words).max(mostWords).orElse(null);
    }

    private static final Comparator<Collection<String>> mostLetters =
            (Collection<String> o1, Collection<String> o2)
                -> o1.iterator().next().length() - o2.iterator().next().length();

    private static final Comparator<Collection<String>> mostWords =
            (Collection<String> o1, Collection<String> o2) -> o1.size() - o2.size();
}
