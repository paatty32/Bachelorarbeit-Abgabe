package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.view.DiaryView;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.view.GroupsView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Getter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeaderComponent extends AbstractComponent implements RouterLayout {

    private Tabs header;

    private Icon tfLogo;

    private Icon userIcon;

    private VerticalLayout componentRootLayout;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.initializeMenuBarContent();

    }

    private void initializeMenuBarContent(){

        this.tfLogo = new Icon(VaadinIcon.ACADEMY_CAP);

        this.header = new Tabs();
        this.header.setAutoselect(false);

        this.getHeader().add(new RouterLink("Gruppen", GroupsView.class));
        this.getHeader().add(new RouterLink("Tagebuch", DiaryView.class));

        this.userIcon = new Icon(VaadinIcon.USER);

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidthFull();
        this.componentRootLayout.setHeightFull();

        this.getComponentRootLayout().add(this.getHeader());
        this.getComponentRootLayout().setAlignItems(FlexComponent.Alignment.CENTER);

    }

    @Override
    protected void initializeComponentsActions() {

        this.getHeader().addSelectedChangeListener(event -> {

            String headerLabel = event.getSelectedTab().getLabel();

            if(headerLabel.equals("Gruppen")){

                this.navigateTo(GroupsView.class);

            } else if (headerLabel.equals("Tagebuch")) {

                this.navigateTo(DiaryView.class);

            }

        });

    }
}
