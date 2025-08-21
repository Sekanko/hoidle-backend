package models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class Hoi4Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Continent> continents;

    @Enumerated(EnumType.STRING)
    private Ideology ideology;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Faction> historicalFactions;

    private int stability;

    private boolean nationalFocusTree;
    private boolean accessToTheSea;
    private boolean trainResearched;

    @ElementCollection
    private List<String> formableNations;
    int civilianFactories;
    private String url;
}
