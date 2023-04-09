package fr.nacvs.ied_mediator.sources.film_summary;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHelper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(XmlHelper.class);
	
	private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private final XPathFactory xPathFactory = XPathFactory.newInstance();
	private final XPath xPath = xPathFactory.newXPath();

	public Document loadXml(String xmlString) {
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlString)));
		} catch (ParserConfigurationException | SAXException | IOException exception) {
			LOGGER.error("Cannot parse xml string : " + exception.getMessage());
			throw new IllegalArgumentException(exception);
		}
	}
	
	/**
	 * Find the string present at the location designed by the XPath query.
	 * No result found if path invalid or non-existant.
	 */
	public String findString(Document document, String xPathQuery) {
		return (String) compileAndEvaluate(xPathQuery, document, XPathConstants.STRING);
	}
	
	/**
	 * Find the list of nodes present at the location designed by the XPath query.
	 */
	public NodeList findNodeList(Document document, String xPathQuery) {
		return (NodeList) compileAndEvaluate(xPathQuery, document, XPathConstants.NODESET);
	}
	
	/**
	 * Find the first node of the list present at the location designed by the XPath query.
	 */
	public Node findFirstNode(Document document, String xPathQuery) {
		return (Node) compileAndEvaluate(xPathQuery, document, XPathConstants.NODE);
	}
	
	/**
	 * Find if the resource wanted exists.
	 */
	public boolean anyMatch(Document document, String xPathQuery) {
		return (boolean) compileAndEvaluate(xPathQuery, document, XPathConstants.BOOLEAN);
	}
	
	/**
	 * Find the double present at the location designed by the XPath query.
	 * No result found if path invalid, non-existant or that the value cannot be converted to double.
	 */
	public double findDouble(Document document, String xPathQuery) {
		return (double) compileAndEvaluate(xPathQuery, document, XPathConstants.NUMBER);
	}
	
	/**
	 * Find the boolean present at the location designed by the XPath query and tries to convert it.
	 */
	public boolean findBoolean(Document document, String xPathQuery) {
		return Boolean.parseBoolean(findString(document, xPathQuery));
	}
	
	private Object compileAndEvaluate(String xPathQuery, Document document, QName returnType) {
		XPathExpression expression;
		// Two try/catch in order to provide meaningful error messages
		try {
			expression = xPath.compile(xPathQuery);
		} catch (XPathExpressionException exception) {
			LOGGER.error(xPathQuery);
			LOGGER.error("Error while compiling XPath expression : ", exception.getMessage());
			throw new IllegalArgumentException(exception);
		}
		
		try {
			return expression.evaluate(document, returnType);
		} catch (XPathExpressionException exception) {
			LOGGER.error("Error while evaluating XPath expression : ", exception.getMessage());
			throw new IllegalArgumentException(exception);
		}
	}
}
