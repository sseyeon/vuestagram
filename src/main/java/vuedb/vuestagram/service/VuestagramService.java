package vuedb.vuestagram.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vuedb.vuestagram.model.Member;
import vuedb.vuestagram.model.Post;
import vuedb.vuestagram.model.PostComment;
import vuedb.vuestagram.model.PostLike;
import vuedb.vuestagram.model.request.MemberCreationRequest;
import vuedb.vuestagram.model.request.PostCreationRequest;
import vuedb.vuestagram.repository.MemberRepository;
import vuedb.vuestagram.repository.PostCommentRepository;
import vuedb.vuestagram.repository.PostLikeRepository;
import vuedb.vuestagram.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VuestagramService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    // readPost(Long id) : ID를 기준으로 DB의 포스팅 조회
    public Post readPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        }
        throw new EntityNotFoundException("Can't find any POST under given ID");
    }

    // readPosts() : DB에 저장된 모든 포스팅을 읽어들임
    public List<Post> readPosts() {
        return postRepository.findAll();
    }

    // readPost(member_id, is_follower) : member_id 를 기준으로 DB의 포스팅 조회
    public Post readPost(Long member_id, Boolean is_follower){
        if (is_follower == Boolean.FALSE) { // readPost(member_id, is_follower=False) : member_id 를 기준으로 DB의 포스팅 조회
            Optional<Post> post = postRepository.findByMemberId(member_id);
            if (post.isPresent()) {
                return post.get();
            }
        } else {    // readPost(member_id, is_follower=True) : following_id 를 기준으로 DB의 포스팅 조회
            Optional<Post> post = postRepository.findByMemberId(member_id);
            if (post.isPresent()) {
                return post.get();
            }
        }
        throw new EntityNotFoundException("Can't find any POST under given MEMBER");
    }

    // createPost(Post) : PostCreationRequest로 포스팅을 생성
    public Post createPost(Long member_id, PostCreationRequest request) {
        // member_id를 사용하여 Member 엔터티를 조회하고 존재하지 않을 경우 EntityNotFoundException 발생
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + member_id));
        // Post 엔터티 생성 및 request에서 필드 값을 복사
        Post newPost = new Post();
        BeanUtils.copyProperties(request, newPost);

        // Member와 Post 간의 관계 설정
        newPost.setMember(member);

        // Post 엔터티를 저장하여 새 포스팅을 생성
        return postRepository.save(newPost);
    }
    // deletePost(id) : ID를 기준으로 포스팅 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    // createMember(Member) : MemberCreationRequest로 회원을 생성
    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        return memberRepository.save(member);
    }

    // readMember(userid, password) : userid와 password를 사용하여 멤버를 조회하고 해당 멤버의 ID 값을 반환
    public Long readMember(String userid) {
        // 주어진 userid를 사용하여 멤버를 조회
        Member member = memberRepository.findByUserid(userid);

        if (member != null) {
            // 멤버가 존재하면 해당 멤버의 id(PK)를 반환
            return member.getId();
        } else {
            // 멤버가 존재하지 않으면 null을 반환하거나 예외 처리를 수행할 수 있음
            return null;
        }
    }

    // updateMember(id, Member) : id, MemberCreationRequest로 id에 해당하는 회원을 MemberCreationRequest로 변경함
    public Member updateMember(Long id, @NotNull MemberCreationRequest request) {
        // member_id를 사용하여 Member 엔터티를 조회하고 존재하지 않을 경우 EntityNotFoundException 발생
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + id));

        // MemberCreationRequest에서 필드 값을 업데이트
        existingMember.setUserid(request.getUserid());
        existingMember.setName(request.getName());
        existingMember.setUser_image(request.getUser_image());
        existingMember.setPassword(request.getPassword());

        // Member 엔터티를 저장하여 회원 정보를 업데이트
        return memberRepository.save(existingMember);
    }


//    readPostLike(Long id) : ID를 기준으로 포스팅의 좋아요 갯수 조회
    public Long readPostLikes(Long id){
        // 주어진 postId에 해당하는 포스트 좋아요 데이터의 개수를 계산
        return postLikeRepository.countByPost_id(id);
    }

    // createPostLike(post_id, member_id) : ID를 기준으로 포스팅에 좋아요 추가
    public PostLike createPostLike(Long post_id, Long member_id) {
        // 주어진 post_id에 해당하는 포스트를 조회
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + post_id));

        // 주어진 member_id에 해당하는 멤버를 조회
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + member_id));

        // 새로운 포스트 좋아요 데이터 생성
        PostLike newPostLike = new PostLike();
        newPostLike.setPost(post);
        newPostLike.setMember(member);

        // 포스트 좋아요 데이터를 저장하여 생성
        return postLikeRepository.save(newPostLike);
    }

    // deletePostLike(post_id, member_id) : ID를 기준으로 포스팅에 좋아요 삭제
    // [수정] deletePostLike(id) : ID를 기준으로 포스팅에 좋아요 삭제
    public void deletePostLike(Long post_like_id) {
        postLikeRepository.deleteById(post_like_id);
//        // 주어진 post_id와 member_id에 해당하는 포스트 좋아요를 조회
//        PostLike postLike = postLikeRepository.findByPost_idAndMember_id(post_id, member_id);
//        if (postLike != null) {
//            Long id = postLike.getId();
//            postLikeRepository.deleteById(id);
//        }
    }

    // readPostComments(Long id) : ID를 기준으로 포스팅의 댓글 조회
    public PostComment readPostComments(Long id) {
        Optional<PostComment> postComment = postCommentRepository.findById(id);
        if (postComment.isPresent()) {
            return postComment.get();
        }
        throw new EntityNotFoundException("Can't find any POST-COMMENTS under given ID");
    }

    // createPostComments(post_id, member_id, comment) : ID를 기준으로 포스팅에 댓글 추가
    public PostComment createPostComments(Long post_id, Long member_id, String comment) {
        // 주어진 post_id에 해당하는 포스트를 조회
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + post_id));

        // 주어진 member_id에 해당하는 멤버를 조회
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + member_id));

        // 새로운 포스트 댓글 데이터 생성
        PostComment newComment = new PostComment();
        newComment.setContent(comment);
        newComment.setPost(post);

        // 댓글 작성자(Member)와 댓글(PostComment) 간의 관계 설정
        newComment.setMember(member);

        // 포스트 댓글 데이터를 저장하여 생성
        return postCommentRepository.save(newComment);
    }

    // deletePostComments(id) : ID를 기준으로 포스팅에 댓글 삭제
    public void deletePostComments(Long id) {
        postCommentRepository.deleteById(id);
    }
}
