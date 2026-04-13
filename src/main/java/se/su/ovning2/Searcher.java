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
import java.util.Comparator;

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
    sorted.addAll(set);
    return Collections.unmodifiableSortedSet(sorted);
  }

  @Override
  public Collection<Recording> getRecordingsByGenre(String genre) {
    Set<Recording> set = genreMap.get(genre);

    if(set == null || set.isEmpty()) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(set);
  }

  @Override
  public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
    if (yearFrom > yearTo) {
      return Collections.emptySet();
    }
    SortedMap<Integer, Set<Recording>> sub = yearMap.subMap(yearFrom, yearTo + 1);

    if (sub.isEmpty()) {
      return Collections.emptySet();
    }
    Set<Recording> result = new HashSet<>();

    for (Set<Recording> set : sub.values()) {
      for (Recording r : set) {
        if (r.getGenre().equals(genre)) {
          result.add(r);
        }
      }
    }
    return Collections.unmodifiableSet(result);
  }

  @Override
  public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
    if (offered = null || offered.isEmpty()) {
      return Collections.emptySet();
    }
    Set<Recording> result = new HashSet<>();
    for (Recording r : offered) {
      if (!titleMap.containsKey(r.getTitle())){
        result.add(r);
      }
    }
    return Collections.unmodifiableSet(result);
  }
}
