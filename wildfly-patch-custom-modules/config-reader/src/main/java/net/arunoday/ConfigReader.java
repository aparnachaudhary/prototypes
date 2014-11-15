package net.arunoday;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class ConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);

    private static final String PROPERTIES_FILE = "arunoday.properties";
    private static Pattern floatPattern = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
    private static Pattern intPattern = Pattern.compile("^[0-9]+$");

    @GET
    @Path("/config")
    @Produces({ "application/json" })
    public JsonObject readConfiguration() {
        return convertToJson(getProperties());
    }

    /**
     * Converts the properties object into a JSON string.
     * 
     * @param properties
     *            The properties object to convert.
     * @return A JSON object
     */
    private JsonObject convertToJson(Properties properties) {
        JsonObjectBuilder json = Json.createObjectBuilder();
        for (Object key : properties.keySet()) {
            String value = properties.getProperty(key.toString());
            if (value.equals("true") || value.equals("false")) {
                json.add(key.toString(), Boolean.parseBoolean(value));
            } else if (intPattern.matcher(value).matches()) {
                json.add(key.toString(), Integer.parseInt(value));
            } else if (floatPattern.matcher(value).matches()) {
                json.add(key.toString(), Float.parseFloat(value));
            } else {
                json.add(key.toString(), value);
            }
        }

        return json.build();
    }

    private Properties getProperties() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (Exception e) {

            }
        }
        return properties;
    }

}
