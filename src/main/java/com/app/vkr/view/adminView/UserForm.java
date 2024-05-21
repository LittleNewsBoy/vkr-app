package com.app.vkr.view.adminView;


import com.app.vkr.entity.AppUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserForm extends FormLayout {

	@Autowired
	private PasswordEncoder passwordEncoder;

  TextField username = new TextField("Username");
  TextField password = new TextField("Password");
  ComboBox<String> role = new ComboBox<>("Role");

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  Binder<AppUser> binder = new BeanValidationBinder<>(AppUser.class);
	private AppUser user;

	public UserForm() {
    binder.bindInstanceFields(this);

    role.setItems("ADMIN", "USER");

    add(
		username,
		password,
        role,
        createButtonsLayout());
  }
	public void setContact(AppUser user) {
		this.user = user;
		binder.readBean(user);
	}

  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    return new HorizontalLayout(save, delete, close);
  }

	private void validateAndSave() {
		try {
			binder.writeBean(user);
			fireEvent(new SaveEvent(this, user));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	// Events
	public static abstract class ContactFormEvent extends ComponentEvent<UserForm> {
		private final AppUser user;

		protected ContactFormEvent(UserForm source, AppUser appUser) {
			super(source, false);
			this.user = appUser;
		}

		public AppUser getContact() {
			return user;
		}
	}

	public static class SaveEvent extends ContactFormEvent {
		SaveEvent(UserForm source, AppUser appUser) {
			super(source, appUser);
		}
	}

	public static class DeleteEvent extends ContactFormEvent {
		DeleteEvent(UserForm source, AppUser appUser) {
			super(source, appUser);
		}

	}

	public static class CloseEvent extends ContactFormEvent {
		CloseEvent(UserForm source) {
			super(source, null);
		}
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return addListener(DeleteEvent.class, listener);
	}

	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
		return addListener(SaveEvent.class, listener);
	}
	public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
		return addListener(CloseEvent.class, listener);
	}


}

