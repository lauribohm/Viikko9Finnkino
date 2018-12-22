package com.example.lauribohm.javafinkkariviikko9;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static java.lang.Integer.parseInt;

public class readXMLFile {

    private static readXMLFile XML = new readXMLFile();

    public static readXMLFile getInstance(){
        return XML;
    }

    ArrayList<Theatres> teatteriPointer = new ArrayList<Theatres>();
    ArrayList teatteritLista = new ArrayList();

    public ArrayList teatterit(){

        String id;
        String teatteri;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";

            URL url = new URL("https://www.finnkino.fi/xml/TheatreAreas/");
            Document doc = builder.parse(String.valueOf(url));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i= 0; i < nList.getLength(); i++){

                Node node = nList.item(i);

                Theatres teatterii = new Theatres();

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    id = element.getElementsByTagName("ID").item(0).getTextContent();

                    teatteri = element.getElementsByTagName("Name").item(0).getTextContent();

                    teatterii.setInfo(teatteri, id);
                    teatteriPointer.add(teatterii);

                    teatteritLista.add(teatteri);
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teatteritLista;
    }

    public void findSchedule (String ValittuPV, TextView Kokoshitti, String ValittuTeatteri, String alkuA, String loppuA) {

        int haluttu = 0;
        String tulos = "";

        ArrayList tulostettava = new ArrayList();

        String id = "";

        for (int i = 1; i < teatteriPointer.size(); i++) {

            if (teatteriPointer.get(i).getName().equals(ValittuTeatteri)) {
                id = teatteriPointer.get(i).getID();
            }
        }

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String osoite = "http://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + ValittuPV;

            Document dok = builder.parse(osoite);
            dok.getDocumentElement().normalize();

            NodeList nlist = dok.getDocumentElement().getElementsByTagName("Show");

            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);
                Element element = (Element) node;


                if (node.getNodeType() == node.ELEMENT_NODE) {

                    String teatteri = element.getElementsByTagName("Theatre").item(0).getTextContent();
                    String elokuva = element.getElementsByTagName("Title").item(0).getTextContent();
                    String aika = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();

                    if (alkuA == null && loppuA == null) {
                        tulos = tulos + teatteri + "elokuva: " + elokuva + " Klo: " + aika;
                        tulos = tulos + "\n\n";
                    } else {

                        String[] taAika = alkuA.split(":");
                        int taAika1 = parseInt(taAika[0]);
                        int taAika2 = parseInt(taAika[1]);

                        String[] tlAika = loppuA.split(":");
                        int tlAika1 = parseInt(tlAika[0]);
                        int tlAika2 = parseInt(tlAika[1]);


                        String[] anAika = aika.split("T");
                        String anAika1 = (anAika[1]);

                        String[] anAika2 = anAika1.trim().split(":");
                        String anAika3 = (anAika2[0]);
                        String anAika4 = (anAika2[1]);

                        int vertaaAika1 = parseInt(anAika3);
                        int vertaaAika2 = parseInt(anAika4);

                        System.out.println(vertaaAika1);

                        System.out.println(taAika1 + "   " + tlAika1);

                        if (vertaaAika1 > taAika1 && vertaaAika1 < tlAika1) {

                            //System.out.println(teatteri + elokuva + " Klo: " + aika);
                            tulos = tulos + teatteri + "elokuva: " + elokuva + " Klo: " + aika;
                            tulos = tulos + "\n\n";
                        }
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } //catch (ParseException e) {
        //e.printStackTrace();
        finally {

            Kokoshitti.setMovementMethod(new ScrollingMovementMethod());
            Kokoshitti.setText(tulos);
        }

    }
}
