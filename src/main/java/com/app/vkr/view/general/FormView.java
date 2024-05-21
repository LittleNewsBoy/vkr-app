package com.app.vkr.view.general;

import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import com.app.vkr.repo.AONRepository;
import com.app.vkr.service.AONService;
import com.app.vkr.service.CrmService;
import com.app.vkr.view.adminView.UserForm;
import com.app.vkr.view.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Route(value = "formView", layout = MainLayout.class)
@PermitAll
public class FormView extends FormLayout {
	Binder<AON> binder = new BeanValidationBinder<>(AON.class);
	//private final AONService service;
	DatePicker date = new DatePicker("Дата:");
	ComboBox<String> location = new ComboBox<>("Участок:");
	TextField numberAON = new TextField("Номер АОН:");
	ComboBox<String> product = new ComboBox<>("Изделие");
	ComboBox<String> decimalNumber = new ComboBox<>("Децимальный номер:");
	TextField serialNumber = new TextField("Заводской номер:");
	Checkbox manufacturingD = new Checkbox("Производственный:");
	Checkbox structuralD = new Checkbox("Конструкционный:");
	Checkbox culpritNotFound = new Checkbox("Виновник не установлен");
	TextArea description = new TextArea("Описание дефектов:");
	TextArea decision = new TextArea("Решение:");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");

	private AON aon;

	public FormView() {
		binder.bindInstanceFields(this);
		//this.service = service;

		add(
				createLayout()
		);
	}

	public void setAON(AON aon){
		this.aon = aon;
		binder.readBean(aon);

	}

	private Component createLayout() {
		return new VerticalLayout(createLayout1(),createLayout2(),createLayout3(),createLayout4(),createLayout5());
	}

	private Component createLayout5() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		save.addClickListener(event -> validateAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this,aon)));
		cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

		save.addClickShortcut(Key.ENTER);
		cancel.addClickShortcut(Key.ESCAPE);

		//save.addClickListener(event -> validateAndSave());
		//delete.addClickListener(event -> fireEvent(new FormView.DeleteEvent(this, aon)));

		//horizontalLayout.setSpacing(false);
		//horizontalLayout.setFlexGrow(1, save);
		//horizontalLayout.setFlexGrow(1, delete);
		//horizontalLayout.setWidthFull();
		//horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		return new HorizontalLayout(save,delete,cancel);
	}

	private void validateAndSave() {
		try {
			binder.writeBean(aon);
			fireEvent(new SaveEvent(this, aon));
		} catch (ValidationException e) {
			throw new RuntimeException(e);
		}
	}


	private Component createLayout4() {
		int charLimit = 2500;
		description.setWidthFull();
		description.setLabel("Описание несоответствия:");
		description.setMaxLength(charLimit);
		description.setValueChangeMode(ValueChangeMode.EAGER);
		description.addValueChangeListener(e -> {
			e.getSource()
					.setHelperText(e.getValue().length() + "/" + charLimit);
		});
		decision.setWidthFull();
		decision.setLabel("Решение по дефектам::");
		decision.setMaxLength(charLimit);
		decision.setValueChangeMode(ValueChangeMode.EAGER);
		decision.addValueChangeListener(e -> {
			e.getSource()
					.setHelperText(e.getValue().length() + "/" + charLimit);
		});
		//horizontalLayout.setFlexGrow(1,description);
		//horizontalLayout.setFlexGrow(1,decision);
		//horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		//horizontalLayout.setWidthFull();
		//horizontalLayout.setAlignItems(Alignment.BASELINE);
		return new HorizontalLayout(description,decision);
	}

	private Component createLayout3() {
		manufacturingD.setLabel("Производственный:");
		structuralD.setLabel("Конструкционный:");
		culpritNotFound.setLabel("Виновник не установлен:");
		//horizontalLayout.setFlexGrow(1,manufacturingD);
		//horizontalLayout.setFlexGrow(1,structuralD);
		//horizontalLayout.setFlexGrow(1,culpritNotFound);
		//horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		//horizontalLayout.setWidthFull();
		//horizontalLayout.setAlignItems(Alignment.BASELINE);
		return new HorizontalLayout(manufacturingD,structuralD,culpritNotFound);
	}

	private Component createLayout2() {
		product.setItems("111","222","333");
		decimalNumber.setItems("1111","2222","3333");
		serialNumber.setRequiredIndicatorVisible(true);
		serialNumber.setMinLength(1);
		serialNumber.setMaxLength(18);
		serialNumber.setHelperText("Формат: 123");
		//horizontalLayout.setFlexGrow(1,product);
		//horizontalLayout.setFlexGrow(1,decimalNumber);
		//horizontalLayout.setFlexGrow(1,serialNumber);
		//horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		//horizontalLayout.setWidthFull();
		//horizontalLayout.setAlignItems(Alignment.BASELINE);
		return new HorizontalLayout(product,decimalNumber,serialNumber);
	}

	private Component createLayout1() {
		Locale locale = new Locale("ru", "RU");
		date.setLocale(locale);
		date.setValue(LocalDate.now(ZoneId.systemDefault()));
		location.setItems("1","2","3");
		numberAON.setRequiredIndicatorVisible(true);
		numberAON.setMinLength(1);
		numberAON.setMaxLength(18);
		numberAON.setHelperText("Формат: 123");
		//horizontalLayout.setFlexGrow(1,date);
		//horizontalLayout.setFlexGrow(1,location);
		//horizontalLayout.setFlexGrow(1,numberAON);
		//horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
		//horizontalLayout.setWidthFull();
		//horizontalLayout.setAlignItems(Alignment.BASELINE);
		return new HorizontalLayout(date,location,numberAON);
	}
	public static abstract class ContactFormEvent extends ComponentEvent<FormView> {
		private final AON aon;

		protected ContactFormEvent(FormView source, AON aon) {
			super(source, false);
			this.aon = aon;
		}

		public AON getContact() {
			return aon;
		}
	}
	public static class SaveEvent extends ContactFormEvent {
		SaveEvent(FormView source, AON aon) {
			super(source, aon);
		}
	}

	public static class DeleteEvent extends ContactFormEvent {
		DeleteEvent(FormView source, AON aon) {
			super(source, aon);
		}

	}
	public static class CloseEvent extends ContactFormEvent {
		CloseEvent(FormView source) {
			super(source, null);
		}

	}
	public Registration addSaveListener(ComponentEventListener<FormView.SaveEvent> listener) {
		return addListener(FormView.SaveEvent.class, listener);
	}
	public Registration addDeleteListener(ComponentEventListener<FormView.DeleteEvent> listener) {
		return addListener(FormView.DeleteEvent.class, listener);
	}
	public Registration addCloseListener(ComponentEventListener<FormView.CloseEvent> listener) {
		return addListener(FormView.CloseEvent.class, listener);
	}

}
