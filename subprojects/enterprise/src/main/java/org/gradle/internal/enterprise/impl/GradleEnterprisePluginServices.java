/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.enterprise.impl;

import org.gradle.StartParameter;
import org.gradle.api.internal.GradleInternal;
import org.gradle.internal.enterprise.GradleEnterprisePluginConfig;
import org.gradle.internal.scan.BuildScanServices;
import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.scopes.AbstractPluginServiceRegistry;

public final class GradleEnterprisePluginServices extends AbstractPluginServiceRegistry {

    @Override
    public void registerBuildTreeServices(ServiceRegistration registration) {
        registration.add(GradleEnterprisePluginManager.class);
        registration.add(DefaultGradleEnterprisePluginBuildState.class);
        registration.add(DefaultGradleEnterprisePluginRequiredServices.class);
        registration.addProvider(new Object() {
            GradleEnterprisePluginConfig createGradleEnterprisePluginConfig(StartParameter startParameter, GradleInternal gradle) {
                return new DefaultGradleEnterprisePluginConfig(
                    DefaultGradleEnterprisePluginConfig.buildScanRequest(startParameter),
                    gradle.getBuildType() == GradleInternal.BuildType.TASKS
                );
            }
        });

        registration.addProvider(new BuildScanServices());
    }

}
