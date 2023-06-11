package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.PersonUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequestcontainer.GroupRequestContainerEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.GroupRequestContainer;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw.Broadcaster;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
@RouteScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupTabsheetComponent extends AbstractComponent implements GroupRequestContainerEventListener {

    private VerticalLayout componentRootLayout;

    private TabSheet groupTabsheet;

    private final GroupContainer groupContainer;

    private final GroupRequestContainer groupRequestContainer;

    private final SecurityService securityService;

    private final GroupUiService groupUiService;

    private Tab groupRequestTab;

    private final PersonUiService personUiService;

    private Registration broadcasterRegistration;

    private final AbstractObserver<GroupRequestContainerEventListener> groupRequestContainerEventListenerObserver;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachGroupRequestContainerEventListener();
        this.initializeGroupTabSheet();
        this.initializeComponentRootLayout();

    }

    private void attachGroupRequestContainerEventListener(){
        this.getGroupRequestContainerEventListenerObserver().addEventListeners(this);
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        this.broadcasterRegistration = Broadcaster.registerGroupRequestListener(trainingGroupRequest -> {

            ui.access(() -> {
                this.getGroupRequestContainer().clearData();
                this.getGroupRequestContainer().refreshGrid(trainingGroupRequest);

            //    this.setRequestTabCaption();
            });

        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }


    private void initializeGroupTabSheet(){

        this.groupTabsheet = new TabSheet();
        this.groupTabsheet.setSizeFull();
        this.groupTabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        this.groupTabsheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);

        this.getGroupTabsheet().add("Gruppenübersicht", this.getGroupContainer());

        boolean isRoleTrainer = this.getSecurityService().getUserRoles().contains("ROLE_TRAINER");

        if(isRoleTrainer){

            int requestCount = this.getRequestCount();

            this.groupRequestTab = this.getGroupTabsheet().add("Anfragen",
                    this.getGroupRequestContainer());

        }
    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getGroupTabsheet());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleRequestAnswer() {
        this.setRequestTabCaption();
    }

    private int getRequestCount(){

        Long userId = this.getSecurityService().getUserId();

        List<GroupRequest> traininGroupRequestByTrainer = this.getGroupUiService().getGroupRequestByTrainer(userId);

        return traininGroupRequestByTrainer.size();

    }

    private void setRequestTabCaption(){

        int requestCount = this.getRequestCount();

       // this.getGroupRequestTab().setLabel("Anfragen (" + requestCount + ")");

    }

    public void refreshData() {

        this.getGroupContainer().refreshData();

        this.getGroupRequestContainer().refreshData();

      //  this.setRequestTabCaption();

    }
}
