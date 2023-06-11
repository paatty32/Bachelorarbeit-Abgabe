package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupDTO;
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
public class TrainingPlanEntryDTO implements TrainingPlanEntry, MutableTrainingPlanEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String session;
    private LocalDate date;
    private String athlete;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @ManyToMany(mappedBy = "trainingPlanEntry")
    Set<GroupDTO> groups;

    public void removeGroup(GroupDTO group){

        this.getGroups().remove(group);

        group.removeEntry(this);

    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof TrainingPlanEntryDTO other){

            return this.getId() == other.getId();

        }

        return false;
    }

    @Override
    public int hashCode(){
        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.id.hashCode();

        return hash;
    }
}
