package landakoop.landabus.mainapp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="erabiltzailea")
public class Erabiltzailea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="erabiltzaileaID")	
	int id;
	
	@Column(name="izena")
	String izena;
	
	@Column(name="mota")
	String mota;
	
	@Column(name="erabiltzailea")
	String erabiltzailea;
	
	@Column(name="telegramID")
	String telegramID;
	
	@OneToMany(mappedBy = "erabiltzailea", cascade=CascadeType.ALL)
	Set<AutobusGeldialdia> autobusGeldialdia;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(String erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}

	public String getTelegramID() {
		return telegramID;
	}

	public void setTelegramID(String telegramID) {
		this.telegramID = telegramID;
	}

	public Set<AutobusGeldialdia> getAutobusGeldialdia() {
		return autobusGeldialdia;
	}

	public void setAutobusGeldialdia(Set<AutobusGeldialdia> autobusGeldialdia) {
		this.autobusGeldialdia = autobusGeldialdia;
	}
}
