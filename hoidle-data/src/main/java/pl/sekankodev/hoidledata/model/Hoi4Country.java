package pl.sekankodev.hoidledata.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Hoi4Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Ideology ideology;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Continent> continents;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Faction> historicalFactions;

    @ElementCollection
    private List<String> formableNations;

    private byte researchSlotsNumber;
    private boolean nationalFocusTree;
    private boolean accessToTheSea;
    private boolean researchedTrain;
}
