package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Comment;
import app.core.entities.Post;
import app.core.exceptions.PostSystemException;
import app.core.services.AppService;

@RestController
@RequestMapping("/api")
public class AppController {
	
	@Autowired
	private AppService appService;
	
	// POST
	
	// http://localhost:8080/api
	@PostMapping
	public int addPost (@RequestBody Post post) throws PostSystemException {
		return appService.addPost(post);
	}
	
	// http://localhost:8080/api/{postId}
	@PostMapping("/{postId}")
	public void addComment (@RequestBody Comment comment, @PathVariable int postId) throws PostSystemException {
		appService.addCommentToPost(comment, postId);
	}
	
	
	// GET
	
	// http://localhost:8080/api
	@GetMapping
	public List<Post> getAllPosts() {
		return appService.getAllPosts();
	}
	
	
	// DELETE
	
	// http://localhost:8080/api/post/2
		@DeleteMapping("/{postId}")
		public void deletePost(@PathVariable int postId) throws PostSystemException {
			this.appService.deletePost(postId);
		}
		
	// http://localhost:8080/api/comment?commentId=2
	@DeleteMapping
	public void deleteComment(@RequestParam int commentId) throws PostSystemException {
		this.appService.deleteComment(commentId);
	}
	
	
	// PUT (update)

}
