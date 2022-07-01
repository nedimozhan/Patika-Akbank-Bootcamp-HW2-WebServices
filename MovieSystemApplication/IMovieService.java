package com.ned.MovieSystemApplication;

import java.util.List;

public interface IMovieService {
	public List<Movie> search(String movieName);
	public Movie findById(String id);
}
