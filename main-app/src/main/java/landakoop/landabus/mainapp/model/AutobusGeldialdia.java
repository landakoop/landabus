package landakoop.landabus.mainapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import landakoop.landabus.mainapp.json.ErabiltzaileaSimpleSerializer;
import landakoop.landabus.mainapp.json.GeltokiaSimpleSerializer;
import landakoop.landabus.mainapp.json.IbilbideaSimpleSerializer;

@Entity
@Table(name="ibilbidea_geltokia_bidaiaria")
public class AutobusGeldialdia {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	long id;	
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "bidaiariaid")
	@JsonSerialize(using = ErabiltzaileaSimpleSerializer.class)
	@NotNull
    Erabiltzailea erabiltzailea = new Erabiltzailea();
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "ibilbideaid")
	@JsonSerialize(using = IbilbideaSimpleSerializer.class)
	@NotNull
    Ibilbidea ibilbidea = new Ibilbidea();
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "geltokiaid")
	@JsonSerialize(using = GeltokiaSimpleSerializer.class)
	@NotNull
    Geltokia geltokia = new Geltokia();
	
	@Column(name="ekintza")
	@NotNull
	String ekintza;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timestamp")
	@NotNull
	Date noiz;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEkintza() {
		return ekintza;
	}

	public void setEkintza(String ekintza) {
		this.ekintza = ekintza;
	}
	
	public Date getNoiz() {
		return noiz;
	}

	public void setNoiz(Date noiz) {
		this.noiz = noiz;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(int erabiltzailea) {
		this.erabiltzailea.setId(erabiltzailea);
	}

	public Ibilbidea getIbilbidea() {
		return ibilbidea;
	}

	public void setIbilbidea(int ibilbidea) {
		this.ibilbidea.setId(ibilbidea);
	}

	public Geltokia getGeltokia() {
		return geltokia;
	}

	public void setGeltokia(int geltokia) {
		this.geltokia.setId(geltokia);
	}

	@Override
	public String toString() {
		return "AutobusGeldialdia [id=" + id + ", erabiltzailea=" + erabiltzailea.getId() + ", ibilbidea=" + ibilbidea.getId()
				+ ", geltokia=" + geltokia.getId() + ", ekintza=" + ekintza + ", noiz=" + noiz + "]";
	}
}
