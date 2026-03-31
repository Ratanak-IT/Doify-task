package com.taskflow.repository;

import com.taskflow.domain.Team;
import com.taskflow.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    Page<Team> findByOwner(User owner, Pageable pageable);

    @Query("""
            SELECT t FROM Team t
            JOIN t.members m
            WHERE m.user = :user
            """)
    List<Team> findAllByMember(User user);

    @Query("""
            SELECT t FROM Team t
            JOIN t.members m
            WHERE m.user = :user
            """)
    Page<Team> findAllByMember(User user, Pageable pageable);
}
