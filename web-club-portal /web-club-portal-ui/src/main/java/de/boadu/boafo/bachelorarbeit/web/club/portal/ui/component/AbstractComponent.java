package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.view.TrainingGroupView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;

@NoArgsConstructor
public abstract class AbstractComponent extends Composite<Component> {

    @Getter(AccessLevel.PRIVATE)
    private Component componentRootLayout;

    @PostConstruct
    public void initilalizeComponent(){
        this.initialize();
    }

    private void initialize() {

        this.initializeInternalState();
        this.initializeComponents();
        this.initializeComponentsActions();
        this.initializeRootLayout();

    }

    private void initializeRootLayout(){

        this.componentRootLayout = this.getRootLayout();

    }

    protected void navigateTo(Class<? extends Component> navigationTarget){
        UI.getCurrent().navigate(navigationTarget);
    }

    protected void navigateTo(Class<? extends Component> navigationTarget, Long parameter){

        UI.getCurrent().navigate(TrainingGroupView.class, parameter);

    }


    @Override
    protected Component initContent() {

        return this.getComponentRootLayout();

    }

    protected abstract Component getRootLayout();

    protected abstract void initializeInternalState();

    protected abstract void initializeComponents();

    protected abstract void initializeComponentsActions();



}