package com.dub.advanced;

import java.util.Date;
import java.util.List;

import com.dub.actors.Actor;
import com.dub.directors.Director;
import com.dub.movies.Movie;

public interface AdvancedDAO {
	
	public List<Movie> getMoviesByActorName(String firstName, String lastName);
	
	public List<Actor> getActorsByMovie(String title, Date releaseDate);
	
	public List<Movie> getMoviesByDirectorName(String firstName, String lastName);
	
	public List<Actor> getActorsByDirectorName(String firstName, String lastName);

	public List<Director> getDirectorsByActorName(String firstName, String lastName);

	public void createActorFilm(Long actorId, Long movieId);

	//public void createActorFilm(String firstName, String lastName, String title, Date releaseDate);

	public void createActorFilm(Actor actor, String title, Date releaseDate);




}
