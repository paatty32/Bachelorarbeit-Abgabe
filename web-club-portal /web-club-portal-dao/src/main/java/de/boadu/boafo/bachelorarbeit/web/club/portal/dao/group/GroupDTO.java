package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GroupDTO implements Group, MutableGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String trainer;
    private Long adminId;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<GroupRequestsDTO> requests;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<TrainingPlanEntryDTO> trainingPlanEntry;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;


    public void removeEntry(TrainingPlanEntryDTO entry){

        this.getTrainingPlanEntry().remove(entry);

        entry.getGroups().remove(this);

    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof GroupDTO other){

            return (this.getId().equals(other.getId()));

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
