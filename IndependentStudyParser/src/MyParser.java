import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


//http://en.wikipedia.org/wiki/Java_API_for_XML_Processing

public class MyParser {
	public void parse(InputStream is) throws ParserConfigurationException, SAXException, IOException
	{
		//Using a SAX Parser for a faster way to parser XML
		XMLHandler handler = new XMLHandler();
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware( true );
		SAXParser saxp = spf.newSAXParser();
		saxp.parse( is, handler );
	}

	class XMLHandler extends DefaultHandler {
		//TODO 
	}

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException {
		String xsltResource = 
				"<?xml version='1.0' encoding='UTF-8'?>\n"+
						"<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n"+
						"   <xsl:output method='xml' indent='no'/>\n"+
						"   <xsl:template match='/'>\n"+
						"      <reRoot><reNode><xsl:value-of select='/root/node/@val' /> world</reNode></reRoot>\n"+
						"   </xsl:template>\n"+
						"</xsl:stylesheet>";
		String xmlSourceResource =
				"<?xml version='1.0' encoding='UTF-8'?>\n"+
						"<root><node val='hello'/></root>";

		StringWriter xmlResultResource = new StringWriter();

		Transformer xmlTransformer = TransformerFactory.newInstance().newTransformer(
				new StreamSource(new StringReader(xsltResource))
				);

		xmlTransformer.transform(
				new StreamSource(new StringReader(xmlSourceResource)), new StreamResult(xmlResultResource)
				);

		System.out.println(xmlResultResource.getBuffer().toString());
	}
}