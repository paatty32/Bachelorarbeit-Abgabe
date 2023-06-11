package de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.Diary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.repository.AthleteDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlan;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository.AppUserRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.AppUserRole;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final TrainingsDiaryRepository trainingsDiaryRepository;

    private final CompetitionDiaryRepository competitionDiaryRepository;

    private final AthleteDiaryRepository athleteDiaryRepository;

    private final TrainingPlanRepository trainingPlanRepository;

    @Override
    public AppUserDTO createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception {

        Set<AppUserRole> appUserRoles = this.initializeUserRoles(clickedRoles);

        createPerson.setRoles(appUserRoles);

        AppUserDTO personDTOToCreate = (AppUserDTO) createPerson;


        if(this.getAppUserRepository().findPersonByEmail(personDTOToCreate.getEmail()) == null) {
            AppUserDTO createdPersonDTO = this.getAppUserRepository().save(personDTOToCreate);

            this.initializeUserDiares(createdPersonDTO, clickedRoles);

            return createdPersonDTO;

        } else throw new Exception();

    }

    @Override
    public void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup) {

        AppUserDTO personDTOById = this.getAppUserRepository().findPersonById(userId);

        personDTOById.getTrainingsGroup().add((GroupDTO) newTrainingGroup);

        this.getAppUserRepository().save(personDTOById);

    }

    @Override
    public Set<Group> getUserGroups(Long userId) {

        Set<Group> userGroups = new HashSet<>();

        AppUserDTO personDTOById = this.getAppUserRepository().findPersonById(userId);
        Set<GroupDTO> userGroupsBuffer = personDTOById.getTrainingsGroup();

        userGroups.addAll(userGroupsBuffer);

        return userGroups;
    }

    @Override
    public Set<AppUser> getUserTrainer(Long userId) {

        Set<AppUser> trainer = new HashSet<>();

        Set<AppUser> trainerBuffer = new HashSet<>();

        AppUserDTO personById = this.getAppUserRepository().findPersonById(userId);

        Set<GroupDTO> trainingsGroup = personById.getTrainingsGroup();

        for (GroupDTO groups: trainingsGroup) {

            Long adminId = groups.getAdminId();
            AppUserDTO trainerById = this.getAppUserRepository().findPersonById(adminId);

            trainerBuffer.add(trainerById);

        }

        trainer.addAll(trainerBuffer);

        return trainer;
    }

    private Map<DiaryType, Diary> initializeUserDiares(AppUserDTO createdPersonDTO, Set<String> clickedRoles) {

        Map<DiaryType, Diary> personDiaries = new HashMap<>();

        Long createdPersonId = createdPersonDTO.getId();

        DiaryId trainingPlanDiaryId = DiaryId.builder()
                .userId(createdPersonId)
                .diaryType(DiaryType.TRAININGPLAN)
                .build();

        TrainingPlan trainingPlanDiary = new TrainingPlanDTO(trainingPlanDiaryId);
        this.getTrainingPlanRepository().save((TrainingPlanDTO) trainingPlanDiary);

        for (String role: clickedRoles) {

            switch (role){

                case "Athlet":

                    DiaryId trainingDiaryId = DiaryId.builder()
                            .userId(createdPersonId)
                            .diaryType(DiaryType.TRAINING)
                            .build();

                    DiaryId competitionDiaryId = DiaryId.builder()
                            .userId(createdPersonId)
                            .diaryType(DiaryType.COMPETITION)
                            .build();

                    TrainingDiaryDto trainingDiary = new TrainingDiaryDto(trainingDiaryId);
                    this.getTrainingsDiaryRepository().save(trainingDiary);

                    CompetitionDiaryDto competitionDiary = new CompetitionDiaryDto(competitionDiaryId);
                    this.getCompetitionDiaryRepository().save(competitionDiary);

                    break;

                case "Trainer":
                    break;


                case "Eltern":
                    //TODO
                    break;
            }

        }

        return personDiaries;
    }

    private Set<AppUserRole> initializeUserRoles(Set<String> clickedRoles) {

        Set<AppUserRole> roles = new LinkedHashSet<>();

        for (String role: clickedRoles){

            switch (role){
                case "Athlet":
                    roles.add(AppUserRole.ROLE_ATHLETE);
                    break;

                case "Trainer":
                    roles.add(AppUserRole.ROLE_TRAINER);
                    break;

                case "Eltern":
                    //TODO
                    break;
            }
        }
        return roles;
    }
}
