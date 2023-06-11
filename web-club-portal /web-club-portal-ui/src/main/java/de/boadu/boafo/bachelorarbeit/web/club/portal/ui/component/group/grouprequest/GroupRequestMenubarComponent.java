package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequestmenubar.GroupRequestMenubarEventListener;
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
public class GroupRequestMenubarComponent extends AbstractComponent implements AbstractObserver<GroupRequestMenubarEventListener> {

    private HorizontalLayout componentRootLayout;

    private HorizontalLayout buttonLayout;

    private Button btnAccept;
    private Button btnDecline;

    private Set<GroupRequestMenubarEventListener> groupRequestMenubarEventListeners;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupRequestMenubarEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeButtonLayout();
        this.initializeRootLayout();

    }

    private void initializeButtonLayout(){

        this.btnAccept = new Button();
        this.btnAccept.setText("Akzeptieren");
        this.btnAccept.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_TERTIARY);

        this.btnDecline = new Button();
        this.btnDecline.setText("Ablehnen");
        this.btnDecline.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setSizeFull();

        this.getButtonLayout().add(this.getBtnAccept());
        this.getButtonLayout().add(this.getBtnDecline());
    }

    private void initializeRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setHeight("10%");

        this.getComponentRootLayout().add(this.getButtonLayout());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnAccept().addClickListener(doOnClickAccept());

        this.getBtnDecline().addClickListener(doOnClickDecline());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickDecline() {
        return clickEvent -> {

            this.notifyEventListenerForClickDecline();

        };
    }



    private ComponentEventListener<ClickEvent<Button>> doOnClickAccept() {
        return clickEvent -> {

            this.notifyEventListenersForFlickAccept();

        };
    }



    @Override
    public void addEventListeners(GroupRequestMenubarEventListener listener) {
        this.getGroupRequestMenubarEventListeners().add(listener);
    }

    private void notifyEventListenersForFlickAccept() {
        this.getGroupRequestMenubarEventListeners().forEach(listener -> listener.handleButtonAccept());
    }

    private void notifyEventListenerForClickDecline() {
        this.getGroupRequestMenubarEventListeners().forEach(listener -> listener.handleButtonDecline());
    }
}
