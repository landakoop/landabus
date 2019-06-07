package landakoop.landabus.mainapp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import landakoop.landabus.mainapp.model.Ordutegia;

public class OrdutegiaSimpleSerializer extends JsonSerializer<Ordutegia> {

	@Override
	public void serialize(Ordutegia ordutegia, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {
		jsonGenerator.writeNumber(ordutegia.getId());
	}

}
