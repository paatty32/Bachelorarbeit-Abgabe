package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplandialogform.TrainingPlanDialogFormEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplandialogform.TrainingPlanDialogFormEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplandialogform.TrainingPlanDialogFormEventRequestImpl;
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
public class TrainingPlanDialogFormComponent extends AbstractComponent implements AbstractObserver<TrainingPlanDialogFormEventListener> {

    private VerticalLayout componentRootLayout;

    private DatePicker date;

    private TextArea taSession;
    private TextArea taAthlete;

    private VerticalLayout dialogLayout;

    private Dialog trainingPlanForm;

    private Button btnSave;
    private Button btnClose;

    private Set<TrainingPlanDialogFormEventListener> trainingPlanDialogFormEventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingPlanDialogFormEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeDialogLayout();
        this.initializeDialog();
        this.initializeRootLayout();

    }

    private void initializeDialogLayout(){

        this.date = new DatePicker();
        this.date.setLabel("Datum");
        this.date.setLocale(Locale.GERMANY);

        this.taSession = new TextArea();
        this.taSession.setLabel("Einheit");
        this.taSession.setMaxHeight("150px");
        this.taSession.setHeight("150px");
        this.taSession.setWidth("480px");

        this.taAthlete = new TextArea();
        this.taAthlete.setLabel("Athlet");
        this.taAthlete.setMaxHeight("150px");
        this.taAthlete.setHeight("150px");
        this.taAthlete.setWidth("480px");

        this.dialogLayout = new VerticalLayout();

        this.getDialogLayout().add(this.getDate());
        this.getDialogLayout().add(this.getTaSession());
        this.getDialogLayout().add(this.getTaAthlete());

    }

    private void initializeDialog(){

        this.btnSave = new Button();
        this.btnSave.setText("Speichern");
        this.btnSave.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        this.btnClose = new Button();
        this.btnClose.setText("Schließen");
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.trainingPlanForm = new Dialog();

        this.getTrainingPlanForm().add(this.getDialogLayout());
        this.getTrainingPlanForm().setHeaderTitle("Neuer Trainingsplan Eintrag");
        this.getTrainingPlanForm().getFooter().add(this.getBtnSave());
        this.getTrainingPlanForm().getFooter().add(this.getBtnClose());
    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setHeight("0%");
        this.componentRootLayout.setWidth("0%");

        this.getComponentRootLayout().add(this.getTrainingPlanForm());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnSave().addClickListener(doOnClickSave());

        this.getBtnClose().addClickListener(doOnClickClose());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickSave() {
        return clickEvent -> {

            MutableTrainingPlanEntry newEntry = new TrainingPlanEntryDTO();
            newEntry.setDate(this.getDate().getValue());
            newEntry.setSession(this.getTaSession().getValue());
            newEntry.setAthlete(this.getTaAthlete().getValue());

            TrainingPlanDialogFormEventRequest event = new TrainingPlanDialogFormEventRequestImpl(newEntry);

            this.notifyTrainingPlanDialogEventListeners(event);

            this.clearForm();

            this.getTrainingPlanForm().close();

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickClose() {
        return event -> {

            this.clearForm();

            this.getTrainingPlanForm().close();

        };
    }

    @Override
    public void addEventListeners(TrainingPlanDialogFormEventListener listener) {
        this.getTrainingPlanDialogFormEventListeners().add(listener);
    }

    private void notifyTrainingPlanDialogEventListeners(TrainingPlanDialogFormEventRequest event) {
        this.getTrainingPlanDialogFormEventListeners().forEach(listener -> listener.handleButtonSave(event));
    }

    public void openDialog(){

        this.getTrainingPlanForm().open();

    }

    private void clearForm() {
        this.getDate().clear();
        this.getTaSession().clear();
        this.getTaAthlete().clear();
    }
}
