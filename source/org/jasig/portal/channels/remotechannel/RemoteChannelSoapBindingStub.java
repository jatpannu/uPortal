/**
 * Copyright � 2002 The JA-SIG Collaborative.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the JA-SIG Collaborative
 *    (http://www.jasig.org/)."
 *
 * THIS SOFTWARE IS PROVIDED BY THE JA-SIG COLLABORATIVE "AS IS" AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE JA-SIG COLLABORATIVE OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.jasig.portal.channels.remotechannel;

import org.apache.axis.client.Stub;
import org.apache.axis.client.Call;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.w3c.dom.Element;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Map;
import java.net.URL;
import java.rmi.RemoteException;
import javax.xml.rpc.Service;
import javax.xml.rpc.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.servlet.http.Cookie;

/**
 * RemoteChannelSoapBindingStub.java was originally generated by
 * WSDL2JAVA from Apache Axis.
 * @author Ken Weiner, kweiner@interactivebusiness.com
 * @version $Revision$
 */
public class RemoteChannelSoapBindingStub extends Stub implements RemoteChannel {
  private Vector cachedSerClasses = new Vector();
  private Vector cachedSerQNames = new Vector();
  private Vector cachedSerFactories = new Vector();
  private Vector cachedDeserFactories = new Vector();

  private static final String methodNS = "RemoteChannel";

  public RemoteChannelSoapBindingStub() throws AxisFault {
    this(null);
  }

  public RemoteChannelSoapBindingStub(URL endpointURL, Service service) throws AxisFault {
    this(service);
    super.cachedEndpoint = endpointURL;
  }

  public RemoteChannelSoapBindingStub(Service service) throws AxisFault {
    try {
      if (service == null) {
        super.service = new org.apache.axis.client.Service();
      } else {
        super.service = service;
      }
      Class cls;
      javax.xml.rpc.namespace.QName qName;
      Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
      Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
      Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
      Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
      Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
      Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
      Class simplesf = org.apache.axis.encoding.ser.SimpleNonPrimitiveSerializerFactory.class;
      Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
      qName = new javax.xml.rpc.namespace.QName("http://http.servlet.javax", "Cookie");
      cachedSerQNames.add(qName);
      cls = javax.servlet.http.Cookie.class;
      cachedSerClasses.add(cls);
      cachedSerFactories.add(beansf);
      cachedDeserFactories.add(beandf);

      qName = new javax.xml.rpc.namespace.QName(methodNS, "ArrayOf_tns2_Cookie");
      cachedSerQNames.add(qName);
      cls = javax.servlet.http.Cookie[].class;
      cachedSerClasses.add(cls);
      cachedSerFactories.add(arraysf);
      cachedDeserFactories.add(arraydf);
    }
    catch(java.lang.Exception t) {
      throw AxisFault.makeFault(t);
    }
  }

  private Call getCall() throws RemoteException {
    try {
      Call call = (Call) super.service.createCall();
      if (super.maintainSessionSet) {
        call.setMaintainSession(super.maintainSession);
      }
      if (super.cachedUsername != null) {
        call.setUsername(super.cachedUsername);
      }
      if (super.cachedPassword != null) {
        call.setPassword(super.cachedPassword);
      }
      if (super.cachedEndpoint != null) {
        call.setTargetEndpointAddress(super.cachedEndpoint);
      }
      if (super.cachedTimeout != null) {
        call.setTimeout(super.cachedTimeout);
      }
      Enumeration keys = super.cachedProperties.keys();
      while (keys.hasMoreElements()) {
        String key = (String) keys.nextElement();
        call.setProperty(key, super.cachedProperties.get(key));
      }
      // All the type mapping information is registered
      // when the first call is made.
      // The type mapping information is actually registered in
      // the TypeMappingRegistry of the service, which
      // is the reason why registration is only needed for the first call.
      if (firstCall()) {
        // must set encoding style before registering serializers
        call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP_ENC);
        for (int i = 0; i < cachedSerFactories.size(); ++i) {
          Class cls = (Class) cachedSerClasses.get(i);
          javax.xml.rpc.namespace.QName qName = (javax.xml.rpc.namespace.QName) cachedSerQNames.get(i);
          Class sf = (Class)cachedSerFactories.get(i);
          Class df = (Class)cachedDeserFactories.get(i);
          call.registerTypeMapping(cls, qName, sf, df, false);
        }
      }
      return call;
    }
    catch (java.lang.Throwable t) {
      throw new AxisFault("Failure trying to get the Call object", t);
    }
  }

  public void authenticate(String username, String password) throws RemoteException, org.jasig.portal.channels.remotechannel.Exception{
    if (super.cachedEndpoint == null) {
      throw new NoEndPointException();
    }
    Call call = getCall();
    QName p0QName = new QName("", "username");
    call.addParameter(p0QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    QName p1QName = new QName("", "password");
    call.addParameter(p1QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    call.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
    call.setUseSOAPAction(true);
    call.setSOAPActionURI("");
    call.setOperationStyle("rpc");
    call.setOperationName(new QName(methodNS, "authenticate"));

    Object resp = call.invoke(new Object[] {username, password});

    if (resp instanceof RemoteException) {
      throw (RemoteException)resp;
    }
  }

  public String instantiateChannel(String fname) throws RemoteException, org.jasig.portal.channels.remotechannel.Exception {
    if (super.cachedEndpoint == null) {
      throw new NoEndPointException();
    }
    Call call = getCall();
    QName p0QName = new QName("", "fname");
    call.addParameter(p0QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    call.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    call.setUseSOAPAction(true);
    call.setSOAPActionURI("");
    call.setOperationStyle("rpc");
    call.setOperationName(new QName(methodNS, "instantiateChannel"));

    Object resp = call.invoke(new Object[] {fname});

    if (resp instanceof RemoteException) {
      throw (RemoteException)resp;
    }
    else {
      return (String)resp;
    }
  }

  public Element renderChannel(String instanceId, Map headers, Cookie[] cookies, Map parameters, String baseActionURL) throws RemoteException, org.jasig.portal.channels.remotechannel.Throwable {
    if (super.cachedEndpoint == null) {
        throw new org.apache.axis.NoEndPointException();
    }
    Call call = getCall();
    QName p0QName = new QName("", "instanceId");
    call.addParameter(p0QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    QName p1QName = new QName("", "headers");
    call.addParameter(p1QName, new QName("http://xml.apache.org/xml-soap", "Map"), ParameterMode.IN);
    QName p2QName = new QName("", "cookies");
    call.addParameter(p2QName, new QName(methodNS, "ArrayOf_tns2_Cookie"), ParameterMode.IN);
    QName p3QName = new QName("", "parameters");
    call.addParameter(p3QName, new QName("http://xml.apache.org/xml-soap", "Map"), ParameterMode.IN);
    QName p4QName = new QName("", "baseActionURL");
    call.addParameter(p4QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    call.setReturnType(new QName("http://xml.apache.org/xml-soap", "Element"));
    call.setUseSOAPAction(true);
    call.setSOAPActionURI("");
    call.setOperationStyle("rpc");
    call.setOperationName(new QName(methodNS, "renderChannel"));

    Object resp = call.invoke(new Object[] {instanceId, headers, cookies, parameters, baseActionURL});

    if (resp instanceof RemoteException) {
      throw (RemoteException)resp;
    }
    else {
      return (Element)resp;
    }
  }

  public void freeChannel(String instanceId) throws RemoteException, org.jasig.portal.channels.remotechannel.Exception{
    if (super.cachedEndpoint == null) {
      throw new NoEndPointException();
    }
    Call call = getCall();
    QName p0QName = new javax.xml.rpc.namespace.QName("", "instanceId");
    call.addParameter(p0QName, new QName("http://www.w3.org/2001/XMLSchema", "string"), ParameterMode.IN);
    call.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
    call.setUseSOAPAction(true);
    call.setSOAPActionURI("");
    call.setOperationStyle("rpc");
    call.setOperationName(new QName(methodNS, "freeChannel"));

    Object resp = call.invoke(new Object[] {instanceId});

    if (resp instanceof RemoteException) {
      throw (RemoteException)resp;
    }
  }

  public void logout() throws RemoteException, org.jasig.portal.channels.remotechannel.Exception{
    if (super.cachedEndpoint == null) {
      throw new NoEndPointException();
    }
    Call call = getCall();
    call.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
    call.setUseSOAPAction(true);
    call.setSOAPActionURI("");
    call.setOperationStyle("rpc");
    call.setOperationName(new QName(methodNS, "logout"));

    Object resp = call.invoke(new Object[] {});

    if (resp instanceof RemoteException) {
      throw (RemoteException)resp;
    }
  }
}
