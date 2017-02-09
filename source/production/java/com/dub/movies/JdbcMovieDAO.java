package com.dub.movies;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dub.exceptions.DirectorNotFoundException;
import com.dub.exceptions.DuplicateMovieException;
import com.dub.exceptions.MovieNotFoundException;


@Repository
public class JdbcMovieDAO implements MovieDAO {
	
	@Resource 
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplateObject;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateObject;
	private SimpleJdbcInsert insertMovie;
	
	@PostConstruct
	public void setDataSource() {
		this.jdbcTemplateObject = 
	    		  new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplateObject = 
	    		  new NamedParameterJdbcTemplate(dataSource);

		this.insertMovie =
	    		  new SimpleJdbcInsert(dataSource)
	      				.withTableName("movie")
	      				.usingGeneratedKeyColumns("movieId");
	}
	
	@Override
	public List<Movie> listAllMovies() {		
		String SQL = "SELECT * FROM movie";
		List<Movie> movies = jdbcTemplateObject.query(
										SQL, new MovieMapper());
		return movies;
	}
	
	@Override
	public long getNumberOfMovies() {
		String SQL = "SELECT COUNT(*) FROM movie";
		return jdbcTemplateObject.queryForObject(SQL, Long.class);
		
	}

	@Override
	public List<Movie> getMovie(String title) {
		
		String SQL = "SELECT * FROM movie WHERE title = :title";	   		   
		SqlParameterSource namedParameters = new MapSqlParameterSource("title", title);  		   
		
		List<Movie> movies = namedParameterJdbcTemplateObject.query(
				SQL, namedParameters, new MovieMapper());
	
		return movies;
	}

	@Override
	public Movie getMovie(String title, Date releaseDate) {
		String SQL = "SELECT * FROM movie" + 
					" WHERE title = :title AND releasedate = :releaseDate";	   		   
		SqlParameterSource namedParameters = new MapSqlParameterSource()
						.addValue("title", title)
						.addValue("releaseDate", releaseDate);
		
		try {
			Movie movie = namedParameterJdbcTemplateObject.queryForObject(
					SQL, namedParameters, new MovieMapper());
		
			return movie;
		} catch (EmptyResultDataAccessException e) {
			throw new MovieNotFoundException();
		}
	}
	
	@Override
	public void update(Movie movie) {
				
		String SQL = "UPDATE movie SET directorId = :directorId, runningTime = :runningTime" + 
				" WHERE title = :title AND releaseDate = :releaseDate";
				
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("title", movie.getTitle())
				.addValue("releaseDate", movie.getReleaseDate())
				.addValue("directorId", movie.getDirectorId())
				.addValue("runningTime", movie.getRunningTime());	
		try {				
			namedParameterJdbcTemplateObject.update(SQL, namedParameters);
		} catch (Exception e) {
			String ex = ExceptionUtils.getRootCauseMessage(e);					
			if (ex.contains("FOREIGN KEY")) {
				throw new DirectorNotFoundException();
			} else {
				throw e;
			}
		}	
	}

	
	@Override
	public void create(Movie movie) {
		SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("title", movie.getTitle())
		.addValue("releaseDate", movie.getReleaseDate())
		.addValue("directorId", movie.getDirectorId())	
		.addValue("runningTime", movie.getRunningTime());	
		try {
			// actual insertion
			Number newId = insertMovie.executeAndReturnKey(parameters);		   			   		   	
			movie.setId(newId.intValue());
		} catch (Exception e) {
			String ex = ExceptionUtils.getRootCauseMessage(e);
			if (ex.contains("movie_unique")) {
				throw new DuplicateMovieException();				
			} else if (ex.contains("FOREIGN KEY")) {
				throw new DirectorNotFoundException();
			} else {
				throw e;
			}
		}
	}

	
	@Override
	public Movie getMovie(long id) {
		String SQL = "SELECT * FROM movie" + 
				" WHERE movieId = :id";	   		   
		SqlParameterSource namedParameters = new MapSqlParameterSource()
							.addValue("id", id);
		try {
			Movie movie = namedParameterJdbcTemplateObject.queryForObject(
							SQL, namedParameters, new MovieMapper());
			return movie;
		} catch (EmptyResultDataAccessException e) {
			throw new MovieNotFoundException();			
		}
	}

	@Override
	public void delete(long id) {
		String SQL = "DELETE FROM movie WHERE movieId = :id";	   		   
		SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("id", id);
		
		namedParameterJdbcTemplateObject.update(SQL, namedParameters);
	
	}
	
}
