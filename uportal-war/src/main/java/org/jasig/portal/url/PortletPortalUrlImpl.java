/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portal.url;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.portlet.om.IPortletWindow;
import org.jasig.portal.portlet.om.IPortletWindowId;

/**
 * Implementation of a portlet URL
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
class PortletPortalUrlImpl extends AbstractPortalUrl implements IPortletPortalUrl {

    protected final Log logger = LogFactory.getLog(this.getClass());
    
    private final IPortletWindow portletWindow;
    private final TYPE urlType;
    
    private final Map<String, String[]> portletParameters = new ParameterMap();
    private final Map<String, String[]> publicRenderParameters = new ParameterMap();
    private final Map<String, List<String>> properties = new LinkedHashMap<String, List<String>>();
    
    private String cacheability;
    private PortletMode portletMode;
    private WindowState windowState;
    private Boolean secure;
    private String resourceId;
    private IPortletPortalUrl delegatePortletUrl;
    
    public PortletPortalUrlImpl(TYPE type, IPortletWindow portletWindow, HttpServletRequest httpServletRequest, IUrlGenerator urlGenerator) {
        super(httpServletRequest, urlGenerator);
        
        Validate.notNull(portletWindow, "portletWindow can not be null");
        Validate.notNull(type, "type can not be null");
        
        this.portletWindow = portletWindow;
        this.urlType = type;
    }
    
    @Override
    public IPortletWindowId getTargetWindowId() {
        return this.portletWindow.getPortletWindowId();
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#getPortletMode()
     */
    public PortletMode getPortletMode() {
        return this.portletMode;
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortletPortalUrl#getPortletParameters()
     */
    @Override
    public Map<String, String[]> getPortletParameters() {
        return this.portletParameters;
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#getWindowState()
     */
    public WindowState getWindowState() {
        return this.windowState;
    }
    
    /* (non-Javadoc)
     * @see org.apache.pluto.container.PortletURLProvider#getType()
     */
    @Override
    public TYPE getType() {
        return this.urlType;
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#setPortletMode(javax.portlet.PortletMode)
     */
    public void setPortletMode(PortletMode portletMode) {
        this.portletMode = portletMode;
    }
    
    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#addPortletParameter(java.lang.String, java.lang.String[])
     */
    public void addPortletParameter(String name, String... values) {
        Validate.notNull(name, "name can not be null");
        Validate.noNullElements(values, "values can not be null or contain null elements");
        
        final String[] valuesList = this.portletParameters.get(name);
        
        final String[] newValuesList;
        final int copyStart;
        if (valuesList == null) {
            newValuesList = new String[values.length];
            copyStart = 0;
        }
        else {
            newValuesList = new String[valuesList.length + values.length];
            copyStart = valuesList.length;
            System.arraycopy(valuesList, 0, newValuesList, 0, copyStart);
        }
        
        System.arraycopy(values, 0, newValuesList, copyStart, values.length);
        
        this.portletParameters.put(name, valuesList);
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#setPortletParameter(java.lang.String, java.lang.String[])
     */
    public void setPortletParameter(String name, String... values) {
        Validate.notNull(name, "name can not be null");
        Validate.noNullElements(values, "values can not be null or contain null elements");
        
        final String[] valuesCopy = new String[values.length];
        System.arraycopy(values, 0, valuesCopy, 0, values.length);
        
        this.portletParameters.put(name, valuesCopy);
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#setPortletParameters(java.util.Map)
     */
    public void setPortletParameters(Map<String, String[]> parameters) {
        this.portletParameters.clear();
        this.portletParameters.putAll(parameters);
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IPortalPortletUrl#setWindowState(javax.portlet.WindowState)
     */
    public void setWindowState(WindowState windowState) {
        this.windowState = windowState;
    }
    
    @Override
    public Map<String, String[]> getRenderParameters() {
        return this.portletParameters;
    }

    @Override
    public String getCacheability() {
        return this.cacheability;
    }

    @Override
    public Map<String, List<String>> getProperties() {
        return this.properties;
    }

    @Override
    public Map<String, String[]> getPublicRenderParameters() {
        return this.publicRenderParameters;
    }

    @Override
    public String getResourceID() {
        return this.resourceId;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public void setCacheability(String cacheLevel) {
        this.cacheability = cacheLevel;
    }

    @Override
    public void setResourceID(String resourceID) {
        this.resourceId = resourceID;
    }

    @Override
    public void setSecure(boolean secure) throws PortletSecurityException {
        this.secure = secure;
    }

    @Override
    public String toURL() {
        return this.getUrlString();
    }

    @Override
    public void write(Writer out, boolean escapeXML) throws IOException {
        final String url = this.getUrlString();
        if (escapeXML) {
            StringEscapeUtils.escapeXml(out, url);
        }
        else {
            out.write(url);
        }
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.url.IBasePortalUrl#getUrlString()
     */
    public String getUrlString() {
        return this.urlGenerator.generatePortletUrl(this.request, this);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PortletPortalUrl [portletWindow=" + this.portletWindow + ", portletMode=" + this.portletMode
                + ", windowState=" + this.windowState + ", portletParameters=" + this.portletParameters
                + ", properties=" + this.properties + ", cacheability=" + this.cacheability + ", resourceId="
                + this.resourceId + ", secure=" + this.secure + ", publicRenderParameters="
                + this.publicRenderParameters + ", delegatePortletUrl=" + this.delegatePortletUrl + ", urlType="
                + this.urlType + ", portalParameters=" + this.portalParameters + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.cacheability == null) ? 0 : this.cacheability.hashCode());
        result = prime * result + ((this.delegatePortletUrl == null) ? 0 : this.delegatePortletUrl.hashCode());
        result = prime * result + ((this.portletMode == null) ? 0 : this.portletMode.hashCode());
        result = prime * result + ((this.portletParameters == null) ? 0 : this.portletParameters.hashCode());
        result = prime * result + ((this.portletWindow == null) ? 0 : this.portletWindow.hashCode());
        result = prime * result + ((this.properties == null) ? 0 : this.properties.hashCode());
        result = prime * result + ((this.publicRenderParameters == null) ? 0 : this.publicRenderParameters.hashCode());
        result = prime * result + ((this.resourceId == null) ? 0 : this.resourceId.hashCode());
        result = prime * result + ((this.secure == null) ? 0 : this.secure.hashCode());
        result = prime * result + ((this.urlType == null) ? 0 : this.urlType.hashCode());
        result = prime * result + ((this.windowState == null) ? 0 : this.windowState.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PortletPortalUrlImpl other = (PortletPortalUrlImpl) obj;
        if (this.cacheability == null) {
            if (other.cacheability != null) {
                return false;
            }
        }
        else if (!this.cacheability.equals(other.cacheability)) {
            return false;
        }
        if (this.delegatePortletUrl == null) {
            if (other.delegatePortletUrl != null) {
                return false;
            }
        }
        else if (!this.delegatePortletUrl.equals(other.delegatePortletUrl)) {
            return false;
        }
        if (this.portletMode == null) {
            if (other.portletMode != null) {
                return false;
            }
        }
        else if (!this.portletMode.equals(other.portletMode)) {
            return false;
        }
        if (this.portletParameters == null) {
            if (other.portletParameters != null) {
                return false;
            }
        }
        else if (!this.portletParameters.equals(other.portletParameters)) {
            return false;
        }
        if (this.portletWindow == null) {
            if (other.portletWindow != null) {
                return false;
            }
        }
        else if (!this.portletWindow.equals(other.portletWindow)) {
            return false;
        }
        if (this.properties == null) {
            if (other.properties != null) {
                return false;
            }
        }
        else if (!this.properties.equals(other.properties)) {
            return false;
        }
        if (this.publicRenderParameters == null) {
            if (other.publicRenderParameters != null) {
                return false;
            }
        }
        else if (!this.publicRenderParameters.equals(other.publicRenderParameters)) {
            return false;
        }
        if (this.resourceId == null) {
            if (other.resourceId != null) {
                return false;
            }
        }
        else if (!this.resourceId.equals(other.resourceId)) {
            return false;
        }
        if (this.secure == null) {
            if (other.secure != null) {
                return false;
            }
        }
        else if (!this.secure.equals(other.secure)) {
            return false;
        }
        if (this.urlType == null) {
            if (other.urlType != null) {
                return false;
            }
        }
        else if (!this.urlType.equals(other.urlType)) {
            return false;
        }
        if (this.windowState == null) {
            if (other.windowState != null) {
                return false;
            }
        }
        else if (!this.windowState.equals(other.windowState)) {
            return false;
        }
        return true;
    }
}
