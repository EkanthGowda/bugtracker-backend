package com.bugtracker.config;

import com.bugtracker.model.*;
import com.bugtracker.repository.IssueRepository;
import com.bugtracker.repository.ProjectRepository;
import com.bugtracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner seedDefaultUser(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            IssueRepository issueRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "koushik@gmail.com";
            User user = null;
            if (userRepository.findByEmail(email).isEmpty()) {
                user = User.builder()
                        .name("Koushik")
                        .email(email)
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.DEVELOPER)
                        .build();
                user = userRepository.save(user);
            } else {
                user = userRepository.findByEmail(email).get();
            }

            // Seed test project
            if (projectRepository.findById(1L).isEmpty()) {
                Project project = Project.builder()
                        .id(1L)
                        .name("Test Project")
                        .description("A test project for Kanban")
                        .build();
                projectRepository.save(project);

                // Seed test issues
                Issue issue1 = Issue.builder()
                        .title("Design homepage")
                        .description("Create wireframes and mockups")
                        .status(IssueStatus.TODO)
                        .priority(IssuePriority.HIGH)
                        .project(project)
                        .assignee(user)
                        .build();

                Issue issue2 = Issue.builder()
                        .title("Setup database")
                        .description("Configure PostgreSQL")
                        .status(IssueStatus.IN_PROGRESS)
                        .priority(IssuePriority.MEDIUM)
                        .project(project)
                        .assignee(user)
                        .build();

                Issue issue3 = Issue.builder()
                        .title("Write API docs")
                        .description("Document all endpoints")
                        .status(IssueStatus.DONE)
                        .priority(IssuePriority.LOW)
                        .project(project)
                        .assignee(user)
                        .build();

                issueRepository.saveAll(java.util.List.of(issue1, issue2, issue3));
            }
        };
    }
}
