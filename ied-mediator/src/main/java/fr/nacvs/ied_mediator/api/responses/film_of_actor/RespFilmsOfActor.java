package fr.nacvs.ied_mediator.api.responses.film_of_actor;

import java.util.ArrayList;
import java.util.List;

public class RespFilmsOfActor {

	private List<RespOneFilmOfActor> films = new ArrayList<>();

	public RespFilmsOfActor(List<RespOneFilmOfActor> films) {
		super();
		this.films = films;
	}

	public List<RespOneFilmOfActor> getFilms() {
		return films;
	}

	public void setFilms(List<RespOneFilmOfActor> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "RespFilmsOfActor [films=" + films + "]";
	}
}
