package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan.TrainingGroupPlanEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan.TrainingGroupPlanEventRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingGroupPlanComponent extends AbstractComponent implements AbstractObserver<TrainingGroupPlanEventListener> {

    private VerticalLayout componentRootLayout;

    private Grid<TrainingPlanEntry> trainingPlanGrid;
    private List<TrainingPlanEntry> trainingPlanBuffer;
    private InMemoryDataProvider<TrainingPlanEntry> trainingPlanEntryInMemoryDataProvider;

    private Long groupId;

    private final GroupUiService groupUiService;

    private Set<TrainingGroupPlanEventListener> trainingPlanEventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingPlanBuffer = new ArrayList<>();
        
        this.trainingPlanEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeGridData();
        this.initializeComponentRootLayout();

    }

    private void initializeGrid(){

        this.trainingPlanGrid = new Grid<>();
        this.trainingPlanGrid.addColumn(TrainingPlanEntry::getDate).setHeader("Datum");
        this.trainingPlanGrid.addColumn(TrainingPlanEntry::getSession).setHeader("Einheit");
        this.trainingPlanGrid.addColumn(TrainingPlanEntry::getAthlete).setHeader("Athlet");

        this.trainingPlanEntryInMemoryDataProvider = new ListDataProvider<>(this.getTrainingPlanBuffer());

        this.getTrainingPlanGrid().setItems(this.getTrainingPlanEntryInMemoryDataProvider());

    }

    private void initializeGridData(){

        if(this.getGroupId() != null) {
            Set<TrainingPlanEntry> entries = this.getGroupUiService().getTrainingPlanByGroup(this.getGroupId());

            this.getTrainingPlanBuffer().clear();
            this.getTrainingPlanBuffer().addAll(entries);

            this.getTrainingPlanGrid().setItems(this.getTrainingPlanBuffer());
        }
    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getTrainingPlanGrid());

    }

    @Override
    protected void initializeComponentsActions() {
        
        this.getTrainingPlanGrid().addItemClickListener(doOnClickGrid());

    }

    private ComponentEventListener<ItemClickEvent<TrainingPlanEntry>> doOnClickGrid() {
        return clickEvent -> {

            TrainingPlanEntry clickedEntry = clickEvent.getItem();

            TrainingGroupPlanEventRequest event = TrainingGroupPlanEventRequest.instanceOf(clickedEntry);

            this.notifyEventListenerForClickedEntry(event);

        };
    }

    private void notifyEventListenerForClickedEntry(TrainingGroupPlanEventRequest clickedEntry) {
        this.getTrainingPlanEventListeners().forEach(listener -> listener.handleGridClick(clickedEntry));
    }

    @Override
    public void addEventListeners(TrainingGroupPlanEventListener listener) {
        this.getTrainingPlanEventListeners().add(listener);
    }

    public void setGroupId(Long parameter) {

        this.groupId = parameter;
        this.initializeGridData();

    }
}
