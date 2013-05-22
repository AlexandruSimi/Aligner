/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aligner;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Claudiu
 */
public class TextReader {

    private Document doc;
    private int index;
    private NodeList nodeList;

    public TextReader(String path) {
        this.index = 0;
        File xmlFile = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("value");
        } catch (ParserConfigurationException ex) {
            System.err.println("Eroare la parsare!");
        } catch (SAXException ex) {
            System.err.println("Sax Exception!");
        } catch (IOException ex) {
            System.err.println("Exceptie I/O!");
        }
    }

    public String getNext() {
        Node nod = nodeList.item(index);
        index += 1;
        if (nod.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) nod;
            return el.getTextContent();
        }
        index -= 1;
        return null;
    }

    public void setIndex(int newIndex) {
        this.index = newIndex;
    }

    public int getIndex() {
        return this.index;
    }

    public int getByIndex(int newIndex) {
        Node nod = nodeList.item(newIndex);

        if (nod.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) nod;
            return Integer.parseInt(el.getTextContent());
        }

        return -1;
    }

    public int count() {
        return nodeList.getLength();
    }
    
    
}
