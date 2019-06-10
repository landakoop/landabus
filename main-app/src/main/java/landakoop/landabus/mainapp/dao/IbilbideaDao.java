package landakoop.landabus.mainapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.Ibilbidea;
import landakoop.landabus.mainapp.model.rest.ErabileraRest;
import landakoop.landabus.mainapp.model.rest.IbilbideaRest;

@Service
public interface IbilbideaDao extends CrudRepository<Ibilbidea,Long> {
	Iterable<Ibilbidea> findAll();
	
	@Query(value="select i.ibilbideaID,o.irteeraOrdua,o.helmugaOrdua, o.finkoa, "
			+ "(select count(lg.geltokiaID) as geldialdiak from linea_geltokiak as lg where lineaID=o.lineaID) as geldialdiak, "
			+ "(select g1.izena from (geltokia g1 join linea_geltokiak lg on g1.geltokiaID=lg.geltokiaID) "
			+ "                 where lg.posizioa=(select min(posizioa) from linea_geltokiak where lineaID=o.lineaID) "
			+ "                       and lineaID=o.lineaID) as irteera, "
			+ "(select g2.izena from (geltokia g2 join linea_geltokiak lg on g2.geltokiaID=lg.geltokiaID) "
			+ "                 where lg.posizioa=(select max(posizioa) from linea_geltokiak where lineaID=o.lineaID) "
			+ "                       and lineaID=o.lineaID) as helmuga "
			+ "from (ibilbidea as i join ordutegia as o on i.ordutegiaID= o.ordutegiaID)",nativeQuery=true)	
	Iterable<IbilbideaRest> getIbilbideak();

	@Query(value="select i.ibilbideaID,o.irteeraOrdua,o.helmugaOrdua, o.finkoa, "
			+ "(select count(lg.geltokiaID) as geldialdiak from linea_geltokiak as lg where lineaID=o.lineaID) as geldialdiak, "
			+ "(select g1.izena from (geltokia g1 join linea_geltokiak lg on g1.geltokiaID=lg.geltokiaID) "
			+ "                 where lg.posizioa=(select min(posizioa) from linea_geltokiak where lineaID=o.lineaID) "
			+ "                       and lineaID=o.lineaID) as irteera, "
			+ "(select g2.izena from (geltokia g2 join linea_geltokiak lg on g2.geltokiaID=lg.geltokiaID) "
			+ "                 where lg.posizioa=(select max(posizioa) from linea_geltokiak where lineaID=o.lineaID) "
			+ "                       and lineaID=o.lineaID) as helmuga "
			+ "from (ibilbidea as i join ordutegia as o on i.ordutegiaID= o.ordutegiaID) "
			+ "where ISNULL(o.data) OR o.data = (CURDATE() + ?1)",nativeQuery=true)	
	Iterable<IbilbideaRest> getIbilbideakEguna(int eguna);
	
	
	@Query(value="SELECT count(igb.ekintza) as bidaiariak, count(CASE WHEN o.finkoa THEN 1 END) as finkoak,count(CASE WHEN NOT o.finkoa THEN 1 END) as malguak, DATE(igb.noiz) as data "
			+ "from ((ibilbidea_geltokia_bidaiaria as igb "
			+ " join ibilbidea as i on i.ibilbideaID = igb.ibilbideaID) "
			+ " join ordutegia as o on i.ordutegiaID = o.ordutegiaID) "
			+ "group by DATE(igb.noiz)",nativeQuery=true)	
	List<ErabileraRest> getErabilera();
}
