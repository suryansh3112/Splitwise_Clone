package com.example.splitwise_clone.controller;

import com.example.splitwise_clone.dto.AddMemberRequest;
import com.example.splitwise_clone.dto.CreateGroupRequest;
import com.example.splitwise_clone.dto.GroupResponse;
import com.example.splitwise_clone.dto.MessageResponse;
import com.example.splitwise_clone.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GroupResponse createGroup(
            @Valid @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return groupService.createGroup(request, jwt.getSubject());
    }

    @PostMapping("/{groupId}/members")
    public MessageResponse addMember(
            @PathVariable Long groupId,
            @Valid @RequestBody AddMemberRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return groupService.addMember(groupId, request, jwt.getSubject());
    }
}