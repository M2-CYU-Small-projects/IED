package fr.nacvs.ied_mediator.api.responses.film_by_title;

import java.util.ArrayList;
import java.util.List;

public class RespFilmsByTitle {

	private List<RespOneFilmByTitle> films = new ArrayList<>(0);

	public RespFilmsByTitle() {
		super();
	}

	public RespFilmsByTitle(List<RespOneFilmByTitle> films) {
		super();
		this.films = films;
	}

	public List<RespOneFilmByTitle> getFilms() {
		return films;
	}

	public void setFilms(List<RespOneFilmByTitle> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "RespFilmsByTitle [films=" + films + "]";
	}

}
