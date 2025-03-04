package com.geonlee.redis.example.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "redis_example")
@Entity
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public static Example of(String name, String description) {
        return Example.builder()
                .name(name)
                .description(description)
                .build();
    }

    public void update(String name, String description) {
        this.name=name;
        this.description=description;
    }

}
