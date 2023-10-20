package vuedb.vuestagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuedb.vuestagram.model.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
