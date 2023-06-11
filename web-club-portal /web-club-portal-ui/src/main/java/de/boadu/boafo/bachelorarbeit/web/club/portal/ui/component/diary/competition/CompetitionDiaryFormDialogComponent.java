package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition;

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
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog.CompetitionDiaryFormDialogEventListeners;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog.CompetitonDiaryFormDialogEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog.CompetitonDiaryFormDialogEventRequestImpl;
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
public class CompetitionDiaryFormDialogComponent extends AbstractComponent implements
        AbstractObserver<CompetitionDiaryFormDialogEventListeners> {

    private VerticalLayout componentRootLayout;

    private DatePicker date;

    private TextArea taPlaceInput;
    private TextArea taDiciplineInuput;
    private TextArea taResultInput;
    private TextArea taFeelingInput;

    private VerticalLayout dialogLayout;

    private Dialog newEntryDialogForm;

    private Button btnSave;
    private Button btnClose;

    private Set<CompetitionDiaryFormDialogEventListeners> competitionDiaryFormDialogEventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.competitionDiaryFormDialogEventListeners = new HashSet<>();

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

        this.taPlaceInput = new TextArea();
        this.taPlaceInput.setLabel("Ort");
        this.taPlaceInput.setMaxHeight("150px");
        this.taPlaceInput.setHeight("150px");
        this.taPlaceInput.setWidth("480px");

        this.taDiciplineInuput = new TextArea();
        this.taDiciplineInuput.setLabel("Disziplin");
        this.taDiciplineInuput.setMaxHeight("150px");
        this.taDiciplineInuput.setHeight("150px");
        this.taDiciplineInuput.setWidth("480px");

        this.taResultInput = new TextArea();
        this.taResultInput.setLabel("Ergebniss");
        this.taResultInput.setMaxHeight("150px");
        this.taResultInput.setHeight("150px");
        this.taResultInput.setWidth("480px");

        this.taFeelingInput = new TextArea();
        this.taFeelingInput.setLabel("Zufriedenheit");
        this.taFeelingInput.setMaxHeight("150px");
        this.taFeelingInput.setHeight("150px");
        this.taFeelingInput.setWidth("480px");

        this.dialogLayout = new VerticalLayout();

        this.getDialogLayout().add(this.getDate());
        this.getDialogLayout().add(this.getTaPlaceInput());
        this.getDialogLayout().add(this.getTaDiciplineInuput());
        this.getDialogLayout().add(this.getTaResultInput());
        this.getDialogLayout().add(this.getTaFeelingInput());

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
        this.getNewEntryDialogForm().setHeaderTitle("Neuer Wettkampf Eintrag");
        this.getNewEntryDialogForm().getFooter().add(this.getBtnSave());
        this.getNewEntryDialogForm().getFooter().add(this.getBtnClose());

    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        //TODO: Nochmal angucken
        this.componentRootLayout.setHeight("0%");
        this.componentRootLayout.setWidth("0%");

        this.getComponentRootLayout().add(this.getNewEntryDialogForm());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnClose().addClickListener(doOnClickClose());

        this.getBtnSave().addClickListener(doOnClickSave());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickSave() {
        return clickEvent -> {

            LocalDate date = this.getDate().getValue();

            String place = this.getTaPlaceInput().getValue();

            String dicipline = this.getTaDiciplineInuput().getValue();

            String result = this.getTaResultInput().getValue();

            String feeling = this.getTaFeelingInput().getValue();

            MutableCompetitionDiaryEntry newEntry = new CompetitionDiaryEntryDto();
            newEntry.setDate(date);
            newEntry.setPlace(place);
            newEntry.setDicipline(dicipline);
            newEntry.setResult(result);
            newEntry.setFeeling(feeling);

            CompetitonDiaryFormDialogEventRequest event = new CompetitonDiaryFormDialogEventRequestImpl(newEntry);

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

    @Override
    public void addEventListeners(CompetitionDiaryFormDialogEventListeners listener) {

        this.getCompetitionDiaryFormDialogEventListeners().add(listener);

    }

    private void notifySaveClickedEventListener(CompetitonDiaryFormDialogEventRequest event) {

        this.getCompetitionDiaryFormDialogEventListeners().forEach(listener -> listener.handleSave(event));

    }

    private void clearForm() {

        this.getDate().clear();

        this.getTaDiciplineInuput().clear();

        this.getTaFeelingInput().clear();

        this.getTaResultInput().clear();

        this.getTaPlaceInput().clear();
    }

    public void openDialog() {

        this.getNewEntryDialogForm().open();
    }


}
