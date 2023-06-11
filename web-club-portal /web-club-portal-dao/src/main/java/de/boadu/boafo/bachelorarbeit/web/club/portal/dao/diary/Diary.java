package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Diary {

    @EmbeddedId
    private DiaryId id;

}
