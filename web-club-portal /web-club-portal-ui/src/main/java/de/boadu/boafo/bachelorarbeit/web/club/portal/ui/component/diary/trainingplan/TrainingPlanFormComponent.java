package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan;

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
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingPlanFormComponent extends AbstractComponent implements AbstractObserver<TrainingPlanFormEventListener> {

    private VerticalLayout componentRootLayout;

    private DatePicker date;

    private TextArea taSession;
    private TextArea taAthlete;

    private Button btnUpdate;
    private Button btnDelete;
    private Button btnClose;
    private Button btnShare;

    private VerticalLayout formLayout;

    private HorizontalLayout buttonLayout;

    private Set<TrainingPlanFormEventListener> trainingPlanFormEventListeners;

    private Long clickedEntryId;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingPlanFormEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeFormLayout();
        this.initializeButtonLayout();
        this.intitializeRootLayout();

    }

    private void initializeFormLayout(){

        this.date = new DatePicker();
        this.date.setLabel("Datum");
        this.date.setLocale(Locale.GERMANY);

        this.taSession = new TextArea();
        this.taSession.setLabel("Einheit");
        this.taSession.setMaxHeight("150px");
        this.taSession.setHeight("150px");
        this.taSession.setWidth("480px");

        this.taAthlete = new TextArea();
        this.taAthlete.setLabel("Athlete");
        this.taAthlete.setMaxHeight("150px");
        this.taAthlete.setHeight("150px");
        this.taAthlete.setWidth("480px");

        this.formLayout = new VerticalLayout();
        this.formLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        this.getFormLayout().add(this.getDate());
        this.getFormLayout().add(this.getTaSession());
        this.getFormLayout().add(this.getTaAthlete());

    }

    private void initializeButtonLayout(){

        this.btnUpdate = new Button();
        this.btnUpdate.setText("Übernehmen");
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        this.btnDelete = new Button();
        this.btnDelete.setText("Löschen");
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.btnClose = new Button();
        this.btnClose.setText("Schließen");
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.btnShare = new Button();
        this.btnShare.setIcon(VaadinIcon.SHARE.create());

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setWidthFull();

        this.getButtonLayout().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.getButtonLayout().add(this.getBtnUpdate());
        this.getButtonLayout().add(this.getBtnDelete());
        this.getButtonLayout().add(this.getBtnShare());
        this.getButtonLayout().add(this.getBtnClose());

    }

    private void intitializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("40%");
        this.componentRootLayout.setHeight("100%");
        this.componentRootLayout.setVisible(false);

        this.getComponentRootLayout().add(this.getFormLayout());
        this.getComponentRootLayout().add(this.getButtonLayout());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnUpdate().addClickListener(doOnClickUpdate());

        this.getBtnDelete().addClickListener(doOnClickDelete());

        this.getBtnShare().addClickListener(doOnClickShare());

        this.getBtnClose().addClickListener(doOnClickClose());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickShare() {
        return clickEntry -> {

            this.notifyTrainingPlanFormEventListenerForClickShare();

        };
    }

    private void notifyTrainingPlanFormEventListenerForClickShare() {

        this.getTrainingPlanFormEventListeners().forEach(listener -> listener.handleButtonShare());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickDelete() {
        return clickEvent -> {

            if (this.getClickedEntryId() != null) {

                TrainingPlanFormDeleteEntryEventRequest event = TrainingPlanFormDeleteEntryEventRequest.getInstance(this.getClickedEntryId());

                this.notifyTrainingPlanFormEventListenerForDeletingEntry(event);

                this.clearForm();

            }

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickUpdate() {
        return clickEvent -> {

            MutableTrainingPlanEntry updatedEntry = new TrainingPlanEntryDTO();
            updatedEntry.setId(this.getClickedEntryId());
            updatedEntry.setDate(this.getDate().getValue());
            updatedEntry.setSession(this.getTaSession().getValue());
            updatedEntry.setAthlete(this.getTaAthlete().getValue());

            TrainingPlanFormEventRequest event = new TrainingPlanFormEventRequestImpl(updatedEntry);

            this.notifyTrainingPlanFormEventListenerForClickedUpdate(event);
        };
    }


    private ComponentEventListener<ClickEvent<Button>> doOnClickClose() {
        return event -> {

            this.getComponentRootLayout().setVisible(false);

            this.clearForm();

        };
    }

    @Override
    public void addEventListeners(TrainingPlanFormEventListener listener) {
        this.getTrainingPlanFormEventListeners().add(listener);
    }

    private void notifyTrainingPlanFormEventListenerForClickedUpdate(TrainingPlanFormEventRequest event) {

        this.getTrainingPlanFormEventListeners().forEach(listener -> listener.handleButtonUpdate(event));

    }

    private void notifyTrainingPlanFormEventListenerForDeletingEntry(TrainingPlanFormDeleteEntryEventRequest event) {

        this.getTrainingPlanFormEventListeners().forEach(listener -> listener.handleButtonDelete(event));

    }


    private void clearForm() {
        this.getDate().clear();

        this.getTaAthlete().clear();

        this.getTaSession().clear();
    }

    public void fillForm(TrainingPlanEntry trainingPlanEntry) {

        this.getDate().setValue(trainingPlanEntry.getDate());

        this.getTaSession().setValue(trainingPlanEntry.getSession());

        this.getTaAthlete().setValue(trainingPlanEntry.getAthlete());
    }

    public void setClickedEntryId(TrainingPlanEntry trainingPlanEntry){

        this.clickedEntryId = trainingPlanEntry.getId();

    }


}
