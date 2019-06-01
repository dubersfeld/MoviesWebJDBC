package com.dub.spring.movies;

import java.util.Date;
import java.util.List;

public interface MovieServices {

	List<DisplayMovie> getAllMovies();
	
	Movie getMovie(long id);
	
	// should be unique
	DisplayMovie getMovie(String title, Date releaseDate);
		
	// can give several matches
	List<DisplayMovie> getMovie(String title);
	
	void deleteMovie(long id);
	
	void createMovie(Movie movie);	
	void updateMovie(Movie movie);	
	long numberOfMovies();
}// interface
