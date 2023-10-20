package vuedb.vuestagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuedb.vuestagram.model.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Long countByPost_id(Long postId);
}
