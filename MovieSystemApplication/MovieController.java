package com.ned.MovieSystemApplication;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@RestController
public class MovieController {
	
	private IMovieService restTempService;
	private IMovieService fileService;
	
	@Autowired
	public MovieController(@Qualifier("restTemplate") IMovieService movieService,@Qualifier("fileService") IMovieService fileService) {
		this.restTempService = movieService;
		this.fileService = fileService;
	}
	
	//Web Service 1
	@RequestMapping(path="/movies/search", method = RequestMethod.GET)
	public List<Movie> search(@RequestParam(name = "movie_name")String name) {
		
		return restTempService.search(name);
	}
	
	//Web Service 2
	@PostMapping("/movies/addToList/{id}")
    public Movie addToList(@PathVariable(name = "id") String id){
        
		Movie movie = this.restTempService.findById(id);
		FileMovieService fileMovieService = (FileMovieService)this.fileService;
		fileMovieService.writeToFile(movie);
		
		return movie;
    }
	
	// Web Service 3
	@PostMapping("/movies/detail/{id}")
    public Movie detail(@PathVariable(name = "id") String id){
        
		// If data has found in the file
		Movie movie = fileService.findById(id);
		if(movie != null) {
			return movie;
		}
		
		// Data didnt find in file so we have to fetch from external web service
		movie = restTempService.findById(id);
		
		return movie;
    }
}






