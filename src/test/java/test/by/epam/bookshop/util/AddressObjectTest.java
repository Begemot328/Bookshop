package test.by.epam.bookshop.util;


import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.util.AddressObject;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

public class AddressObjectTest extends Assert {
    private static final String addressRU = "Грушевская, 73, Минск";
    private static final String address = "Минск, газеты Правда , 26";
    private static final String addressEN = "Minsk, vulica Hrušaŭskaja 73";
    private static final String addressWrong = "Micvxlnsk, Grussfdlcmhevskaya, 73";
    private static final String addressCorrect =
            "vulica Hrušaŭskaja 73, Minsk 220089, Belarus";
    private static final double longitude = 27.5201565;
    private static final double latitude = 53.8836485;

    @Test
    public void testGetCoordsRU() throws AddressException {
        AddressObject object = new AddressObject(addressRU);
        assertTrue(object.isStatus() && object.getLatitude() == latitude && object.getLongitude() == longitude);
    }

    @Test
    public void testGetCoordsEN() throws AddressException {
        AddressObject object = new AddressObject(addressEN);
        assertTrue(object.isStatus() && object.getLatitude() == latitude && object.getLongitude() == longitude);
    }

    @Test
    public void testGetAddressEN() throws AddressException {
        AddressObject object = new AddressObject(addressEN);
        assertTrue(object.isStatus() && object.getFormattedAddress().equals(addressCorrect));
    }

    @Test
    public void testGetAddressRU() throws AddressException {
        AddressObject object = new AddressObject(addressRU);
        assertTrue(object.isStatus() && object.getFormattedAddress().equals(addressCorrect));
    }

    @Test (expected = AddressException.class)
    public void testIsFalse() throws AddressException {
        AddressObject object = new AddressObject(addressWrong);
    }

    @Test
    public void testIsTrue() throws AddressException {
        AddressObject object = new AddressObject(address);
        assertTrue(object.isStatus());
    }

}
