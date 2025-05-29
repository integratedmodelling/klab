package org.integratedmodelling.klab.hub.users;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static boolean isValidJson(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void processSecrets(JsonNode node) throws IOException, InterruptedException {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();

                if (key.toLowerCase().contains("secret")) {
                    //callApi(key, value);
                }

                processSecrets(value);
            }
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                processSecrets(item);
            }
        }
    }
}
