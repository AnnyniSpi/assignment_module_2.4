package dev.annyni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedEntityGraph(
        name = "graph.eventUserAndFile",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("file")}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;
}
