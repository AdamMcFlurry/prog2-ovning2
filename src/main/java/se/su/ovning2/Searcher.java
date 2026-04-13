package se.su.ovning2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

public class Searcher implements SearchOperations {
  private Set<String> artists;
  private Set<String> genres;

  private Map<String, Recording> titleMap;
  private Map<String, Set<Recording>> artistMap;
  private Map<String, Set<Recording>> genreMap;
  private TreeMap<Integer, Set<Recording>> yearMap;

  public Searcher(Collection<Recording> data) {
    Collection<Recording> recordings = data;

    artists = new HashSet<>();
    genres = new HashSet<>();

    titleMap = new HashMap<>();
    artistMap = new HashMap<>();
    genreMap = new HashMap<>();
    yearMap = new TreeMap();

    for (Recording r : data) {

      //Samla unika värden
      artists.add(r.getArtist());
      genres.add(r.getGenre());

      //Title -> Recording
      titleMap.put(r.getTitle(), r);

      // Artist -> Set av recordings
      artistMap.computeIfAbsent(r.getArtist(), k -> new HashSet<>()).add(r);

      // Genre -> Set av recordings
      genreMap.computeIfAbsent(r.getGenre(), k -> new HashSet<>()).add(r);

      // År -> Set av recordings
      yearMap.computeIfAbsent(r.getYear(), k -> new HashSet<>()).add(r);

    }
  }

  @Override
  public long numberOfArtists() {
    return artists.size();
  }

  @Override
  public long numberOfGenres() {
    return genres.size();
  }

  @Override
  public long numberOfTitles() {
    return titleMap.size();
  }

  @Override
  public boolean doesArtistExist(String name) {
    return artists.contains(name);
  }

  @Override
  public Collection<String> getGenres() {
    return Collections.unmodifiableSet(genres);
  }

  @Override
  public Recording getRecordingByName(String title) {
    return titleMap.get(title);
  }

  @Override
  public Collection<Recording> getRecordingsAfter(int year) {
    // Alla år ska vara strikt större än year
    SortedMap<Integer, Set<Recording>> tail = yearMap.tailMap(year + 1);

    if (tail.isEmpty()) {
      return Collections.emptySet();
    }
    Set<Recording> result = new HashSet<>();

    for (Set<Recording> set : tail.values()) {
      result.addAll(set);
    }
    return Collections.unmodifiableSet(result);
  }

  @Override
  public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
    
    Set<Recording> set = artistMap.get(artist);
    if (set == null || set.isEmpty()) {
      return Collections.emptySortedSet();
    }
    SortedSet<Recording> sorted = new TreeSet<>(Comparator.comparingInt(Recording::getYear));
  }

  @Override
  public Collection<Recording> getRecordingsByGenre(String genre) {
    
  }

  @Override
  public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRecordingsByGenreAndYear'");
  }

  @Override
  public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'offerHasNewRecordings'");
  }
}
