package com.taskflow.repository;

import com.taskflow.domain.Team;
import com.taskflow.domain.TeamInvitation;
import com.taskflow.domain.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, UUID> {

    Optional<TeamInvitation> findByToken(String token);

    List<TeamInvitation> findByTeamAndStatus(Team team, InvitationStatus status);

    boolean existsByTeamAndInviteeEmailAndStatus(Team team, String email, InvitationStatus status);
}
