package com.app.vkr.view.layout;

import com.app.vkr.security.SecurityService;
import com.app.vkr.view.general.ChatView;
import com.app.vkr.view.general.HomeView;
import com.app.vkr.view.general.UserView;
import com.app.vkr.view.adminView.AdminView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainLayout extends AppLayout {

//	public MainLayout(SecurityService securityService) {
//		DrawerToggle toggle = new DrawerToggle();
//
//		H1 title = new H1("MyApp");
//		title.getStyle().set("font-size", "var(--lumo-font-size-l)")
//				.set("margin", "0");
//
//		new Button("Logout", e -> securityService.logout());
//
//		SideNav nav = getSideNav();
//
//		Scroller scroller = new Scroller(nav);
//		scroller.setClassName(LumoUtility.Padding.SMALL);
//
//		addToDrawer(scroller);
//		addToNavbar(toggle, title);
//	}
//
//	private SideNav getSideNav() {
//		SideNav sideNav = new SideNav();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
//			sideNav.addItem(
//					new SideNavItem("Home", HomeView.class, VaadinIcon.CHART.create()),
//					new SideNavItem("User", HomeView.class, VaadinIcon.DASHBOARD.create()),
//					new SideNavItem("Logout", HomeView.class, VaadinIcon.LIST.create()));
//		}else {
//			sideNav.addItem(
//					new SideNavItem("Home", HomeView.class, VaadinIcon.CHART.create()),
//					new SideNavItem("Admin", HomeView.class, VaadinIcon.CART.create()),
//					new SideNavItem("User", HomeView.class, VaadinIcon.DASHBOARD.create()),
//					new SideNavItem("Logout", HomeView.class, VaadinIcon.LIST.create()));
//		}
//		return sideNav;
//	}
private final SecurityService securityService;

	public MainLayout(SecurityService securityService) {
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}

	private void createHeader() {
		H1 logo = new H1("Vaadin CRM");
		logo.addClassNames(
				LumoUtility.FontSize.LARGE,
				LumoUtility.Margin.MEDIUM);

		String u = securityService.getAuthenticatedUser().getUsername();
		Button logout = new Button("Log out " + u, e -> securityService.logout()); // <2>

		var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo); // <4>
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
