package by.epam.bookshop.util;

import by.epam.bookshop.exceptions.AddressException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddressObject {
    private static String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";
    Map <String, String> addressToGeo = new HashMap<>();
    private String key = "AIzaSyCwIrGRwhU9pynlNeOMDXPqQYZmwroni4Q";
    private String address;
    private String formattedAddress;
    private double longitude;
    private double latitude;
    private boolean status;
    private String errorMessage;

    public AddressObject(String address) throws AddressException {
        processAddress(address);
    }

    public void processAddress(String address) throws AddressException {
        final JSONObject response;
        addressToGeo.put("address",address);
        addressToGeo.put("key", key);
        try {
            response = JsonReader.read(getURL(baseURL, addressToGeo));
        } catch (IOException e) {
            throw new AddressException(e);
        }
        String status = response.getString("status");
        if (status.equals("OVER_QUERY_LIMIT")) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new AddressException(e);
            }
        }
        if (!status.equals("OK")) {
            this.status = false;
            errorMessage = status;
            throw new AddressException(status);
        }
        this.status = true;
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        longitude = location.getDouble("lng");// долгота
        latitude = location.getDouble("lat");// широта
        location = response.getJSONArray("results").getJSONObject(0);
        formattedAddress = location.getString("formatted_address");
    }

    private static String getURL(String baseURL, final Map<String, String> params) {
        StringBuilder builder = new StringBuilder(baseURL);
        builder.append("?");
        for (String key :params.keySet()) {
            builder.append(key).append("=").append(params.get(key).replace(" ", "%20")).append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String toString() {
        return getFormattedAddress();
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
