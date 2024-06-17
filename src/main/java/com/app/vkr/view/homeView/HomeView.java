package com.app.vkr.view.homeView;

//import com.myapp.app.data.entity.AppUser;
import com.app.vkr.entity.AON;
import com.app.vkr.service.AONService;
import com.app.vkr.view.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {
	Grid<AON> grid = new Grid<>(AON.class, false);
	TextField filterText = new TextField();
	private final AONService service;
	FormView form;

	public HomeView(AONService service) {
		this.service = service;
		confGrid();
		confForm();
		add(
				getToolbar(),
				getContent()
		);
		
		updateList();
		closeEditor();
	}

	private void closeEditor() {
		form.setAON(null);
		form.setVisible(false);

	}

	private void updateList() {
		grid.setItems(service.findAllAON(filterText.getValue()));
	}

	private Component getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, form);
		content.setFlexGrow(2, grid);
		content.setFlexGrow(1,form);
		content.setSizeFull();
		content.setJustifyContentMode(JustifyContentMode.AROUND);
		content.setAlignItems(Alignment.BASELINE);
		return content;
	}

	private void confForm() {
		form = new FormView();
		form.setWidth("25em");

		form.addSaveListener(this::saveAON);
		form.addDeleteListener(this::deleteAON);
		form.addCloseListener(e -> closeEditor());
	}

	private void deleteAON(FormView.DeleteEvent deleteEvent) {
		service.deleteAON(deleteEvent.getContact());
		updateList();
		closeEditor();
	}

	private void saveAON(FormView.SaveEvent saveEvent) {
		service.saveAON(saveEvent.getContact());
		updateList();
		closeEditor();
	}

	private Component getToolbar() {
		filterText.setPlaceholder("Filter by product...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		filterText.addValueChangeListener(e -> updateList());

		Button addAONButton = new Button("Add AON");
		addAONButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addAONButton.addClickListener(e -> addAON());

		return new HorizontalLayout(filterText, addAONButton);
	}

	private void addAON() {
		grid.asSingleSelect().clear();
		editAON(new AON());
	}

	private void confGrid() {
		grid.addClassName("users-grid");
		grid.setColumns("date","location","product","decimalNumber");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(e -> editAON(e.getValue()));

	}

	private void editAON(AON aon) {
		if (aon == null){
			closeEditor();
		}else {
			form.setAON(aon);
			form.setVisible(true);
		}
	}


}
