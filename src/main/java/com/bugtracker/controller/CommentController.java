package com.bugtracker.controller;

import com.bugtracker.model.Comment;
import com.bugtracker.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{issueId}/comments")
    @PreAuthorize("isAuthenticated()")
    public Comment addComment(
            @PathVariable Long issueId,
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        String content = request.get("content");
        return commentService.addComment(
                issueId,
                content,
                authentication.getName()
        );
    }

    @GetMapping("/{issueId}/comments")
    @PreAuthorize("isAuthenticated()")
    public List<Comment> getComments(
            @PathVariable Long issueId) {

        return commentService.getComments(issueId);
    }
}
