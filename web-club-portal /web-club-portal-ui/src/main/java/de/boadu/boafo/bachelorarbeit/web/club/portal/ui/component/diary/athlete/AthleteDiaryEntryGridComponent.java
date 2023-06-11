package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class AthleteDiaryEntryGridComponent extends AbstractComponent implements RefreshableComponent<Collection<TrainingDiaryEntry>> {

    private VerticalLayout componentRootLayout;

    private Grid<TrainingDiaryEntry> trainingDiaryEntryGrid;
    private List<TrainingDiaryEntry> trainingDiaryEntrieBuffer;
    private InMemoryDataProvider<TrainingDiaryEntry> trainingDiaryEntryInMemoryDataProvider;

    private Button btnClose;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingDiaryEntrieBuffer = new ArrayList<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeButton();
        this.initializeComponentRootLayout();

    }

    private void initializeGrid(){

        this.trainingDiaryEntryGrid = new Grid<>();
        this.trainingDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.trainingDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.trainingDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        this.trainingDiaryEntryInMemoryDataProvider = new ListDataProvider<>(this.getTrainingDiaryEntrieBuffer());

        this.getTrainingDiaryEntryGrid().setItems(this.getTrainingDiaryEntryInMemoryDataProvider());

        this.getTrainingDiaryEntryGrid().addColumn(TrainingDiaryEntry::getDate).setHeader("Datum");
        this.getTrainingDiaryEntryGrid().addColumn(TrainingDiaryEntry::getSession).setHeader("Einheit");
        this.getTrainingDiaryEntryGrid().addColumn(TrainingDiaryEntry::getFeeling).setHeader("Gefühlzustand");

    }

    private void initializeButton(){

        this.btnClose = new Button("Schließen");
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);
        this.btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("60%");
        this.componentRootLayout.setHeightFull();

        this.getComponentRootLayout().add(this.getTrainingDiaryEntryGrid());
        this.getComponentRootLayout().add(this.getBtnClose());
        this.getComponentRootLayout().setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.getBtnClose());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnClose().addClickListener(event -> {

            this.getComponentRootLayout().setVisible(false);

        });

    }

    @Override
    public void refreshGrid(Collection<TrainingDiaryEntry> data) {

        this.getTrainingDiaryEntrieBuffer().clear();
        this.getTrainingDiaryEntrieBuffer().addAll(data);

        this.getTrainingDiaryEntryInMemoryDataProvider().refreshAll();

    }

    @Override
    public void clearData() {

        this.getTrainingDiaryEntrieBuffer().clear();

        this.getTrainingDiaryEntryInMemoryDataProvider().refreshAll();
        this.getTrainingDiaryEntryInMemoryDataProvider().clearFilters();

    }
}
