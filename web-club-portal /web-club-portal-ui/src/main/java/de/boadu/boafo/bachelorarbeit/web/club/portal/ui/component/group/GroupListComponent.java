package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.PersonUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw.Broadcaster;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.view.TrainingGroupView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringComponent
//@UIScope
@RouteScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupListComponent extends AbstractComponent implements RefreshableComponent<Collection<Group>> {

    private VerticalLayout componentRootLayout;

    private List<Group> groupBuffer;
    private InMemoryDataProvider<Group> groupInMemoryDataProvider;

    private Scroller groupScroller;
    private VerticalLayout scrollerContent;

    private final GroupUiService groupUiService;

    private final PersonUiService personUiService;

    private final SecurityService securityService;

    private Registration broadcasterRegistration;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupBuffer = new ArrayList<>();

    }

    @Override
    protected void initializeComponents() {

        this.initalizeScrollerContent();
        this.intializeScroller();
        this.initializeComponentRootLayout();

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        this.broadcasterRegistration = Broadcaster.registerOwnTrainingGroupListener(trainingGroup -> {

            ui.access(() -> {
                Long userId = this.getSecurityService().getUserId();
                Set<Group> groups = this.getPersonUiService().getUserGroups(userId);
                this.refreshList(groups);
            });

        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

    private void initalizeScrollerContent(){

        this.scrollerContent = new VerticalLayout();

        Long userId = this.getSecurityService().getUserId();
        Set<Group> trainingGroups = this.getPersonUiService().getUserGroups(userId);

        for (Group groups: trainingGroups) {

            this.getScrollerContent().add(new RouterLink(groups.getName(), TrainingGroupView.class, groups.getId()));

        }
    }

    private void intializeScroller(){

        this.groupScroller = new Scroller();
        this.groupScroller.setHeight("400px");
        this.groupScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        this.getGroupScroller().setContent(this.getScrollerContent());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("20%");

        this.getComponentRootLayout().add(new H2("Meine Gruppen"));
        this.getComponentRootLayout().add(this.getGroupScroller());

    }

    @Override
    protected void initializeComponentsActions() {

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

    public void refreshList(Collection<Group> groupsByAdmin) {

        this.getScrollerContent().removeAll();

        for (Group groups: groupsByAdmin) {

            this.getScrollerContent().add(new RouterLink(groups.getName(), TrainingGroupView.class, groups.getId()));

        }
    }
}
