//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.04 at 03:11:32 PM CEST 
//


package landakoop.landabus.mainapp.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autobusGeldialdiaSoap" type="{http://mainapp.landabus.landakoop/soap}autobusGeldialdiaSoap"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "autobusGeldialdiaSoap"
})
@XmlRootElement(name = "autobusGeldialdiaResponse")
public class AutobusGeldialdiaResponse {

    @XmlElement(required = true)
    protected AutobusGeldialdiaSoap autobusGeldialdiaSoap;

    /**
     * Gets the value of the autobusGeldialdiaSoap property.
     * 
     * @return
     *     possible object is
     *     {@link AutobusGeldialdiaSoap }
     *     
     */
    public AutobusGeldialdiaSoap getAutobusGeldialdiaSoap() {
        return autobusGeldialdiaSoap;
    }

    /**
     * Sets the value of the autobusGeldialdiaSoap property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutobusGeldialdiaSoap }
     *     
     */
    public void setAutobusGeldialdiaSoap(AutobusGeldialdiaSoap value) {
        this.autobusGeldialdiaSoap = value;
    }

}
