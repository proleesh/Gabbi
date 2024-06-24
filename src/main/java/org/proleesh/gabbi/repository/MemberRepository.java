package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 사용자 찾기
    Member findByEmail(String email);
}
