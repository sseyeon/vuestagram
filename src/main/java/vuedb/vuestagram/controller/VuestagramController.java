package vuedb.vuestagram.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vuedb.vuestagram.model.Member;
import vuedb.vuestagram.model.Post;
import vuedb.vuestagram.model.PostComment;
import vuedb.vuestagram.model.PostLike;
import vuedb.vuestagram.model.request.MemberCreationRequest;
import vuedb.vuestagram.model.request.PostCreationRequest;
import vuedb.vuestagram.service.VuestagramService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/vuestagram")
public class VuestagramController {
    private final VuestagramService vuestagramService;

    @Autowired
    public VuestagramController(VuestagramService vuestagramService) {
        this.vuestagramService = vuestagramService;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return vuestagramService.readPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return vuestagramService.readPost(id);
    }

    @GetMapping("/posts/{type}/{id}")
    public Post getPostByMember(@PathVariable("type") String type, @PathVariable("id") Long id) {
        if (type.equals("member")) {
            return vuestagramService.readPost(id, Boolean.FALSE);
        } else {
            return vuestagramService.readPost(id, Boolean.TRUE);

        }
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody PostCreationRequest request, @RequestParam Long memberId) {
        return vuestagramService.createPost(memberId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId) {
        vuestagramService.deletePost(postId);
        return "OK";
    }

    @GetMapping("/members")
    public Long getMemberId(@RequestParam String userid) {
        return vuestagramService.readMember(userid);
    }

    @PutMapping("/members/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody @NotNull MemberCreationRequest request) {
        return vuestagramService.updateMember(id, request);
    }

    @PostMapping("/members")
    public Member createMember(@RequestBody MemberCreationRequest request) {
        return vuestagramService.createMember(request);
    }
    @GetMapping("/posts-likes/{id}")
    public Long getPostLikes(@PathVariable Long id) {
        return vuestagramService.readPostLikes(id);
    }
    @PostMapping("/post-likes")
    public PostLike createPostLike(@PathVariable("post_id") Long post_id, @PathVariable("member_id") Long member_id) {
        return vuestagramService.createPostLike(post_id, member_id);
    }

    @DeleteMapping("/post-likes/{id}")
    public String deletePostLike(@PathVariable Long post_like_id) {
        vuestagramService.deletePostLike(post_like_id);
        return "OK";
    }

    @GetMapping("/post-comments/{post_id}")
    public PostComment getPostComments(@PathVariable Long post_id) {
        return vuestagramService.readPostComments(post_id);
    }

    @PostMapping("/post-comments")
    public PostComment createPostComments(@PathVariable Long post_id, @PathVariable Long member_id, @RequestBody String comment) {
        return vuestagramService.createPostComments(post_id, member_id, comment);
    }

    @DeleteMapping("/post-comments/{pc_id}")
    public String deletePostComments(@PathVariable Long pc_id) {
        vuestagramService.deletePostComments(pc_id);
        return "OK";
    }

}
