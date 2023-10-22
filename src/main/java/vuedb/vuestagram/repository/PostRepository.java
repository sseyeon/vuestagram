package vuedb.vuestagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuedb.vuestagram.model.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByMemberId(Long member_id);

}
