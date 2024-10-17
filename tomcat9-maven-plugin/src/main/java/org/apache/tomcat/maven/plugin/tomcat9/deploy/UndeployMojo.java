package org.apache.tomcat.maven.plugin.tomcat9.deploy;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.tomcat.maven.common.deployer.TomcatManagerException;
import org.apache.tomcat.maven.common.deployer.TomcatManagerResponse;
import org.apache.tomcat.maven.plugin.tomcat9.AbstractWarCatalinaMojo;

import java.io.IOException;

/**
 * Undeploy a WAR from Tomcat.
 *
 * @since 2.1
 */
@Mojo( name = "undeploy", threadSafe = true )
public class UndeployMojo
    extends AbstractWarCatalinaMojo
{
    // ----------------------------------------------------------------------
    // Mojo Parameters
    // ----------------------------------------------------------------------

    /**
     * Whether to fail the build if the web application cannot be undeployed.
     */
    @Parameter( property = "maven.tomcat.failOnError", defaultValue = "true" )
    private boolean failOnError;

    // ----------------------------------------------------------------------
    // Protected Methods
    // ----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    protected void invokeManager()
        throws MojoExecutionException, TomcatManagerException, IOException
    {
        getLog().info( messagesProvider.getMessage( "UndeployMojo.undeployingApp", getDeployedURL() ) );

        try
        {

            TomcatManagerResponse tomcatResponse = getManager().undeploy( getPath() );

            checkTomcatResponse( tomcatResponse );

            log( tomcatResponse.getHttpResponseBody() );

        }
        catch ( TomcatManagerException e )
        {
            if ( failOnError )
            {
                throw e;
            }

            getLog().warn( messagesProvider.getMessage( "UndeployMojo.undeployError", e.getMessage() ) );
        }
    }

}
