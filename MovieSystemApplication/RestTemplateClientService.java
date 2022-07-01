package com.ned.MovieSystemApplication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;



// I didnt write RestTemplate in MovieController class because I tought that we may have to change it one day with another library.
@Component
@Qualifier("restTemplate")
@Scope("singleton")
public class RestTemplateClientService implements IMovieService {
	
	private RestTemplate client;
	org.springframework.http.HttpHeaders headers;
	HttpEntity<?> requestEntity;
	ObjectMapper objectMapper;
	
	
	public RestTemplateClientService() {
		
		client = new RestTemplate();
		headers = new org.springframework.http.HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("authorization", "apikey 7MZD4WQL0wL8GlGixqrZOy:4frUSNRHQZ4Y10PgbkCH5g");		
		requestEntity = new HttpEntity<>(headers);
		objectMapper=new ObjectMapper();
	}
	
	// Get data from external web service and list fetched movies by name
	@Override
	public List<Movie> search(String movieName) {
		
		String urlString = "https://api.collectapi.com/imdb/imdbSearchByName?query=" + movieName;
		ResponseEntity<String> response = client.exchange(urlString, HttpMethod.GET,requestEntity,String.class);
		String r = response.getBody();
		List<Movie> movies = new ArrayList<Movie>();
		
		//Create movies objects from JSON
		try {
			
			JsonNode node = objectMapper.readTree(r);
			JsonNode resultNode = node.get("result");
			
			if(resultNode.isArray()) {
				ArrayNode moviesNode = (ArrayNode)resultNode;
				
				for(int i=0;i<moviesNode.size();i++) {
					JsonNode singleMovie = moviesNode.get(i);
					String title = singleMovie.get("Title").toString();
					String year = singleMovie.get("Year").toString();
					String imdbId = singleMovie.get("imdbID").toString();
					String type = singleMovie.get("Type").toString();
					
					Movie movie = new Movie();
					movie.setImdbID(imdbId);
					movie.setTitle(title);
					movie.setType(type);
					movie.setYear(year);
					
					movies.add(movie);
				}
			}		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return movies;
	}

	// Find and return movie by id from external web services
	@Override
	public Movie findById(String id) {
		
		String urlString = "https://api.collectapi.com/imdb/imdbSearchById?movieId=" + id;
		ResponseEntity<String> response = client.exchange(urlString, HttpMethod.POST,requestEntity,String.class);
		String r = response.getBody();
		Movie movie = new Movie();
		
		try {
			
			JsonNode node = objectMapper.readTree(r);
			JsonNode resultNode = node.get("result");
						
			String title = resultNode.get("Title").toString();
			String year = resultNode.get("Year").toString();
			String imdbId = resultNode.get("imdbID").toString();
			String type = resultNode.get("Type").toString();
				
			movie.setImdbID(imdbId);
			movie.setTitle(title);
			movie.setType(type);
			movie.setYear(year);
					
		}catch (Exception e) {
			// TODO: handle exception
		}
		return movie;
	}
}
