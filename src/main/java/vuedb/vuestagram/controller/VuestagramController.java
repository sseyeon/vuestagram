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

    @GetMapping("/posts/{memberId}")
    public Post getPostByMember(@PathVariable Long memberId, @RequestParam Boolean isFollower) {
        return vuestagramService.readPost(memberId, isFollower);
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody PostCreationRequest request, @RequestParam Long memberId) {
        return vuestagramService.createPost(memberId, request);
    }

    @GetMapping("/members")
    public Long getMemberId(@RequestParam String userid, @RequestParam String password) {
        return vuestagramService.readMember(userid, password);
    }

    @PutMapping("/members/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody @NotNull MemberCreationRequest request) {
        return vuestagramService.updateMember(id, request);
    }

    @GetMapping("/posts/{id}/likes")
    public Long getPostLikes(@PathVariable Long id) {
        return vuestagramService.readPostLikes(id);
    }
    @PostMapping("/posts/{post_id}/likes/{member_id}")
    public PostLike createPostLike(@PathVariable Long post_id, @PathVariable Long member_id) {
        return vuestagramService.createPostLike(post_id, member_id);
    }
    @PostMapping("/posts/{post_id}/comments/{member_id}")
    public PostComment createPostComments(@PathVariable Long post_id, @PathVariable Long member_id, @RequestBody String comment) {
        return vuestagramService.createPostComments(post_id, member_id, comment);
    }
}
