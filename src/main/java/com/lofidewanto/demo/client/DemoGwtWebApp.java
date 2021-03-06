/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.lofidewanto.demo.client;

import java.util.logging.Logger;

import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.options.BootboxLocale;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.lofidewanto.demo.client.common.ServicePreparator;
import com.lofidewanto.demo.client.common.WidgetName;
import com.lofidewanto.demo.client.ui.main.MainPanelView;

public class DemoGwtWebApp implements EntryPoint {

	private static Logger logger = Logger
			.getLogger(DemoGwtWebApp.class.getName());

	private static final String HOST_LOADING_IMAGE = "loadingImage";

	private static final String HISTORY_MAIN = "main";

	private static final String LOCALE = "de_DE";

	// Create Gin Injector
	private final DemoGwtWebAppGinjector injector = GWT
			.create(DemoGwtWebAppGinjector.class);

	@Override
	public void onModuleLoad() {
		// Disable Back Button
		setupHistory();

		setupBootbox();

		initServices();

		createViews();

		removeLoadingImage();
	}

	private void removeLoadingImage() {
		// Remove loadingImage from Host HTML page
		RootPanel.getBodyElement()
				.removeChild(RootPanel.get(HOST_LOADING_IMAGE).getElement());
	}

	private void initServices() {
		ServicePreparator servicePreparator = injector.getServicePreparator();
		servicePreparator.prepare();
	}

	private void setupHistory() {
		History.newItem(HISTORY_MAIN);

		// Add history listener
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				if (!token.equals(HISTORY_MAIN)) {
					History.newItem(HISTORY_MAIN);
				}
			}
		});
	}

	private void createViews() {
		// Views
		logger.info("Create Views begins...");

		MainPanelView mainPanelView = injector.getMainPanelView();
		mainPanelView.setContentAreaVisible(false);

		mainPanelView.addWidget(WidgetName.PERSONLIST,
				injector.getPersonPanelView());

		mainPanelView.setContentAreaVisible(true);
		mainPanelView.updatePersonPanelView();

		RootPanel.get().add(mainPanelView);

		logger.info("Create Views ends...");
	}

	private void setupBootbox() {
		if (LocaleInfo.getCurrentLocale().getLocaleName().equals(LOCALE)) {
			logger.info(
					"Locale: " + LocaleInfo.getCurrentLocale().getLocaleName());
			Bootbox.setLocale(BootboxLocale.DE);
		}
	}
}
