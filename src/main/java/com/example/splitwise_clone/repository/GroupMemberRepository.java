package com.example.splitwise_clone.repository;

import com.example.splitwise_clone.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findByUser_Id(Long userId);

    List<GroupMember> findByGroup_Id(Long groupId);

    Optional<GroupMember> findByUser_IdAndGroup_Id(Long userId, Long groupId);

    boolean existsByUser_IdAndGroup_Id(Long userId, Long groupId);
}