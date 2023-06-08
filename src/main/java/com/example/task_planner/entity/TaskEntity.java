package com.example.task_planner.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
@NamedEntityGraph(
        name = "WithUserEntity",
        attributeNodes = {
                @NamedAttributeNode("userEntity")
        }
)
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@Table(name = "task")
@Entity
@Setter
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    private String message;

    private LocalDateTime date;
}
