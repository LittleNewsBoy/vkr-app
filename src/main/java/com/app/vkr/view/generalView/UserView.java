package com.app.vkr.view.generalView;

import com.app.vkr.entity.AON;
import com.app.vkr.service.AONService;
import com.app.vkr.view.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Locale;

@Getter
@Route(value = "userView", layout = MainLayout.class)
@PermitAll
public class UserView extends VerticalLayout {
	private final AONService service;
	Button primaryButton = new Button("Создать отчет по АОН за промежуток времени: ");
	DatePicker startDatePicker = new DatePicker("Выберите дату начала отчета:");
	DatePicker finishDatePicker = new DatePicker("Выберите дату конца отчета:");
	LocalDate start;
	LocalDate end;
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
		primaryButton.addClickListener(e -> {
			UI.getCurrent().navigate(ReportView.class);
		});

		startDatePicker.setLocale(locale);
		finishDatePicker.setLocale(locale);

		LocalDate date = LocalDate.now();
		startDatePicker.setValue(date.minusWeeks(3));
		finishDatePicker.setValue(date);

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
