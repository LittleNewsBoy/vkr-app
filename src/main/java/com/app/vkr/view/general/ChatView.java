package com.app.vkr.view.general;

import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import com.app.vkr.view.layout.MainLayout;
import com.sun.jna.platform.win32.Netapi32Util;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "chat", layout = MainLayout.class)
@PermitAll
public class ChatView extends VerticalLayout {

	public ChatView() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		var userInfo = new UserInfo(username,username);
		var messageList = new CollaborationMessageList(userInfo, "new AON");
		HorizontalLayout horizontalLayout = new HorizontalLayout(new VerticalLayout(messageList, new CollaborationMessageInput(messageList)));
		horizontalLayout.setSizeFull();
		horizontalLayout.setWidthFull();
		horizontalLayout.setMaxHeight("750px");
		horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		add(horizontalLayout);
	}
}