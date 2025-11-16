import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONReader {
    private final Object json;
    private JSONReader(Object json) {
        this.json = json;
    }

    public static JSONReader fromFile(String filename) {
        try (var in = JSONReader.class.getResourceAsStream(filename)) {
            if (in == null) return null;
            String json = new String(in.readAllBytes());
            Object parsed = JSONParser.parse(json);
            return new JSONReader(parsed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(String keyPath) {
        String[] parts = keyPath.split("\\.");
        Object value = json;

        for (String part : parts) {
            if (value instanceof Map) {
                value = ((Map<?, ?>)value).get(part);
            } else if (value instanceof List && part.matches("\\d+")) {
                value = ((List<?>)value).get(Integer.parseInt(part));
            } else {
                return null;
            }
        }

        return value;
    }
}
