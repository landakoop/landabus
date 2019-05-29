package landakoop.landabus.mainapp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import landakoop.landabus.mainapp.model.Ibilbidea;

public class IbilbideaSimpleSerializer extends JsonSerializer<Ibilbidea> {

	@Override
	public void serialize(Ibilbidea ibilbidea, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {
		jsonGenerator.writeNumber(ibilbidea.getId());
	}

}
