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
import java.util.TreeSet;
import java.util.Comparator;

public class Searcher implements SearchOperations {
  private Set<String> artists;
  private Set<String> genres;
  private Set<Recording> allRecordings; 

  private final Map<String, Set<Recording>> genreToRecordings = new HashMap<>();
  private final Map<String, Set<Recording>> artistToRecordings = new HashMap<>();
  private final Map<String, Recording> titleToRecording = new HashMap<>();
  private final Set<Recording> allRecording = new HashSet<>();
  private final SortedMap<Integer, Set<Recording>> yearToRecordings = new TreeMap<>();
  

  public Searcher(Collection<Recording> data) {
    Collection<Recording> recordings = data;

    artists = new HashSet<>();
    genres = new HashSet<>();
    allRecordings = new Hashset<>();

    titleMap = new HashMap<>();
    artistMap = new HashMap<>();
    genreMap = new HashMap<>();
    yearMap = new TreeMap();

    for (Recording r : recordings) {
      allRecordings.add(r);
      artists.add(r.getArtist());
      titleMap.put(r.getTitle(),r);
      artistMap.computeIfAbsent(r.getArtist(), k -> new HashSet<>()).add(r);

      for (String g : r.getGenre()) {
        genres.add(g);
        genreMap.computeIfAbsent(g, k -> new HashSet<>()).add(r);
      }
    
    yearMap.computeIfAbsent(r.getYear(), k -> new HashSet<>()).add(r);
    
    }
  }

  @Override
  public long numberOfArtists() {
    return artistToRecordings.size();
  }

  @Override
  public long numberOfGenres() {
    return genreToRecordings.size();
  }

  @Override
  public long numberOfTitles() {
    return titleToRecording.size();
  }

  @Override
  public boolean doesArtistExist(String name) {
    return artistToRecordings.containsKey(name);
  }

  @Override
  public Collection<String> getGenres() {
    return Collections.unmodifiableSet(genreToRecordings.keySet());
  }

  @Override
  public Recording getRecordingByName(String title) {
    return titleToRecording.get(title);
  }

  @Override
  public Collection<Recording> getRecordingsAfter(int year) {
    SortedMap<Integer, Set<Recording>> afterYear = yearToRecordings.tailMap(year);
    Set<Recording> allAfter = new HashSet<>();
    for (Set<Recording> currentYear : afterYear.values()) {
      allAfter.addAll(currentYear);
    }
    return Collections.unmodifiableSet(allAfter);
  }

  @Override
  public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {  
    Set<Recording> sameArtist = artistToRecordings.get(artist);
    if (sameArtist == null) {
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
        if (r.getGenre().contains(genre)) {
          result.add(r);
        }
      }
    }
    return Collections.unmodifiableSet(result);
  }

  @Override
  public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
    if (offered == null || offered.isEmpty()) {
      return Collections.emptySet();
    }
    Set<Recording> result = new HashSet<>();
    for (Recording r : offered) {
      if (!allRecordings.contains(r)){
        result.add(r);
      }
    }
    return Collections.unmodifiableSet(result);
  }
}