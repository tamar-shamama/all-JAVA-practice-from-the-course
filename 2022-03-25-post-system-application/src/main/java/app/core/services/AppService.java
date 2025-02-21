package app.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Comment;
import app.core.entities.Post;
import app.core.exceptions.PostSystemException;
import app.core.repositories.CommentRepository;
import app.core.repositories.PostRepository;

@Service
@Transactional
public class AppService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@Value("${post.length.title.min}")
	private int postTitleMinLength;
	@Value("${post.length.title.max}")
	private int postTitleMaxLength;

	public int addPost(Post post) throws PostSystemException {
		// check that this post is new
		if (post.getId() != 0 && this.postRepository.existsById(post.getId())) {
			throw new PostSystemException("addPost failed - post already exists");
		}
		// check that title length is of the right length
		if (post.getTitle().length() < this.postTitleMinLength) {
			throw new PostSystemException("addPost failed - title too short");
		}
		if (post.getTitle().length() > this.postTitleMaxLength) {
			throw new PostSystemException("addPost failed - title too long");
		}
		post = this.postRepository.save(post);
		return post.getId();
	}
	

	/**
	 * @param comment
	 * @param postId
	 * @return 
	 * @throws PostSystemException if post not exist
	 */
	public void addCommentToPost(Comment comment, int postId) throws PostSystemException {
		
		Optional<Post> optional = this.postRepository.findById(postId);
		if (optional.isEmpty()) {
			throw new PostSystemException("addComment failed - post not exist");
			
		} else {
			Post post = optional.get();
			post.addComment(comment);
		}
	}

	public List<Post> getAllPosts() {
		return this.postRepository.findAll();
	}

	public List<Comment> getAllComments() {
		return this.commentRepository.findAll();
	}

	public List<Comment> getAllCommentsOfPost(int postId) {
		return this.commentRepository.findByPostId(postId);
	}

	public void updatePost(Post post) throws PostSystemException {
		
		Optional<Post> opt = this.postRepository.findById(post.getId());
		
		if (opt.isPresent()) {
			Post postFromDb = opt.get();
			postFromDb.setText(post.getText());
			postFromDb.setTitle(post.getTitle());
			
		} else {
			throw new PostSystemException("updatePost failed - post not exist");
		}
	}

	public void updateComment(Comment comment) throws PostSystemException {
		if (this.commentRepository.existsById(comment.getId())) {
			this.commentRepository.save(comment);
		} else {
			throw new PostSystemException("updateComment failed - comment not exist");
		}
	}

	public void deletePost(int postId) throws PostSystemException {
		if (this.postRepository.existsById(postId)) {
			this.postRepository.deleteById(postId);
		} else {
			throw new PostSystemException("deletePost failed - post not exist");
		}
	}

	public void deleteComment(int commentId) throws PostSystemException {
		if (this.commentRepository.existsById(commentId)) {
			this.commentRepository.deleteById(commentId);
		} else {
			throw new PostSystemException("deleteComment failed - comment not exist");
		}
	}

}
