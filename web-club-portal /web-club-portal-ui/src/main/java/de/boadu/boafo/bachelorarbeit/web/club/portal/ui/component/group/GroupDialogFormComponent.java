package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog.GroupDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog.GroupDialogEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog.GroupDialogEventRequestImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupDialogFormComponent extends AbstractComponent implements AbstractObserver<GroupDialogEventListener> {

    private VerticalLayout componentRootLayout;

    private Dialog newGroup;

    private VerticalLayout dialogFormLayout;

    private TextField tfName;
    private TextArea taDescription;

    private Button btnSave;
    private Button btnCancel;

    private Set<GroupDialogEventListener> groupDialogEventListener;

    private final SecurityService securityService;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupDialogEventListener = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.intializeDialogFormLayout();
        this.initializeDialog();
        this.initializeComponentRootLayout();

    }

    private void intializeDialogFormLayout(){

        this.tfName = new TextField();
        this.tfName.setLabel("Gruppen Namen");

        this.taDescription = new TextArea();
        this.taDescription.setLabel("Beschreibung");

        this.dialogFormLayout = new VerticalLayout();

        this.getDialogFormLayout().add(this.getTfName());
        this.getDialogFormLayout().add(this.getTaDescription());

    }

    private void initializeDialog(){

        this.btnSave = new Button();
        this.btnSave.setText("Speichern");
        this.btnSave.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        this.btnCancel = new Button();
        this.btnCancel.setText("Abbrechen");
        this.btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        this.newGroup = new Dialog();
        this.newGroup.setHeaderTitle("Neue Trainingsgruppe erstellen");

        this.getNewGroup().add(this.getDialogFormLayout());
        this.getNewGroup().getFooter().add(this.getBtnSave());
        this.getNewGroup().getFooter().add(this.getBtnCancel());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setHeight("0%");
        this.componentRootLayout.setWidth("0%");

        this.getComponentRootLayout().add(this.getNewGroup());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnSave().addClickListener(doOnClickSave());

        this.getBtnCancel().addClickListener(doOnClickClose());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickSave() {
        return clickEvent -> {

            AppUserDTO loggedUser = this.getSecurityService().getLoggedUser();
            Long userId = this.getSecurityService().getUserId();

            MutableGroup newGroup = new GroupDTO();
            newGroup.setName(this.getTfName().getValue());
            newGroup.setDescription(this.getTaDescription().getValue());
            newGroup.setAdminId(userId);
            newGroup.setTrainer(loggedUser.getName() + " " + loggedUser.getSurname());

            GroupDialogEventRequest event = new GroupDialogEventRequestImpl(newGroup);

            this.notifyTrainingGroupsEventListernsForClickSave(event);

            this.getNewGroup().close();
            this.clearForm();

        };
    }


    @Override
    public void addEventListeners(GroupDialogEventListener listener) {

        this.getGroupDialogEventListener().add(listener);

    }

    private void notifyTrainingGroupsEventListernsForClickSave(GroupDialogEventRequest event) {

        this.getGroupDialogEventListener().forEach(listener -> listener.handleButtonSave(event));

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickClose() {
        return event -> {

            this.clearForm();
            this.getNewGroup().close();

        };
    }

    private void clearForm() {

        this.getTfName().clear();

        this.getTaDescription().clear();

    }

    public void openDialog(){

        this.getNewGroup().open();

    }


}
