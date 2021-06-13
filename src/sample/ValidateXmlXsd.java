package sample;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.File;
import java.io.IOException;

public class ValidateXmlXsd {
    public static void main(String[] args) {
        try{
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("metropolitan.xsd"));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new XsdErrorHandler());
            validator.validate(new StreamSource(new File("metropolitan.xml")));
            System.out.println("Validation is successful");
        }
        catch (SAXException e) {
            System.out.println("Validation is failed: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

