//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.02 at 05:44:30 PM CEST 
//


package landakoop.landabus.mainapp.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for autobusGeldialdia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="autobusGeldialdia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="erabiltzailea" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ibilbidea" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="geltokia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ekintza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="noiz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autobusGeldialdia", propOrder = {
    "erabiltzailea",
    "ibilbidea",
    "geltokia",
    "ekintza",
    "noiz"
})
public class AutobusGeldialdia {

    protected int erabiltzailea;
    protected int ibilbidea;
    protected int geltokia;
    @XmlElement(required = true)
    protected String ekintza;
    protected long noiz;

    /**
     * Gets the value of the erabiltzailea property.
     * 
     */
    public int getErabiltzailea() {
        return erabiltzailea;
    }

    /**
     * Sets the value of the erabiltzailea property.
     * 
     */
    public void setErabiltzailea(int value) {
        this.erabiltzailea = value;
    }

    /**
     * Gets the value of the ibilbidea property.
     * 
     */
    public int getIbilbidea() {
        return ibilbidea;
    }

    /**
     * Sets the value of the ibilbidea property.
     * 
     */
    public void setIbilbidea(int value) {
        this.ibilbidea = value;
    }

    /**
     * Gets the value of the geltokia property.
     * 
     */
    public int getGeltokia() {
        return geltokia;
    }

    /**
     * Sets the value of the geltokia property.
     * 
     */
    public void setGeltokia(int value) {
        this.geltokia = value;
    }

    /**
     * Gets the value of the ekintza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEkintza() {
        return ekintza;
    }

    /**
     * Sets the value of the ekintza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEkintza(String value) {
        this.ekintza = value;
    }

    /**
     * Gets the value of the noiz property.
     * 
     */
    public long getNoiz() {
        return noiz;
    }

    /**
     * Sets the value of the noiz property.
     * 
     */
    public void setNoiz(long value) {
        this.noiz = value;
    }

}
