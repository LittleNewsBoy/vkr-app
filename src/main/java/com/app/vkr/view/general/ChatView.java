package com.app.vkr.view.general;

import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import com.app.vkr.entity.Todo;
import com.app.vkr.repo.TodoRepo;
import com.app.vkr.view.layout.MainLayout;
import com.sun.jna.platform.win32.Netapi32Util;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "chat", layout = MainLayout.class)
@PermitAll
public class ChatView extends VerticalLayout {
	TextField task = new TextField();
	Todo todo;
	Button button = new Button("Add");
	Button buttonDone = new Button("Done");
	VerticalLayout todos = new VerticalLayout();
	private TodoRepo repo;
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
	UserInfo userInfo = new UserInfo(username,username);

	public ChatView(TodoRepo repo) {
		this.repo = repo;
		todos.setPadding(false);
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.addClickShortcut(Key.ENTER);

		button.addClickListener(click -> {
			Todo todo = repo.save(new Todo(task.getValue()));
			todos.add(createCheckbox(todo));
			task.clear();
		});
		buttonDone.addClickListener(click -> {
			repo.deleteByDone(true);
			todos.removeAll();
			repo.findAll().forEach(todo -> todos.add(createCheckbox(todo)));
		});

		repo.findAll().forEach(todo -> todos.add(createCheckbox(todo)));

		HorizontalLayout horizontalLayout = getHorizontalLayout();
		add(horizontalLayout);

	}

	private HorizontalLayout getHorizontalLayout() {

		var messageList = new CollaborationMessageList(userInfo, "new AON");
		messageList.setWidth("500px");
		VerticalLayout ver1 = new VerticalLayout(new H1("Chat"), messageList,new CollaborationMessageInput(messageList));
		VerticalLayout ver2 = new VerticalLayout(new H1("Todo"), new HorizontalLayout(task, button, buttonDone), todos);
		ver1.setHeight("500px");
		HorizontalLayout horizontalLayout = new HorizontalLayout(ver1,ver2);
		horizontalLayout.setSpacing(false);

		horizontalLayout.setAlignItems(Alignment.BASELINE);
		horizontalLayout.setSizeFull();

		return horizontalLayout;
	}

	private Component createCheckbox(Todo todo) {
		return new Checkbox(todo.getTask(), todo.isDone(), e -> {
			todo.setDone(e.getValue());
			repo.save(todo);
		});
	}
}