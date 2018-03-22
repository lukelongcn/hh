package com.h9.api.provider.handler;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class CDataContentHandler extends XMLSerializer {
    private static final Pattern XML_CHARS = Pattern.compile("[<>&]");
    public CDataContentHandler( OutputStream output, OutputFormat format) {
        super(output,format);
    }
    public void characters(char[] ch, int start, int length) throws SAXException {
        boolean useCData = XML_CHARS.matcher(new String(ch, start,length)).find();
        if (useCData)super.startCDATA();
        super.characters(ch,start,length);
        if(useCData) super.endCDATA();
    }
    public static String ojbectToXmlWithCDATA(Class clazz, Object obj) throws Exception {
        if(obj == null){
            return "";
        }
        JAXBContext context = JAXBContext.newInstance(clazz);
        OutputFormat of = new OutputFormat();
        of.setOmitXMLDeclaration(true);
        of.setPreserveSpace(true);
        of.setIndenting(true);
        ByteArrayOutputStream op = new ByteArrayOutputStream();
        CDataContentHandler serializer = new CDataContentHandler(op, of);
        SAXResult result=new SAXResult(serializer.asContentHandler());
        Marshaller mar=context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(obj, result);
        return op.toString("UTF-8");
    }


}
