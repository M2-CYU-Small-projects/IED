package fr.nacvs.ied_mediator.manual;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;
import fr.nacvs.ied_mediator.config.SpringIoc;
import fr.nacvs.ied_mediator.mediator.Mediator;

public class TestMediator {

	public static void main(String[] args) {
		Mediator mediator = SpringIoc.getBean(Mediator.class);
		
		RespFilmsByTitle filmsByTitle = mediator.findFilmsByTitle("Avatar");
		System.out.println(filmsByTitle);
		
		RespFilmsOfActor filmsOfActor = mediator.findFilmsOfActor("Johnny Depp");
		System.out.println(filmsOfActor);
	}
}
