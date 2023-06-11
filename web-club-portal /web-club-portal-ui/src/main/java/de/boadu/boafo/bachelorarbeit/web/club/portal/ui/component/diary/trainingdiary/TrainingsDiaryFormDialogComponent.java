package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary;

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
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.MutableTrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarydialog.TrainingsDairyFormDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarydialog.TrainingsDairyFormDialogSaveClickedEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarydialog.TrainingsDairyFormDialogSaveClickedEventRequestImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingsDiaryFormDialogComponent extends AbstractComponent implements AbstractObserver<TrainingsDairyFormDialogEventListener> {

    private DatePicker datePicker;

    private TextArea taSession;
    private TextArea taFeeling;

    private Button btnSave;
    private Button btnClose;

    private Dialog newEntryDialogForm;

    private VerticalLayout dialogLayout;

    private VerticalLayout componentRootLayout;

    private Set<TrainingsDairyFormDialogEventListener> eventListeners;


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

        this.initializeDialogLayout();
        this.initializeDialog();
        this.initializeRootLayout();

    }

    private void initializeDialogLayout(){

        this.dialogLayout = new VerticalLayout();

        this.datePicker = new DatePicker();
        this.datePicker.setLabel("Datum");
        this.datePicker.setLocale(Locale.GERMANY);

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

        this.getDialogLayout().add(this.getDatePicker());
        this.getDialogLayout().add(this.getTaSession());
        this.getDialogLayout().add(this.getTaFeeling());

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

        this.newEntryDialogForm = new Dialog();
        this.getNewEntryDialogForm().add(this.getDialogLayout());
        this.getNewEntryDialogForm().setHeaderTitle("Neuer Training Eintrag");
        this.getNewEntryDialogForm().getFooter().add(this.getBtnSave());
        this.getNewEntryDialogForm().getFooter().add(this.getBtnClose());

    }

    private void initializeRootLayout(){
        this.componentRootLayout = new VerticalLayout();

        this.getComponentRootLayout().add(this.getNewEntryDialogForm());
    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnSave().addClickListener(doOnClickSave());

        this.getBtnClose().addClickListener(doOnClickClose());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickSave() {
        return clickEvent -> {

            String session = this.getTaSession().getValue();

            String feeling = this.getTaFeeling().getValue();

            LocalDate date = this.getDatePicker().getValue();

            MutableTrainingDiaryEntry mutableTrainingDiaryEntry = new TrainingDiaryEntryDTO();
            mutableTrainingDiaryEntry.setSession(session);
            mutableTrainingDiaryEntry.setFeeling(feeling);
            mutableTrainingDiaryEntry.setDate(date);

            TrainingsDairyFormDialogSaveClickedEventRequest event = new TrainingsDairyFormDialogSaveClickedEventRequestImpl((TrainingDiaryEntry) mutableTrainingDiaryEntry);

            this.notifySaveClickedEventListener(event);

            this.clearForm();

            this.getNewEntryDialogForm().close();

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickClose() {
        return event -> {

            this.clearForm();

            this.getNewEntryDialogForm().close();

        };
    }

    private void clearForm() {

        this.getTaSession().clear();

        this.getDatePicker().clear();

        this.getTaFeeling().clear();

    }

    private void notifySaveClickedEventListener(TrainingsDairyFormDialogSaveClickedEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleSave(event));

    }

    @Override
    public void addEventListeners(TrainingsDairyFormDialogEventListener listener) {
        this.getEventListeners().add(listener);
    }

    public void openDialog() {

        this.getNewEntryDialogForm().open();

    }
}
