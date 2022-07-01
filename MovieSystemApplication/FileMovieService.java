package com.ned.MovieSystemApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fileService")
@Scope("singleton") //We dont need another FileMovieService Object
public class FileMovieService implements IMovieService{
	
	File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedWriter bufferWrite;
	private BufferedReader bufferRead;
	
	// Creating file
	public FileMovieService() {
		
		try {
			file = new File("movies.txt");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// Write to file as CSV
	public void writeToFile(Movie movie) {
		
		try {
			fileWriter = new FileWriter(file,true);
            bufferWrite = new BufferedWriter(fileWriter);
			
			bufferWrite.write(movie.getImdbID() + "," + movie.getTitle() + "," + movie.getType() + "," + movie.getYear());
			bufferWrite.newLine();
			bufferWrite.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error !");
		}
	}
	
	@Override
	public List<Movie> search(String movieName) {
		// TODO Auto-generated method stub
		
		// In homework we dont need fill this function in this class because we dont use
		return null;
	}
	
	// Read from file if it exist and convert to Movie object
	@Override
	public Movie findById(String id) {
		// TODO Auto-generated method stub
		Movie movie=null;
		
		try {
			fileReader = new FileReader(file);
            bufferRead = new BufferedReader(fileReader);
            
            String line = bufferRead.readLine();
            while(line!=null) {
            	
            	List<String> movieAttributes = Arrays.asList(line.split(","));
            	if(movieAttributes.get(0).equals(id)) {
            		movie.setImdbID(id);
            		movie.setTitle(movieAttributes.get(1));
            		movie.setType(movieAttributes.get(2));
            		movie.setYear(movieAttributes.get(3));
            		bufferRead.close();
            		return movie;
            	}
            	line = bufferRead.readLine();
            }
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		return movie;
	}
}
