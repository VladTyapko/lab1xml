package sample;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlWorker {
    private final  Metropolitan metropolitan;

    public ArrayList<Line> getLines() {
        return metropolitan.getLines();
    }

    XmlWorker() throws ParserConfigurationException, IOException, SAXException {
        metropolitan = new Metropolitan();
        File inputFile = new File("metropolitan.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("line");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element lineElement = (Element) nNode;
                Line line = new Line(Integer.valueOf(lineElement.getAttribute("line_id")),
                        lineElement.getAttribute("color"));
                metropolitan.addLine(line);
                NodeList stations = lineElement.getElementsByTagName("station");

                for (int j = 0; j < stations.getLength(); j++) {
                    Node fNode = stations.item(j);

                    if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element stationElement = (Element) fNode;
                        Station station = new Station(Integer.parseInt(stationElement.getAttribute("station_id")),
                                stationElement.getAttribute("name"));
                        line.addStation(station);
                    }
                }

            }
        }
    }

    public void saveToXml() {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("metropolitan");
            document.appendChild(root);
            for (int i = 0; i < metropolitan.countLines(); i++) {
                Element lineElement = document.createElement("line");
                lineElement.setAttribute("line_id", String.valueOf(metropolitan.getLines().get(i).line_id));
                lineElement.setAttribute("name", metropolitan.getLines().get(i).color);
                for (int j = 0; j < metropolitan.getLines().get(i).getStations().size(); j++) {
                    Station station = metropolitan.getLines().get(i).getStations().get(j);
                    Element stationElement = document.createElement("station");
                    stationElement.setAttribute("id", String.valueOf(station.station_id));
                    stationElement.setAttribute("name", station.name);
                    lineElement.appendChild(stationElement);
                }
                root.appendChild(lineElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("metropolitan.xml"));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void addLine(Line line) {
        metropolitan.addLine(line);
    }

    public void addStationByLine(int code, Station station) {
        Line line = metropolitan.getLine(code);
        line.addStation(station);
    }

    public Line findStationByLine(int code) {
        return metropolitan.getLine(code);
    }

    public void updateLine(int line_id, int newid, String newColor) {
        Line line = metropolitan.getLine(line_id);
        line.color = newColor;
        line.line_id = newid;
    }


    public void deleteLine(int line_id) {
        Line line = metropolitan.getLine(line_id);
        getLines().remove(line);
    }

    public void deleteStation(int line_id, int station_id){
        Line line = metropolitan.getLine(line_id);
        Station station = line.findStationById(station_id);
        line.getStations().remove(station);
    }

}
