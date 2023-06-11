package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest.GroupRequestEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest.GroupRequestEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest.GroupRequestEventRequestImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupRequestGridComponent extends AbstractComponent implements AbstractObserver<GroupRequestEventListener>,
        RefreshableComponent<Collection<GroupRequest>> {

    private VerticalLayout componentRootLayout;

    private InMemoryDataProvider<GroupRequest> groupRequestInMemoryDataProvider;
    private List<GroupRequest> groupRequestsBuffer;
    private Grid<GroupRequest> groupRequestsGrid;

    private final GroupUiService groupUiService;

    private final SecurityService securityService;

    private Set<GroupRequestEventListener> groupRequestEventListeners;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupRequestsBuffer = new ArrayList<>();

        this.groupRequestEventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeGridData();
        this.initializeComponentRootLayout();

    }

    private void initializeGrid(){

        this.groupRequestsGrid = new Grid<>();
        this.groupRequestsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.groupRequestsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        this.groupRequestsGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.groupRequestsGrid.addColumn(GroupRequest::getGroupName).setHeader("Gruppe");
        this.groupRequestsGrid.addColumn(GroupRequest::getName).setHeader("Name");
        this.groupRequestsGrid.addColumn(GroupRequest::getSurname).setHeader("Nachname");

        this.groupRequestInMemoryDataProvider = new ListDataProvider<>(this.getGroupRequestsBuffer());

        this.getGroupRequestsGrid().setItems(this.getGroupRequestInMemoryDataProvider());

    }

    private void initializeGridData(){

        Long userId = this.getSecurityService().getUserId();

        List<GroupRequest> requests = this.getGroupUiService().getGroupRequestByTrainer(userId);

        this.getGroupRequestsBuffer().addAll(requests);

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getGroupRequestsGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getGroupRequestsGrid().addItemClickListener(doOnClickGrid());

    }

    private ComponentEventListener<ItemClickEvent<GroupRequest>> doOnClickGrid() {
        return clickEvent -> {

            GroupRequest clickedRequest = clickEvent.getItem();

            GroupRequestEventRequest event = new GroupRequestEventRequestImpl(clickedRequest);

            this.notifyGroupRequestEventListenerForGridClick(event);

        };
    }


    @Override
    public void addEventListeners(GroupRequestEventListener listener) {
        this.getGroupRequestEventListeners().add(listener);
    }

    private void notifyGroupRequestEventListenerForGridClick(GroupRequestEventRequest event) {
        this.getGroupRequestEventListeners().forEach(listener -> listener.handleGridClick(event));
    }

    @Override
    public void refreshGrid(Collection<GroupRequest> data) {

        this.getGroupRequestsBuffer().clear();
        this.getGroupRequestsBuffer().addAll(data);

        this.getGroupRequestInMemoryDataProvider().refreshAll();

    }

    @Override
    public void clearData() {

        this.getGroupRequestsBuffer().clear();

        this.getGroupRequestInMemoryDataProvider().refreshAll();
        this.getGroupRequestInMemoryDataProvider().clearFilters();

    }
}
