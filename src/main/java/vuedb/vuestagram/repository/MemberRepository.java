package vuedb.vuestagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuedb.vuestagram.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserid(String userid);
}
