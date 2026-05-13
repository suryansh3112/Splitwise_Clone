package com.example.splitwise_clone.service;

import com.example.splitwise_clone.dto.AddMemberRequest;
import com.example.splitwise_clone.dto.CreateGroupRequest;
import com.example.splitwise_clone.dto.GroupResponse;
import com.example.splitwise_clone.dto.MessageResponse;
import com.example.splitwise_clone.entity.Group;
import com.example.splitwise_clone.entity.GroupMember;
import com.example.splitwise_clone.entity.User;
import com.example.splitwise_clone.exception.ForbiddenException;
import com.example.splitwise_clone.exception.MemberAlreadyExistsException;
import com.example.splitwise_clone.exception.ResourceNotFoundException;
import com.example.splitwise_clone.repository.GroupMemberRepository;
import com.example.splitwise_clone.repository.GroupRepository;
import com.example.splitwise_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public GroupResponse createGroup(CreateGroupRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));

        Group group = new Group();
        group.setName(request.getName());
        group.setCreatedBy(creator);

        Group savedGroup = groupRepository.save(group);

        GroupMember creatorMembership = new GroupMember();
        creatorMembership.setUser(creator);
        creatorMembership.setGroup(savedGroup);
        creatorMembership.setRole("ADMIN");

        groupMemberRepository.save(creatorMembership);

        return new GroupResponse(savedGroup.getId(), savedGroup.getName(), creator.getEmail());
    }

    public MessageResponse addMember(Long groupId, AddMemberRequest request, String requesterEmail) {
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        GroupMember requesterMembership = groupMemberRepository
                .findByUser_IdAndGroup_Id(requester.getId(), group.getId())
                .orElseThrow(() -> new ForbiddenException("You are not a member of this group"));

        if (!"ADMIN".equalsIgnoreCase(requesterMembership.getRole())) {
            throw new ForbiddenException("Only group admin can add members");
        }

        User newMember = userRepository.findByEmail(request.getMemberEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User to add not found"));

        if (groupMemberRepository.existsByUser_IdAndGroup_Id(newMember.getId(), group.getId())) {
            throw new MemberAlreadyExistsException("User is already a member of this group");
        }

        GroupMember membership = new GroupMember();
        membership.setUser(newMember);
        membership.setGroup(group);
        membership.setRole("MEMBER");

        groupMemberRepository.save(membership);

        return new MessageResponse("Member added successfully");
    }
}