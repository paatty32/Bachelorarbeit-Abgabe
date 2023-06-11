package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.AthleteDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events.AthleteDiaryGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events.AthleteDiaryGridEventRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class AthleteDiaryGridComponent extends AbstractComponent implements AbstractObserver<AthleteDiaryGridEventListener>,
        RefreshableComponent<Collection<AppUser>> {

    private VerticalLayout componentRootLayout;

    private Grid<AppUser> athleteDiaryGrid;
    private List<AppUser> athleteDiariesBuffer;
    private InMemoryDataProvider<AppUser> athleteDiaryInMemoryDataProvider;

    private final AthleteDiaryUiService athleteDiaryUiService;

    private final SecurityService securityService;

    private Set<AthleteDiaryGridEventListener> athleteDiaryGridEventListeners;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.athleteDiariesBuffer = new ArrayList<>();

        this.athleteDiaryGridEventListeners = new HashSet<>();
    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeGridData();
        this.initializeComponentRootLayout();

    }

    private void initializeGrid(){

        this.athleteDiaryGrid = new Grid<>();
        this.athleteDiaryGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.athleteDiaryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.athleteDiaryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        this.athleteDiaryInMemoryDataProvider = new ListDataProvider<>(this.getAthleteDiariesBuffer());

        this.getAthleteDiaryGrid().setItems(this.getAthleteDiaryInMemoryDataProvider());

        this.getAthleteDiaryGrid().addColumn(AppUser::getName).setHeader("Name");
        this.getAthleteDiaryGrid().addColumn(AppUser::getSurname).setHeader("Nachname");

    }

    private void initializeGridData(){

        Long userId = this.getSecurityService().getUserId();

        List<AppUser> appUserList = this.getAthleteDiaryUiService().getAthletesByTrainer(userId);

        this.getAthleteDiariesBuffer().clear();
        this.getAthleteDiariesBuffer().addAll(appUserList);

        this.getAthleteDiaryInMemoryDataProvider().refreshAll();

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidthFull();
        this.componentRootLayout.setHeightFull();


        this.getComponentRootLayout().add(this.getAthleteDiaryGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getAthleteDiaryGrid().addItemClickListener( clickEvent -> {

            AppUser clickedAppUser = clickEvent.getItem();

            AthleteDiaryGridEventRequest event = AthleteDiaryGridEventRequest.getInstance(clickedAppUser);

            this.notifyEventListenerForGridClick(event);


        });

    }

    @Override
    public void addEventListeners(AthleteDiaryGridEventListener listener) {

        this.getAthleteDiaryGridEventListeners().add(listener);

    }

    private void notifyEventListenerForGridClick(AthleteDiaryGridEventRequest event) {
        this.getAthleteDiaryGridEventListeners().forEach(listener -> listener.handleGridClick(event));
    }

    @Override
    public void refreshGrid(Collection<AppUser> data) {

        this.getAthleteDiariesBuffer().clear();
        this.getAthleteDiariesBuffer().addAll(data);

        this.getAthleteDiaryInMemoryDataProvider().refreshAll();

    }

    @Override
    public void clearData() {

        this.getAthleteDiariesBuffer().clear();

        this.getAthleteDiaryInMemoryDataProvider().refreshAll();
        this.getAthleteDiaryInMemoryDataProvider().clearFilters();

    }
}
