package landakoop.landabus.mainapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Linea;

public interface LineaDao extends CrudRepository<Linea,Long> {
	
	@Query(value="SELECT denbora "
			+ "from (linea_geltokiak as lg join distantzia d on d.geltokiaA = ?2 "
			+ "where d.geltokiaB = ?3 and lg.lineaID = ?3 ", nativeQuery=true)
	int getDistantzia(long lineaID, long geltokiaA, long geltokiaB);
}
