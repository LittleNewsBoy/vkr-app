package com.app.vkr.view.general;

//import com.myapp.app.data.entity.AppUser;
import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import com.app.vkr.service.AONService;
import com.app.vkr.service.CrmService;
import com.app.vkr.view.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Locale;

@Route(value = "userView", layout = MainLayout.class)
@PermitAll
public class UserView extends VerticalLayout {
	private final AONService service;
	Button primaryButton = new Button("Создать отчет по АОН за промежуток времени: ");
	DatePicker startDatePicker = new DatePicker("Выберите дату начала отчета:");
	DatePicker finishDatePicker = new DatePicker("Выберите дату конца отчета:");
	Locale locale = new Locale("ru", "RU");
	Grid<AON> grid = new Grid<>(AON.class, false);

	public UserView(AONService service){
		this.service = service;
		add(
				createScene()
		);
	}

	private Component createScene() {
		return new VerticalLayout(createLayout1(),createGrid());
	}

	private Component createGrid() {
		grid.addClassName("users-grid");
		grid.setColumns("date","location","product","decimalNumber");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
		grid.setItems(service.findAllByUsernameAON());
		return grid;
	}

	private Component createLayout1() {
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

		startDatePicker.setLocale(locale);
		finishDatePicker.setLocale(locale);

		startDatePicker.addValueChangeListener(e -> finishDatePicker.setMin(e.getValue()));
		finishDatePicker.addValueChangeListener(e -> startDatePicker.setMax(e.getValue()));
		
		HorizontalLayout horizontalLayout = new HorizontalLayout(primaryButton, startDatePicker, finishDatePicker);
		horizontalLayout.setFlexGrow(1,primaryButton);
		horizontalLayout.setFlexGrow(1,startDatePicker);
		horizontalLayout.setFlexGrow(1,finishDatePicker);
		horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		horizontalLayout.setWidthFull();
		horizontalLayout.setAlignItems(Alignment.BASELINE);
		return horizontalLayout;
	}

}
