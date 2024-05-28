package dev.annyni.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NamedEntityGraph(
        name = "graph.userEvents",
        attributeNodes = @NamedAttributeNode("events")
)
//@ToString(exclude = {"events"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> events;
}
