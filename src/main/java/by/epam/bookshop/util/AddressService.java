package by.epam.bookshop.util;

public class AddressService {
    private String address;
    private String formattedAddress;
    private double longitude;
    private double latitude;
    private boolean status;
    private String errorMessage;

    public AddressService(String address) {
        this.address = address;
        processAddress(address);
    }

    private void processAddress(String address) {

    }


}
