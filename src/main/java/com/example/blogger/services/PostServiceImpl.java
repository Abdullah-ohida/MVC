package com.example.blogger.services;

import com.cloudinary.utils.ObjectUtils;
import com.example.blogger.data.model.Comment;
import com.example.blogger.data.model.Post;
import com.example.blogger.data.repositories.PostRepository;
import com.example.blogger.services.config.CloudinaryService;
import com.example.blogger.web.dtos.PostDto;
import com.example.blogger.web.exceptions.CommentNullException;
import com.example.blogger.web.exceptions.PostDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    CloudinaryService cloudinaryService;


    @Override
    public Post createPost(PostDto postDto) {
        if(postDto == null) throw new NullPointerException("Post cannot be null");

        Post post = new Post();

        if(postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()){
            Map<?, ?> uploadImageResult = null;
            try {
                uploadImageResult = cloudinaryService.uploadImage(postDto.getImageFile(), ObjectUtils.asMap(
                        "public_id", "blogger" + extractFileName(postDto.getImageFile().getName())
                ));
                post.setPostImage(String.valueOf(uploadImageResult.get("url")));
                log.info("Image url --> {}", uploadImageResult.get("url"));

            } catch (IOException e) {
                log.info("Error occurred while uploading image --> {}", e.getMessage());
            }
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

         return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return  postRepository.findByOrderByDatePublishedDesc();
    }

    @Override
    public List<Post> findAllPostByAuthorId(Long id) throws PostDoesNotExistException {
       List<Post> posts = getPosts(id);
       validateAuthorPosts(id, posts);
       return posts;
    }

    @Override
    public void deleteAllPostByAuthorId(Long id) throws PostDoesNotExistException {
        List<Post> posts = getPosts(id);
        validateAuthorPosts(id, posts);
        findAllPostByAuthorIdAndDelete(posts);
    }

    private void validateAuthorPosts(Long id, List<Post> posts) throws PostDoesNotExistException {
        if (posts.isEmpty())
            throw new PostDoesNotExistException(String.format("No associated posts with author-id %d ", id));
    }

    private List<Post> getPosts(Long id) {
        return postRepository.findPostByAuthorId(id);
    }

    private void findAllPostByAuthorIdAndDelete(List<Post> posts) {
        postRepository.deleteAll(posts);
    }

    @Override
    public Post updatePost(Long id, PostDto postDtoToUpdate) throws PostDoesNotExistException {
        Post existingPost = findPostById(id);
        if (!existingPost.getContent().equals(postDtoToUpdate.getContent())){
            existingPost.setContent(postDtoToUpdate.getContent());
        }

        if (!existingPost.getTitle().equals(postDtoToUpdate.getTitle())){
            existingPost.setTitle(postDtoToUpdate.getTitle());
        }
        return postRepository.save(existingPost);
    }

    @Override
    public Post findPostById(Long id) throws PostDoesNotExistException {
        if(id == null) throw new NullPointerException("Id cannot be null");

        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent())
            return post.get();
        else throw new PostDoesNotExistException(String.format("Post with id %d does not exist", id));
    }

    @Override
    public void deletePostById(Long id) {
        if(id == null) throw new NullPointerException("Id cannot be null");
        postRepository.deleteById(id);
    }

    @Override
    public Post addCommentToPost(Long id, Comment comment) throws PostDoesNotExistException, CommentNullException {
        Post existingPost = findPostById(id);

        if(comment == null) throw new CommentNullException("Comment cannot be null exception");

        existingPost.addComment(comment);

        return postRepository.save(existingPost);
    }


    private String extractFileName(String filename){
        return filename.split("\\.")[0];
    }
}
