package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsmenubar.GroupMenubarEventListeners;
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
public class GroupMenubarComponent extends AbstractComponent implements AbstractObserver<GroupMenubarEventListeners> {

    private HorizontalLayout componentRootLayout;

    private Button btnAdd;
    private Button btnJoin;
    private Button btnRequested;

    private HorizontalLayout buttonLayout;

    private Set<GroupMenubarEventListeners> groupEventListeners;

    private final SecurityService securityService;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeButtonLayout();
        this.initializeRootLayout();

    }

    private void initializeButtonLayout(){

        boolean role_trainer = this.getSecurityService().getUserRoles().contains("ROLE_TRAINER");

        this.btnAdd = new Button();
        this.btnAdd.setText("Erstellen");
        this.btnAdd.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnAdd.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        this.btnAdd.setVisible(role_trainer);

        this.btnJoin = new Button();
        this.btnJoin.setText("Beitreten");
        this.btnJoin.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnJoin.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        this.btnJoin.setVisible(false);

        this.btnRequested = new Button();
        this.btnRequested.setText("Angefragt");
        this.btnRequested.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.btnRequested.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        this.btnRequested.setEnabled(false);
        this.btnRequested.setVisible(false);

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setSizeFull();

        this.getButtonLayout().add(this.getBtnAdd());
        this.getButtonLayout().add(this.getBtnJoin());
        this.getButtonLayout().add(this.getBtnRequested());

    }

    private void initializeRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setHeight("10%");

        this.getComponentRootLayout().add(this.getButtonLayout());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnAdd().addClickListener(doOnClickAdd());

        this.getBtnJoin().addClickListener(doOnClickJoin());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickAdd() {
        return event -> {

            this.notifyEventListenerForClickAdd();

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickJoin() {
        return clickEvent -> {

            this.notifyEventlistenerForClickJoin();

        };
    }

    @Override
    public void addEventListeners(GroupMenubarEventListeners listener) {

        this.getGroupEventListeners().add(listener);

    }

    private void notifyEventListenerForClickAdd(){

        this.getGroupEventListeners().forEach(listener -> listener.handleButtonAdd());

    }

    private void notifyEventlistenerForClickJoin() {

        this.getGroupEventListeners().forEach(listener -> listener.handleButtonJoin());

    }

    public void showJoinButton() {

        this.getBtnJoin().setVisible(true);

    }

    public void disableJoinButton() {

        this.getBtnJoin().setVisible(false);

    }

    public void setJoinButtonToRequested() {

        this.getBtnRequested().setVisible(true);

    }

    public void disButtonRequested() {

        this.getBtnRequested().setVisible(false);
    }
}
