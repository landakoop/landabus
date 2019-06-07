package landakoop.landabus.mainapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;

@CrossOrigin
@Entity
@Table(name="linea")
public class Linea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lineaID")
	long id;
	
	@Column(name="izena")
	String izena;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="linea_geltokiak", 
				joinColumns={@JoinColumn(name="lineaID")}, 
				inverseJoinColumns={@JoinColumn(name="geltokiaID")})
	@JsonIgnore
    private List<Geltokia> geltokiak = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public List<Geltokia> getGeltokiak() {
		return geltokiak;
	}

	public void setGeltokiak(List<Geltokia> geltokiak) {
		this.geltokiak = geltokiak;
	}	
	
	public void addGeltokia(Geltokia geltokia) {
		geltokiak.add(geltokia);
	}
}