#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package ${package}.provider;

import java.util.Map;
import java.util.HashMap;
import org.opendaylight.controller.sal.binding.api.AbstractBindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.data.DataBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderActivator extends AbstractBindingAwareProvider {
    private static final Logger log = LoggerFactory.getLogger(ProviderActivator.class);

    private AppProvider appProvider;

    public ProviderActivator() {
      appProvider = new AppProvider();
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        log.info("Provider Session initialized");
        DataBrokerService dataService = session.<DataBrokerService>getSALService(DataBrokerService.class);
        appProvider.setDataService(dataService);
    }
}
