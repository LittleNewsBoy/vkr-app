package com.app.vkr.view.adminView;

import com.app.vkr.entity.AppUser;
import com.app.vkr.service.CrmService;
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
import jakarta.annotation.security.RolesAllowed;

@Route(value = "adminView", layout = MainLayout.class)
@RolesAllowed("ROLE_ADMIN")

public class AdminView extends VerticalLayout {

	private final CrmService service;
	Grid<AppUser> grid = new Grid<>(AppUser.class, true);
	com.vaadin.flow.component.textfield.TextField filterText = new TextField();
	UserForm form;

	public AdminView(CrmService service) {
		this.service = service;
		addClassName("admin-view");
		configureGrid();
		configureForm();
		add(
				getToolbar(),
				getContent()
		);
		updateList();
		closeEditor();
	}

	private void closeEditor() {
		form.setContact(null);
		form.setVisible(false);
		removeClassName("editing");
	}

	private void updateList() {
		grid.setItems(service.findAllUsers(filterText.getValue()));
	}

	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, form);
		content.setFlexGrow(2,grid);
		content.setFlexGrow(1,form);
		content.addClassName("content");
		content.setSizeFull();
		return content;
	}

	private void configureForm() {
		form = new UserForm();
		form.setWidth("25em");

		form.addSaveListener(this::saveContact); 
		form.addDeleteListener(this::deleteContact);
		form.addCloseListener(e -> closeEditor());
	}

	private void saveContact(UserForm.SaveEvent event) {
		service.saveUser(event.getContact());
		updateList();
		closeEditor();
	}

	private void deleteContact(UserForm.DeleteEvent event) {
		service.deleteUser(event.getContact());
		updateList();
		closeEditor();
	}

	private Component getToolbar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());

		Button addUserButton =new Button("Add User");
		addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		addUserButton.addClickListener(e -> addUser());

		HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private void addUser() {
		grid.asSingleSelect().clear();
		editUser(new AppUser());
	}

	private void configureGrid() {
		grid.addClassName("users-grid");
		grid.setColumns("username","password","role");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(e -> editUser(e.getValue()));
	}

	private void editUser(AppUser appUser) {
		if (appUser == null){
			closeEditor();
		}else {
			form.setContact(appUser);
			form.setVisible(true);
			addClassName("editing");
		}
	}
}
