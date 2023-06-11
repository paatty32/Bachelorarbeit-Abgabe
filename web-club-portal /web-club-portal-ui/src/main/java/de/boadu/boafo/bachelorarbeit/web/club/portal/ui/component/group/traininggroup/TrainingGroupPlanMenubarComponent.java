package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplanmenubar.TrainingGroupPlanMenubarEventListener;
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
public class TrainingGroupPlanMenubarComponent extends AbstractComponent implements AbstractObserver<TrainingGroupPlanMenubarEventListener> {

    private HorizontalLayout componentRootLayout;

    private HorizontalLayout buttonLayout;

    private Button btnAddToDiary;

    private Set<TrainingGroupPlanMenubarEventListener> trainingGroupPlanMenubarEventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingGroupPlanMenubarEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeButtonLayout();
        this.initializeComponentRootLayout();

    }

    private void initializeButtonLayout(){

        this.btnAddToDiary = new Button();
        this.btnAddToDiary.setText("Hinzufügen");

        this.buttonLayout = new HorizontalLayout();
        this.buttonLayout.setSizeFull();

        this.getButtonLayout().add(this.getBtnAddToDiary());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setHeight("10%");

        this.getComponentRootLayout().add(this.getButtonLayout());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnAddToDiary().addClickListener(doOnClickAdd());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickAdd() {
        return clickEvent -> {

            this.notifyEventListenerForClickAdd();

        };
    }

    @Override
    public void addEventListeners(TrainingGroupPlanMenubarEventListener listener) {
        this.getTrainingGroupPlanMenubarEventListeners().add(listener);
    }

    private void notifyEventListenerForClickAdd() {
        this.getTrainingGroupPlanMenubarEventListeners().forEach(listener -> listener.handleButtonAdd());
    }
}
