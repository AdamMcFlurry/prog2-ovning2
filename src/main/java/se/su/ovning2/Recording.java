package se.su.ovning2;

import java.util.*;

public class Recording implements Comparable<Recording> {
  private final int year;
  private final String artist;
  private final String title;
  private final String type;
  private final Set<String> genre;

  public Recording(String title, String artist, int year, String type, Set<String> genre) {
    this.title = title;
    this.year = year;
    this.artist = artist;
    this.type = type;
    this.genre = genre;
  }

  public String getArtist() {
    return artist;
  }

  public Collection<String> getGenre() {
    return genre;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  @Override
  public String toString() {
    return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
  }

  @Override
  public boolean equals(Object o){
    if (o instanceof Recording r) {
      return (r.title.equals(title) && r.artist.equals(artist) && r.year == year);
    }
    return false;
  }

  //Alla attrbut i equals() måste också finnas i hash
  @Override
  public int hashCode() {
    return Objects.hash(year, artist, title);
  }

  //EJ KLAR
  @Override
  public int compareTo(Recording r) {
    if (year > r.year) {
      return 1;
    } else if (year < r.year){
      return -1;
    } else if (title.compareTo(r.title) > 0) {
      return 1;
    } else if (title.compareTo(r.title) < 0) {
      return -1;
    } else if (title.compareTo(r.artist) > 0) {
      return 1;
    } else if (title.compareTo(r.artist) < 0) {
      return -1;
    } else {
      return 0;
    }
    //.compareTo() för resten
  }
}
