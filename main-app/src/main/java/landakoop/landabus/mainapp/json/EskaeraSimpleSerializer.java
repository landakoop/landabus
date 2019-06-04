package landakoop.landabus.mainapp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import landakoop.landabus.mainapp.model.Eskaera;

public class EskaeraSimpleSerializer extends JsonSerializer<Eskaera>{

	@Override
	public void serialize(Eskaera eskaera, JsonGenerator generator, SerializerProvider serializers) throws IOException {
		generator.writeNumber(eskaera.getId());
	}

}
