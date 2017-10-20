package anagrams;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

public class Anagrams {

  public static void main(String[] args) throws IOException {
    assert args.length == 1; // path-to-words
    Path path = FileSystems.getDefault().getPath(args[0]);
    anagrams(Files.lines(path)).forEach(System.out::println);
  }

  public static Stream<List<String>> anagrams(Stream<String> words) {
    return words.parallel().collect(groupingByAlphagramConcurrent())
        .values().parallelStream().filter(v -> v.size() > 1);
  }

  public static Stream<String> anagramsFor(String word, Stream<String> words) {
    int[] alphagram = alphagram(word);
    return words.parallel().filter(s -> !s.equals(word) && Arrays.equals(alphagram, alphagram(s)));
  }

  public static String anyAnagramFor(String word, Stream<String> words) {
    return anagramsFor(word, words).findAny().orElse(null);
  }

  private static Collector<String, ?, ConcurrentMap<int[], List<String>>>
      groupingByAlphagramConcurrent() {
    return groupingByConcurrent(
        Anagrams::alphagram,
        () -> new ConcurrentSkipListMap<>(Arrays::compare),
        toList());
  }

  private static int[] alphagram(String s) {
    return s.codePoints().sorted().toArray();
  }

  public static Collection<String> anagramWithMostLetters(Stream<String> words) {
    return anagrams(words).max(mostLetters).orElse(null);
  }

  public static Collection<String> anagramWithMostWords(Stream<String> words) {
    return anagrams(words).max(mostWords).orElse(null);
  }

  private static final Comparator<Collection<String>> mostLetters
      = comparingInt(cs -> cs.iterator().next().length());

  private static final Comparator<Collection<String>> mostWords
      = comparingInt(Collection::size);
}
