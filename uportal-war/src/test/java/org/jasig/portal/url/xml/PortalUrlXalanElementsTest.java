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

package org.jasig.portal.url.xml;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.easymock.EasyMock;
import org.jasig.portal.url.IBasePortalUrl;
import org.jasig.portal.url.IPortalUrlProvider;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class PortalUrlXalanElementsTest extends TestCase {

    public void testPortalUrlElement() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        
        
        
        
        final IBasePortalUrl basePortalUrl = EasyMock.createMock(IBasePortalUrl.class);
        basePortalUrl.addPortalParameter("foo", "bar");
        basePortalUrl.addPortalParameter("foo", "bor");
        basePortalUrl.addPortalParameter("page", "42");
        basePortalUrl.addPortalParameter("node", "element");
        basePortalUrl.addPortalParameter("empty", "");
        EasyMock.expectLastCall(); //works for all previous void calls
        
        EasyMock.expect(basePortalUrl.getUrlString()).andReturn("/uPortal/home/normal/render.uP");

        
        final IPortalUrlProvider portalUrlProvider = EasyMock.createMock(IPortalUrlProvider.class);
        
        EasyMock.expect(portalUrlProvider.getDefaultUrl(request)).andReturn(basePortalUrl);
        
        
        EasyMock.replay(portalUrlProvider, basePortalUrl);
        
        
        
        final TransformerFactory tFactory = TransformerFactory.newInstance();
        final Transformer transformer = tFactory.newTransformer(new StreamSource(this.getClass().getResourceAsStream("portalUrlTest.xsl")));
        
        //Setup the transformer parameters
        transformer.setParameter(PortalUrlXalanElements.PORTAL_URL_PROVIDER_PARAMETER, portalUrlProvider);
        transformer.setParameter(PortalUrlXalanElements.CURRENT_PORTAL_REQUEST, request);
        
        
        final StringWriter resultWriter = new StringWriter();

        // set up configuration in the transformer impl
        final StreamSource sourceStream = new StreamSource(this.getClass().getResourceAsStream("test.xml"));
        transformer.transform(sourceStream, new StreamResult(resultWriter));
        
        final String result = resultWriter.getBuffer().toString();
        final String expected = IOUtils.toString(this.getClass().getResourceAsStream("portalUrlResult.xml"));

        XMLUnit.setIgnoreWhitespace(true);
        Diff d = new Diff(expected, result);
        assertTrue("Transformation result differs from what's expected" + d, d.similar());
        
        EasyMock.verify(portalUrlProvider, basePortalUrl);
    }
}
