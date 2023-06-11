package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid.GroupGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid.GroupsGridEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid.GroupsGridEventRequestImpl;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw.Broadcaster;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupGridComponent extends AbstractComponent implements RefreshableComponent<Collection<Group>>,
        AbstractObserver<GroupGridEventListener> {

    private VerticalLayout componentRootLayout;

    private Grid<Group> groupGrid;
    private List<Group> groupBuffer;
    private InMemoryDataProvider<Group> groupInMemoryDataProvider;

    private Set<GroupGridEventListener> groupGridEventListeners;

    private final GroupUiService groupUiService;

    private Group clickedGroup;

    private Registration broadcasterRegistration;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        this.broadcasterRegistration = Broadcaster.register( trainingGroup -> {

                ui.access(() -> {
                    this.clearData();
                    this.refreshGrid(trainingGroup);
                });

        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

    @Override
    protected void initializeInternalState() {

        this.groupBuffer = new ArrayList<>();

        this.groupGridEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeGridData();
        this.initializeRootComponent();

    }

    private void initializeGrid(){

        this.groupGrid = new Grid<>();
        this.groupGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.groupGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.groupGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        this.groupGrid.addColumn(Group::getName).setHeader("Name");
        this.groupGrid.addColumn(Group::getDescription).setHeader("Beschreibung");
        this.groupGrid.addColumn(Group::getTrainer).setHeader("Trainer");

        this.groupInMemoryDataProvider = new ListDataProvider<>(this.getGroupBuffer());

        this.groupGrid.setItems(this.getGroupInMemoryDataProvider());
    }

    private void initializeGridData(){

        List<Group> groups = this.getGroupUiService().getTrainingGroups();

        this.getGroupBuffer().addAll(groups);

    }

    private void initializeRootComponent(){

       this.componentRootLayout = new VerticalLayout();
       this.componentRootLayout.setSizeFull();

       this.getComponentRootLayout().add(this.getGroupGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getGroupGrid().addItemClickListener(clickEvent -> {

            this.clickedGroup = clickEvent.getItem();

            GroupsGridEventRequest event = new GroupsGridEventRequestImpl(this.getClickedGroup());

            this.notifyGroupGridEventListenersForClickGroup(event);

           }
        );

    }

    @Override
    public void refreshGrid(Collection<Group> data) {

        this.getGroupBuffer().clear();
        this.getGroupBuffer().addAll(data);

        this.getGroupInMemoryDataProvider().refreshAll();

    }

    @Override
    public void clearData() {

        this.getGroupBuffer().clear();

        this.getGroupInMemoryDataProvider().refreshAll();
        this.getGroupInMemoryDataProvider().clearFilters();

    }

    @Override
    public void addEventListeners(GroupGridEventListener listener) {
        this.getGroupGridEventListeners().add(listener);
    }


    private void notifyGroupGridEventListenersForClickGroup(GroupsGridEventRequest event) {

        this.getGroupGridEventListeners().forEach(listener -> listener.handleGridClick(event));

    }

}
