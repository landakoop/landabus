package landakoop.landabus.mainapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="linea")
public class Linea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lineaID")
	int id;
	
	@Column(name="izena")
	String izena;

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
}