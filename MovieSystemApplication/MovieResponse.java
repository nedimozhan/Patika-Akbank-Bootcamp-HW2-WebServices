package com.ned.MovieSystemApplication;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

public class MovieResponse {
	
	private boolean success;
	private List<Movie> result;
	
	public List<Movie> getResult() {
		return this.result;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void setResult(List<Movie> result) {
		this.result = result;
	}
	
	
	
	
}
