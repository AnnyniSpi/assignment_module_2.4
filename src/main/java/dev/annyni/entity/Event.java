package dev.annyni.entity;

import jakarta.persistence.*;
import lombok.*;

@NamedEntityGraph(
        name = "graph.eventUserAndFile",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("file")}
)
@ToString(exclude = {"user","file"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;
}
