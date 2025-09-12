package org.integratedmodelling.klab.test.cli;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.geotools.http.SimpleHttpClient;

// Cliente HTTP que siempre envía BasicAuth
class WFSWithPreemptiveAuth extends SimpleHttpClient {

    // Realiza GetCapabilities con BasicAuth preemptive
    private static InputStream getCapabilities(String wfsUrl, String user, String pass) throws Exception {
        URL url = new URL(wfsUrl + "?REQUEST=GetCapabilities&SERVICE=WFS");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String encoded = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + encoded);

        int code = conn.getResponseCode();
        if (code != 200) {
            throw new RuntimeException("Fallo GetCapabilities: HTTP " + code);
        }

        return conn.getInputStream(); // XML de capabilities
    }

    public static DataStore createPrivateWFSDataStore(String wfsUrl, String user, String pass) throws Exception {
        // 1. Hacer la primera request manual para verificar acceso
        try (InputStream capabilities = getCapabilities(wfsUrl, user, pass)) {
            System.out.println("GetCapabilities OK, workspace accesible");
            // Opcional: parsear el XML si quieres listar feature types
        }

        // 2. Crear DataStore con parámetros GeoTools
        Map<String, Object> params = new HashMap<>();
        params.put(WFSDataStoreFactory.URL.key, new URL(wfsUrl)); // solo la URL base
//        params.put(WFSDataStoreFactory.WFS_VERSION.key, "1.0.0");
        params.put(WFSDataStoreFactory.USERNAME.key, user);
        params.put(WFSDataStoreFactory.PASSWORD.key, pass);

        // Forzar BasicAuth preemptive en GeoTools
        System.setProperty("org.geotools.http.authenticatePreemptive", "true");

        WFSDataStoreFactory dsf = new WFSDataStoreFactory();
        return (WFSDataStore) dsf.createDataStore(params);
    }

    public static void main(String[] args) throws Exception {
        String wfsUrl = "https://integration.integratedmodelling.org/geoserver/alaska_ahtna/wfs";
        String user = "alaska_user";
        String pass = "xxXXXXx";

        DataStore store = createPrivateWFSDataStore(wfsUrl, user, pass);
        System.out.println("DataStore listo: " + store);
    }
}