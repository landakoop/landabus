package landakoop.landabus.mainapp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import landakoop.landabus.mainapp.model.Erabiltzailea;

public class ErabiltzaileaSimpleSerializer extends JsonSerializer<Erabiltzailea> {

	@Override
	public void serialize(Erabiltzailea erabiltzailea, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {
		jsonGenerator.writeNumber(erabiltzailea.getId());
	}

}
