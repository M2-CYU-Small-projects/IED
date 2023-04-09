package fr.nacvs.ied_mediator.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilmPeople {

	private String title;
	private LocalDate date;
	private String director;
	private List<String> actors = new ArrayList<>();
	private List<String> producers = new ArrayList<>();

	public FilmPeople() {
		super();
	}

	public FilmPeople(String title, LocalDate date, String director, List<String> actors, List<String> producers) {
		super();
		this.title = title;
		this.date = date;
		this.director = director;
		this.actors = actors;
		this.producers = producers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public List<String> getProducers() {
		return producers;
	}

	public void setProducers(List<String> producers) {
		this.producers = producers;
	}

	@Override
	public String toString() {
		return "FilmPeople{" +
						"title='" + title + '\'' +
						", date=" + date +
						", director='" + director + '\'' +
						", actors=" + actors +
						", producers=" + producers +
						'}';
	}
}
