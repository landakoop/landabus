package landakoop.landabus.beans;

public class Geltokia {
	long id;
	String izena;
	
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

	@Override
	public String toString() {
		return "Geltokia [id= " + id + ", izena=" + izena + "]";
	}
	

}
