package com.app.vkr.view.generalView;

import com.app.vkr.entity.AON;
import com.app.vkr.service.AONService;
import com.app.vkr.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;

@Route(value = "report", layout = MainLayout.class)
@PermitAll
public class ReportView extends VerticalLayout {
	long now = System.currentTimeMillis();
	LocalDate date = LocalDate.now();
	private final AONService service;

	public ReportView(AONService service) {
		this.service = service;
		Button pdf = new Button("PDF");
		Button csv = new Button("CSV");
		Grid<AON> grid = new Grid<>(AON.class, false);
		grid.setColumns("date", "location", "numberAON", "product",
				"decimalNumber","serialNumber","manufacturingD","structuralD","culpritNotFound","description",
				"decision","username");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
		grid.setItems(service.findByDate(date.minusWeeks(3),date));
		add(new HorizontalLayout(pdf,csv),grid);
	}
}
