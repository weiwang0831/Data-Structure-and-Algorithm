/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodtreedriver;

/**
 *
 * @author weiwang_ww5
 */
public class ListOfCrimes {

    /**
     * head pointer point
     */
    public Node head;

    /**
     * initiate head=null
     */
    public ListOfCrimes() {
        head = null;
    }

    private int count = 0;

    /**
     *
     * @param count set number of time the function visit
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return pre-condition, a list exist post condition: int return indicates
     * number of nodes in the list
     */
    public int countNodes() {
        int count = 0;
        Node temp = head;
        //when pointer point to null, it reach the end
        while (temp != null) {
            temp = temp.link;
            count++;
        }
        return count;
    }

    /**
     *
     * @param newNode pre condition: newNode exist, list exist post condition:
     * the original list is added with new Node
     */
    public void add(Node newNode) {
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node frontNode = this.head;
            this.head = newNode;
            this.head.link = frontNode;
        }
    }

    /**
     *
     * @return precondition: list exist post condition: get the last node from
     * the list
     */
    public Node getNode() {
        if (this.head == null) {
            return null;
        }
        Node frontNode = this.head;
        this.head = this.head.link;
        return frontNode;
    }

    /**
     *
     * @return precondition: list constructed post condition: return String that
     * include all value in t list
     */
    @Override
    public String toString() {
        String result = "";
        while (this.head != null) {
            result = result + this.getNode().value + "\n";
        }
        return result;
    }

    /**
     *
     * @return precondition: list exist postcondition: KML file contect String
     * return
     */
    public String toKML() {

        //set the beginning of KML document
        String begin = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n"
                + "<kml xmlns=\"http://earth.google.com/kml/2.2\"> \n"
                + "<Document>  \n"
                + " <Style id=\"style1\">  \n"
                + " <IconStyle>   \n"
                + "  <Icon>    \n"
                + "   <href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>   \n"
                + "  </Icon>   \n"
                + " </IconStyle>    \n"
                + "</Style> \n";

        String middle = "";
        //Attached points information into the doc
        while (this.head != null) {
            Node thisNode = this.getNode();
            String element = "<Placemark> \n <name>" + thisNode.value.get(4) + "</name>\n"
                    + "<description>" + thisNode.value.get(3) + "</description>\n"
                    + "<styleUrl>#style1</styleUrl> \n"
                    + "<Point>\n"
                    + " <coordinates>" + thisNode.value.get(8) + "," + thisNode.value.get(7) + ",0.000000</coordinates> \n"
                    + "</Point> \n"
                    + "</Placemark> \n";
            middle = middle + element;
        }

        //The end of the document
        String end = "</Document> \n</kml> ";

        //return appended String
        return begin + middle + end;
    }
}
