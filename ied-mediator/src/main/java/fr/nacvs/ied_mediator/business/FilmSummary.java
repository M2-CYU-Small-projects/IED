package fr.nacvs.ied_mediator.business;

import java.time.LocalDate;

public class FilmSummary {

	private String title;
	private LocalDate date;
	private String director;
	private String summary;

	public FilmSummary() {
		super();
	}

	public FilmSummary(String title, LocalDate date, String director, String summary) {
		super();
		this.title = title;
		this.date = date;
		this.director = director;
		this.summary = summary;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "FilmSummary{" +
						"title='" + title + '\'' +
						", date=" + date +
						", director='" + director + '\'' +
						", summary='" + summary + '\'' +
						'}';
	}
}
