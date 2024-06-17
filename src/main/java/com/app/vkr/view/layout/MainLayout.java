package com.app.vkr.view.layout;

import com.app.vkr.security.SecurityService;
import com.app.vkr.view.generalView.ChatView;
import com.app.vkr.view.homeView.HomeView;
import com.app.vkr.view.generalView.UserView;
import com.app.vkr.view.adminView.AdminView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.crypto.Data;
import java.time.LocalDate;

public class MainLayout extends AppLayout {

private final SecurityService securityService;

	public MainLayout(SecurityService securityService) {
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}

	private void createHeader() {
		LocalDate date = LocalDate.now();
		H3 logo = new H3("РИРВ/Сегодняшняя дата: "+ date);
		logo.addClassNames(
				LumoUtility.FontSize.LARGE,
				LumoUtility.Margin.MEDIUM);

		String u = securityService.getAuthenticatedUser().getUsername();
		Button logout = new Button("Log out " + u, e -> securityService.logout()); // <2>

		var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo);
		header.setWidthFull();
		header.addClassNames(
				LumoUtility.Padding.Vertical.NONE,
				LumoUtility.Padding.Horizontal.MEDIUM);

		addToNavbar(header);

	}

	private void createDrawer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			addToDrawer(new VerticalLayout(
					new RouterLink("Home", HomeView.class),
					new RouterLink("User", UserView.class),
					new RouterLink("Chat", ChatView.class)
			));
		}else {
			addToDrawer(new VerticalLayout(
					new RouterLink("Home", HomeView.class),
					new RouterLink("Admin", AdminView.class),
					new RouterLink("User", UserView.class),
					new RouterLink("Chat", ChatView.class)
			));
		}
	}
}
