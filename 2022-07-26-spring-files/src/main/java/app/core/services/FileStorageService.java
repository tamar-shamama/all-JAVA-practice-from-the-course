package app.core.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {
	
	@Value ("${file.upload-directory}")
	private String uploadDirectory;
	private Path fileStoragePath;
	
	
	
	@PostConstruct
	public void init() {
		
		// get the path to the directory
		this.fileStoragePath = Paths.get(this.uploadDirectory).toAbsolutePath().normalize();
		
		// create the directory if needed
		try {  
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/** a method that receives a Multipart file from the user by http, copying
	 * said file to a regular file format, and saves it by it's original name,
	 * in the path given from the server.
	 * @param file
	 * @return file name (String)
	 */
	public String storeFile (MultipartFile file) {
		
		// normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		// check for name validity
		if (fileName.contains("..")) {
			throw new RuntimeException("file name contains invalid characters: " + fileName);
		}
		
		// copy temporary file to a regular file in target location
		try {
			Path targetLocation = this.fileStoragePath.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//			Files.copy(file.getInputStream(), targetLocation);     throws exception if name exist
			return fileName;
		} catch (IOException e) {
			throw new RuntimeException("storeFile failed: " + fileName, e);
		}
	}
	
	
	
	
	/** Download file from server to user
	 * @param fileName
	 * @return Resource
	 */
	public Resource loadFileAsResource (String fileName) {
		
		try {
			// get path to the resource
			Path filePath = this.fileStoragePath.resolve(fileName).normalize();
			
			// create resource object from the path
			Resource resource = new UrlResource(filePath.toUri());
			
			// return the resource
			if (resource.exists()) {
				return resource;
			} else {
				throw new RuntimeException("file not found");
			}
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("file not found", e);
		}
	}
	
	
	
	/** delete file from the server
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		
		// get the path to the file
		Path filePath = this.fileStoragePath.resolve(fileName).normalize();
		
		// get the file
		File file = filePath.toFile();
		
		// delete the file
		if (file.exists()) {
			return file.delete();
		} else {
			throw new RuntimeException("deleteFile failed - not found: " + fileName);
		}
	}

	
	
	
	/** change the file name in the hard disc
	 * @param oldFileName
	 * @param newFileName
	 * @return {@code true} if name was changed successfully 
	 */
	public boolean renameFile(String oldFileName, String newFileName) {
		
		Path oldFilePath = this.fileStoragePath.resolve(oldFileName).normalize();
		File oldFile = oldFilePath.toFile();
		
		if (oldFile.exists()) {
			Path newFilePath = this.fileStoragePath.resolve(newFileName).normalize();
			return oldFile.renameTo(newFilePath.toFile());
		} else {
			throw new RuntimeException("renameFile failed - not found: " + oldFileName);
		}
	}

	
	
	/**
	 * @return a list of all of the files' names
	 */
	public String[] listFileNames() {
		return fileStoragePath.toFile().list();
	}

	
	
	
	/**
	 *  delete all of the files in the directory
	 */
	public void deleteAllFiles() {
		
		File directory = this.fileStoragePath.toFile();
		
		for (File file : directory.listFiles()) {
			file.delete();
		}

	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
