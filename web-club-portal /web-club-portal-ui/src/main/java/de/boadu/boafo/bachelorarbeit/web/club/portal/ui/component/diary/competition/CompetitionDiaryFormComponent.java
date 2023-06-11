package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform.*;
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
public class CompetitionDiaryFormComponent extends AbstractComponent implements AbstractObserver<CompetitionDiaryFormEventListener> {

    private VerticalLayout componentRootLayout;

    private DatePicker date;

    private TextArea taPlaceInput;
    private TextArea taDiciplineInuput;
    private TextArea taResultInput;
    private TextArea taFeelingInput;

    private Button btnUpdate;
    private Button btnDelete;
    private Button btnClose;

    private VerticalLayout formLayout;

    private Set<CompetitionDiaryFormEventListener> eventListeners;

    private HorizontalLayout buttonLayout;

    private Scroller scroller;

    private CompetitionDiaryEntry clickedEntry;

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

        this.formLayout = new VerticalLayout();
        this.formLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        this.getFormLayout().add(this.getDate());
        this.getFormLayout().add(this.getTaPlaceInput());
        this.getFormLayout().add(this.getTaDiciplineInuput());
        this.getFormLayout().add(this.getTaResultInput());
        this.getFormLayout().add(this.getTaFeelingInput());

        this.getFormLayout().setHeight("800px");

    }

    private void initializeButtonLayout(){

        this.btnUpdate = new Button();
        this.btnUpdate.setText("Übernehmen");
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        this.btnDelete = new Button();
        this.btnDelete.setText("Löschen");
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.btnClose = new Button();
        this.btnClose.setText("Schließen");
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setWidthFull();

        this.getButtonLayout().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.getButtonLayout().add(this.getBtnUpdate());
        this.getButtonLayout().add(this.getBtnDelete());
        this.getButtonLayout().add(this.getBtnClose());

    }

    private void initializeRootLayout(){

        this.scroller = new Scroller();
        this.scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        this.scroller.setHeight("80%");
        this.scroller.setWidth("50%");
        this.scroller.setWidthFull();

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("40%");
        this.componentRootLayout.setHeight("100%");

        this.getScroller().setContent(this.getFormLayout());

        this.getComponentRootLayout().add(this.getScroller());
        this.getComponentRootLayout().add(this.getButtonLayout());
        this.getComponentRootLayout().setVisible(false);

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnUpdate().addClickListener(doOnClickUpdate());

        this.getBtnClose().addClickListener(doOnClickClose());

        this.getBtnDelete().addClickListener(doOnClickDelete());

    }


    private ComponentEventListener<ClickEvent<Button>> doOnClickUpdate() {
        return clickEvent -> {

            if (this.getClickedEntry() != null) {

                MutableCompetitionDiaryEntry updatedEntry = new CompetitionDiaryEntryDto();
                updatedEntry.setId(this.getClickedEntry().getId());
                updatedEntry.setDate(this.getDate().getValue());
                updatedEntry.setDicipline(this.getTaDiciplineInuput().getValue());
                updatedEntry.setPlace(this.getTaPlaceInput().getValue());
                updatedEntry.setFeeling(this.getTaFeelingInput().getValue());
                updatedEntry.setResult(this.getTaResultInput().getValue());

                CompetitionDiaryFormEventRequest event = new CompetitionDiaryFormEventRequestImpl(updatedEntry);

                this.notifyUpadetClickedEventListener(event);

            }

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickClose() {
        return clickEvent -> {

            this.clearForm();

            this.getComponentRootLayout().setVisible(false);

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickDelete() {
        return clickEvent -> {

            if (this.getClickedEntry() != null) {

                Long clickedEntryId = this.getClickedEntry().getId();

                CompetitionDiaryFormDeleteEntryEventRequest event = new CompetitionDiaryFormDeleteEntryEventRequestImpl(clickedEntryId);

                this.notifyDeleteClickedEventListener(event);

            }

        };
    }

    @Override
    public void addEventListeners(CompetitionDiaryFormEventListener listener) {

        this.getEventListeners().add(listener);

    }

    private void notifyDeleteClickedEventListener(CompetitionDiaryFormDeleteEntryEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleButtonDelete(event));
    }

    private void notifyUpadetClickedEventListener(CompetitionDiaryFormEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleButtonUpdate(event));
    }

    public void setForm(CompetitionDiaryEntry clickedEntry) {

        this.clickedEntry = clickedEntry;

        this.getDate().setValue(clickedEntry.getDate());

        this.getTaPlaceInput().setValue(clickedEntry.getPlace());

        this.getTaDiciplineInuput().setValue(clickedEntry.getDicipline());

        this.getTaResultInput().setValue(clickedEntry.getResult());

        this.getTaFeelingInput().setValue(clickedEntry.getFeeling());

    }

    public void clearForm(){

        this.getDate().clear();

        this.getTaPlaceInput().clear();

        this.getTaDiciplineInuput().clear();

        this.getTaResultInput().clear();

        this.getTaFeelingInput().clear();
    }

}
