package fr.nacvs.ied_mediator.mediator;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;

public interface Mediator {

	RespFilmsByTitle findFilmsByTitle(String title);
	
	RespFilmsOfActor findFilmsOfActor(String actor);
}
