package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training;


import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainingDiaryEntryDTO implements TrainingDiaryEntry, MutableTrainingDiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String session;
    private String feeling;
    private Boolean isShared;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "athleteEntries",  cascade = CascadeType.PERSIST)
    Set<AthleteDiaryDto> athleteDiaries;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof TrainingDiaryEntryDTO other){

            return this.getId() == other.getId();

        } else return false;

    }

    @Override
    public int hashCode() {

        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.id.hashCode();

        return hash;

    }
}
