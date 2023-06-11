package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.PersonUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog.TrainingDiaryShareDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog.TrainingDiaryShareDialogEventRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingDiaryShareDialogComponent extends AbstractComponent implements AbstractObserver<TrainingDiaryShareDialogEventListener> {

    private VerticalLayout componentRootLayout;

    private VerticalLayout dialogLayout;

    private Dialog trainerDialog;

    private Button btnSelect;
    private Button btnCancel;

    private MultiSelectListBox<AppUser> trainerListBox;
    private List<AppUser> trainerBuffer;
    private InMemoryDataProvider<AppUser> trainerInMemoryDataProvider;

    private final SecurityService securityService;

    private final PersonUiService personUiService;

    private Set<TrainingDiaryShareDialogEventListener> trainingDiaryShareDialogEventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainerBuffer = new ArrayList<>();

        this.trainingDiaryShareDialogEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeDialogLayout();
        this.initializeDialog();
        this.initializeRootLayout();

    }

    private void initializeDialogLayout(){

        this.trainerInMemoryDataProvider = new ListDataProvider<>(this.getTrainerBuffer());

        this.trainerListBox = new MultiSelectListBox<>();
        this.trainerListBox.setItems(this.getTrainerInMemoryDataProvider());
        this.trainerListBox.setRenderer(new ComponentRenderer<>(
                trainer -> new Text(trainer.getName() + trainer.getSurname())
        ));

        this.dialogLayout = new VerticalLayout();
        this.dialogLayout.setSizeFull();

        this.getDialogLayout().add(this.getTrainerListBox());
    }

    private void initializeDialog(){

        this.btnSelect = new Button();
        this.btnSelect.setText("Bestätigen");
        this.btnSelect.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        this.btnCancel = new Button();
        this.btnCancel.setText("Abbrechen");
        this.btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        this.trainerDialog = new Dialog();
        this.trainerDialog.setHeaderTitle("Trainer");

        this.getTrainerDialog().add(this.getDialogLayout());
        this.getTrainerDialog().getFooter().add(this.getBtnSelect());
        this.getTrainerDialog().getFooter().add(this.getBtnCancel());

    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("0%");
        this.componentRootLayout.setHeight("0%");

        this.getComponentRootLayout().add(this.getTrainerDialog());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnSelect().addClickListener(doOnClickSelect());

        this.getBtnCancel().addClickListener(doOnClickCancel());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickSelect() {
        return clickEvent -> {

            Set<AppUser> selectedTrainer = this.getTrainerListBox().getSelectedItems();

            TrainingDiaryShareDialogEventRequest event = TrainingDiaryShareDialogEventRequest.getInstanceOf(selectedTrainer);

            this.notifyEventListenerForClickSelect(event);

            this.getTrainerListBox().clear();
            this.getTrainerDialog().close();

        };
    }


    private ComponentEventListener<ClickEvent<Button>> doOnClickCancel() {
        return listener -> {

            this.getTrainerListBox().clear();
            this.getTrainerDialog().close();

        };
    }


    @Override
    public void addEventListeners(TrainingDiaryShareDialogEventListener listener) {
       this.getTrainingDiaryShareDialogEventListeners().add(listener);
    }

    private void notifyEventListenerForClickSelect(TrainingDiaryShareDialogEventRequest event) {

        this.getTrainingDiaryShareDialogEventListeners().forEach(listener -> listener.handleClickSelect(event));

    }


    public void openDialog() {

        this.getTrainerDialog().open();
    }

    public void iniializeDialog() {

        Long userId = this.getSecurityService().getUserId();

        Set<AppUser> userTrainer = this.getPersonUiService().getUserTrainer(userId);

        this.getTrainerBuffer().clear();
        this.getTrainerBuffer().addAll(userTrainer);

        this.getTrainerInMemoryDataProvider().refreshAll();

    }
}
