package fr.nacvs.ied_mediator.api.responses.film_by_title;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.nacvs.ied_mediator.business.FilmData;
import fr.nacvs.ied_mediator.business.FilmPeople;
import fr.nacvs.ied_mediator.business.FilmSummary;
import fr.nacvs.ied_mediator.util.DateUtils;

public class RespOneFilmByTitle {

	private String date;
	private String genre = "";
	private String distributor = "";
	private long budget;
	private long incomeUs;
	private long incomeWorldwide;
	private String director = "";
	private String summary = "";
	private List<String> actors = new ArrayList<>(0);

	public RespOneFilmByTitle() {
		super();
	}

	public RespOneFilmByTitle(LocalDate date, String genre, String distributor, long budget, long incomeUs, long incomeWorldwide, String summary,
			List<String> actors) {
		super();
		this.date = DateUtils.toString(date);
		this.genre = genre;
		this.distributor = distributor;
		this.budget = budget;
		this.incomeUs = incomeUs;
		this.incomeWorldwide = incomeWorldwide;
		this.summary = summary;
		this.actors = actors;
	}
	
	public void fillDataInfos(FilmData filmData) {
		this.date = DateUtils.toString(filmData.getDate());
		this.distributor = filmData.getDistributor();
		this.budget = filmData.getBudget();
		this.genre = filmData.getGenre();
		this.incomeUs = filmData.getIncomeUs();
		this.incomeWorldwide = filmData.getIncomeWorldwide();
	}

	public void fillPeopleInfos(FilmPeople filmPeople) {
		this.director = filmPeople.getDirector();
		this.actors = filmPeople.getActors();
	}
	
	public void fillSummaryInfos(FilmSummary filmSummary) {
		this.summary = filmSummary.getSummary();
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public long getIncomeUs() {
		return incomeUs;
	}

	public void setIncomeUs(long incomeUs) {
		this.incomeUs = incomeUs;
	}

	public long getIncomeWorldwide() {
		return incomeWorldwide;
	}

	public void setIncomeWorldwide(long incomeWorldwide) {
		this.incomeWorldwide = incomeWorldwide;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "RespOneFilmByTitle [date=" + date + ", genre=" + genre + ", distributor=" + distributor + ", budget=" + budget + ", incomeUs=" + incomeUs
				+ ", incomeWorldwide=" + incomeWorldwide + ", director=" + director + ", summary=" + summary + ", actors=" + actors + "]";
	}
}
