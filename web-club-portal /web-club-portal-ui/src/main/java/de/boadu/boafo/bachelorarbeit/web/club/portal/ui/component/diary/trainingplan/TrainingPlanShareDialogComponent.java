package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare.TrainingPlanShareEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare.TrainingPlanShareEventRequest;
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
public class TrainingPlanShareDialogComponent extends AbstractComponent implements AbstractObserver<TrainingPlanShareEventListener> {

    private VerticalLayout componentRootLayout;

    private Dialog trainingGroupList;

    private VerticalLayout dialogLayout;

    private Button btnSelect;
    private Button btnCancel;

    private MultiSelectListBox<Group> groupListBox;
    private List<Group> trainingGroupBuffer;
    private InMemoryDataProvider<Group> trainingGroupInMemoryDataProvider;

    private final SecurityService securityService;

    private final GroupUiService groupUiService;

    private Set<TrainingPlanShareEventListener> trainingPlanShareEventListeners;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingPlanShareEventListeners = new HashSet<>();

        this.trainingGroupBuffer = new ArrayList<>();

    }

    @Override
    protected void initializeComponents() {
        this.initializeDialogLayout();
        this.initializeDialog();
        this.initializeDialogData();
        this.initializeComponentRootLayout();

    }

    private void initializeDialogLayout(){

        this.trainingGroupInMemoryDataProvider = new ListDataProvider<>(this.getTrainingGroupBuffer());

        this.groupListBox = new MultiSelectListBox<>();
        this.groupListBox.setItems(this.getTrainingGroupInMemoryDataProvider());
        this.groupListBox.setRenderer(new ComponentRenderer<>(
                group -> new Text(group.getName())
        ));

        this.dialogLayout = new VerticalLayout();
        this.dialogLayout.setSizeFull();

        this.getDialogLayout().add(this.getGroupListBox());
    }


    private void initializeDialog(){

        this.btnSelect = new Button();
        this.btnSelect.setText("Bestätigen");
        this.btnSelect.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        this.btnCancel = new Button();
        this.btnCancel.setText("Abbrechen");
        this.btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        this.trainingGroupList = new Dialog();
        this.trainingGroupList.setHeaderTitle("Gruppen");

        this.getTrainingGroupList().add(this.getDialogLayout());
        this.getTrainingGroupList().getFooter().add(this.getBtnSelect());
        this.getTrainingGroupList().getFooter().add(this.getBtnCancel());

    }

    private void initializeDialogData(){

        Long userId = this.getSecurityService().getUserId();

        List<Group> trainingGroupByAdmin = this.getGroupUiService().getTrainingGroupByAdmin(userId);

        this.getTrainingGroupBuffer().addAll(trainingGroupByAdmin);

        this.getTrainingGroupInMemoryDataProvider().refreshAll();

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setWidth("0%");
        this.componentRootLayout.setHeight("0%");

        this.getComponentRootLayout().add(this.getTrainingGroupList());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnSelect().addClickListener(clickEvent -> {

            Set<Group> selectedItems = this.getGroupListBox().getSelectedItems();

            TrainingPlanShareEventRequest event = TrainingPlanShareEventRequest.getInstance(selectedItems);

            this.notifyEventListenersForClickedShare(event);

            this.getTrainingGroupList().close();

        });

        this.getBtnCancel().addClickListener(doOnClickCancel());

    }


    private ComponentEventListener<ClickEvent<Button>> doOnClickCancel() {
        return clickEvent -> {

            this.getTrainingGroupList().close();

        };
    }

    public void openDialog() {

        this.getTrainingGroupList().open();
    }

    @Override
    public void addEventListeners(TrainingPlanShareEventListener listener) {

        this.getTrainingPlanShareEventListeners().add(listener);

    }

    private void notifyEventListenersForClickedShare(TrainingPlanShareEventRequest event) {

        this.getTrainingPlanShareEventListeners().forEach(listener -> listener.handleButtonShare(event));
    }
}
