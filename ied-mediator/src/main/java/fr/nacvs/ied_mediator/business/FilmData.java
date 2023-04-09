package fr.nacvs.ied_mediator.business;

import java.time.LocalDate;

public class FilmData {

	private String title;
	private LocalDate date;
	private String genre;
	private String distributor;
	private long budget;
	private long incomeUs;
	private long incomeWorldwide;
	
	public FilmData() {
		super();
	}

	public FilmData(String title, LocalDate date, String genre, String distributor, long budget, long incomeUs, long incomeWorldwide) {
		super();
		this.title = title;
		this.date = date;
		this.genre = genre;
		this.distributor = distributor;
		this.budget = budget;
		this.incomeUs = incomeUs;
		this.incomeWorldwide = incomeWorldwide;
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

	@Override
	public String toString() {
		return "FilmData [title=" + title + ", date=" + date + ", genre=" + genre + ", distributor=" + distributor + ", budget=" + budget + ", incomeUs="
				+ incomeUs + ", incomeWorldwide=" + incomeWorldwide + "]";
	}
}
