package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingGroupContainerComponent extends AbstractComponent  {

    private VerticalLayout componentRootLayout;

    private TabSheet groupTabSheet;

    private final TrainingGroupPlanContainer trainingPlanContainer;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.initializeTabSheet();
        this.initializeComponentRootLayout();

    }

    private void initializeTabSheet(){

        this.groupTabSheet = new TabSheet();
        this.groupTabSheet.setSizeFull();
        this.groupTabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);

        this.getGroupTabSheet().add("Trainingsplan", this.getTrainingPlanContainer());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getGroupTabSheet());

    }


    @Override
    protected void initializeComponentsActions() {

    }

    public void setGroupId(Long parameter) {
        this.getTrainingPlanContainer().setGroupId(parameter);
    }
}
