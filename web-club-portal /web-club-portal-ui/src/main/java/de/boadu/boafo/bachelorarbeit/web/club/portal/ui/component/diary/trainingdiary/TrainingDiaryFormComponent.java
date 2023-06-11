package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingDiaryFormComponent extends AbstractComponent implements AbstractObserver<TrainingsDairyFormEventListener> {

    private DatePicker tfDate;
    private TextArea taSession;
    private TextArea taFeeling;

    private Button btnUpdate;
    private Button btnDelete;
    private Button btnShare;
    private Button btnClose;

    private VerticalLayout formLayout;
    private HorizontalLayout buttonLayout;

    private VerticalLayout componentRootLayout;

    private Long currentEntryId;

    private Set<TrainingsDairyFormEventListener> eventListeners;

    Set<AthleteDiaryDto> athleteDiary;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.eventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeFormLayout();
        this.initializeButtonLayout();
        this.initializeRootLayout();

    }

    private void initializeFormLayout(){

        this.formLayout = new VerticalLayout();

        this.tfDate = new DatePicker();
        this.tfDate.setLabel("Datum");

        this.taSession = new TextArea();
        this.taSession.setLabel("Einheit");
        this.taSession.setMaxHeight("150px");
        this.taSession.setHeight("150px");
        this.taSession.setWidth("480px");

        this.taFeeling = new TextArea();
        this.taFeeling.setLabel("Gefühlszustand");
        this.taFeeling.setMaxHeight("150px");
        this.taFeeling.setHeight("150px");
        this.taFeeling.setWidth("480px");

        this.getFormLayout().add(this.getTfDate());
        this.getFormLayout().add(this.getTaSession());
        this.getFormLayout().add(this.getTaFeeling());
        this.getFormLayout().setAlignSelf(FlexComponent.Alignment.START, this.getTfDate());
        this.getFormLayout().setAlignSelf(FlexComponent.Alignment.STRETCH, this.getTaSession());
        this.getFormLayout().setAlignSelf(FlexComponent.Alignment.STRETCH, this.getTaFeeling());

    }

    private void initializeButtonLayout(){

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setSizeFull();

        this.btnUpdate = new Button();
        this.btnUpdate.setText("Übernehmen");
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        this.btnDelete = new Button();
        this.btnDelete.setText("Löschen");
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.btnShare = new Button();
        this.btnShare.setIcon(VaadinIcon.SHARE.create());

        this.btnClose = new Button();
        this.btnClose.setText("Schließen");
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.getButtonLayout().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.getButtonLayout().add(this.getBtnUpdate());
        this.getButtonLayout().add(this.getBtnDelete());
        this.getButtonLayout().add(this.getBtnShare());
        this.getButtonLayout().add(this.getBtnClose());
    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("35%");
        this.componentRootLayout.setHeightFull();

        this.getComponentRootLayout().add(this.getFormLayout());
        this.getComponentRootLayout().add(this.getButtonLayout());
        this.getComponentRootLayout().setVisible(false);

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnClose().addClickListener(doOnClickCloseButton());

        this.getBtnUpdate().addClickListener(doOnClickUpdate());

        this.getBtnShare().addClickListener(clickEvent -> {

            this.notifyShareClickedEventListener();

        });

        this.getBtnDelete().addClickListener(doOnClickDelete());

    }



    private ComponentEventListener<ClickEvent<Button>> doOnClickDelete() {
        return clickEvent -> {

            Long clickedEntryId = this.getCurrentEntryId();

            TrainingsDiaryDeleteEntryEventRequest event = new TrainingsDiaryDeleteEntryEventRequestImpl(clickedEntryId);

            this.notifyDeleteClickedEventListener(event);

            this.clearForm();

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickUpdate() {
        return clickEvent -> {

            Long currentEntryIdLongValue = this.getCurrentEntryId();

            String session = this.getTaSession().getValue();

            LocalDate date = this.getTfDate().getValue();

            String feeling = this.getTaFeeling().getValue();

            TrainingDiaryEntryDTO newTrainingDiaryEntry = TrainingDiaryEntryDTO.builder()
                    .id(currentEntryIdLongValue)
                    .session(session)
                    .feeling(feeling)
                    .date(date)
                    .athleteDiaries(this.getAthleteDiary())
                    .build();

            TrainingsDiaryFormEventRequest event = new TrainingsDiaryFormEventRequestImpl(newTrainingDiaryEntry);

            this.notifyUpdateClickedEventListener(event);

        };
    }

    private void notifyDeleteClickedEventListener(TrainingsDiaryDeleteEntryEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleButtonDelete(event));

    }

    private void notifyUpdateClickedEventListener(TrainingsDiaryFormEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleButtonUpdate(event));

    }

    private void notifyShareClickedEventListener() {
        this.getEventListeners().forEach(listener -> listener.handleButtonShare());
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickCloseButton() {
        return event -> {

            this.clearForm();

            this.getComponentRootLayout().setVisible(false);
        };
    }

    @Override
    public void addEventListeners(TrainingsDairyFormEventListener listener) {

        this.getEventListeners().add(listener);

    }

    public void setForm(Long id, String session, String feeling, LocalDate date, Set<AthleteDiaryDto> athleteDiaries) {

        this.currentEntryId = id;

        this.getTaFeeling().setValue(feeling);

        this.getTaSession().setValue(session);

        this.getTfDate().setValue(date);

        this.athleteDiary = athleteDiaries;

    }

    public void clearForm(){

        this.currentEntryId = null;

        this.getTaFeeling().clear();

        this.getTaSession().clear();

        this.getTfDate().clear();

    }
}
