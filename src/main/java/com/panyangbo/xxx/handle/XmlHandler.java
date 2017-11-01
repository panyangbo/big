package com.panyangbo.xxx.handle;

import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/20 15:54
 * @Description XmlHandler
 */
public class XmlHandler<T> {

	   private IBindingFactory factory = null;
	   private StringWriter writer = null;
	   private StringReader reader = null;
	   private final static String CHARSET_NAME = "UTF-8";
	   
	   public String encode2Xml(T t) throws JiBXException, IOException{
		   factory = BindingDirectory.getFactory(t.getClass());
		   writer = new StringWriter();
		   IMarshallingContext mctx = factory.createMarshallingContext();
		   mctx.setIndent(2);
		   mctx.marshalDocument(t, CHARSET_NAME, null, writer);
		   String xmlstr = writer.toString();
		   writer.close();
		   return xmlstr;
	   } 
	   

	@SuppressWarnings("unchecked")
	  public T decode2Order(String xmlBody) throws JiBXException {
		   reader = new StringReader(xmlBody);
		   IUnmarshallingContext uctx = factory.createUnmarshallingContext();
		   T t = (T) uctx.unmarshalDocument(reader);
		   return t;
	   }
}
