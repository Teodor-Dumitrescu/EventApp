package services;

import domain.Address;
import general.BasicServices;
import repository.AddressRepository;
import java.util.List;

/**
 * Is an intermediate class, between repository(database) and view(GUI).
 */
public class AddressService implements BasicServices<Address> {

    private static AddressService addressServiceInstance = null;
    private AddressRepository addressRepository;

    private AddressService() {
        addressRepository = AddressRepository.getAddressRepositoryInstance();
    }

    public static AddressService getAddressServiceInstance() {

        if(addressServiceInstance == null){
            addressServiceInstance = new AddressService();
        }

        return addressServiceInstance;
    }


    /**
     * Make a list and return all addresses from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Address> getAll() {
        return addressRepository.getData();
    }


    /**
     * Return one single address from database when the selector is primary key.
     *
     * @return address
     */
    @Override
    public Address get(int index) {
        return addressRepository.get(index);
    }


    /**
     * Return the index for last address inserted into database.
     *
     * @return List
     */
    public int getLastIndex() {
        return addressRepository.getLastIndex();
    }


    /**
     * Main function for inserting new addresses into database.
     *
     * @param address object which will be inserted into database
     */
    @Override
    public void add(Address address) {
        addressRepository.insert(address);
    }


    /**
     * Main function for updating existing address from database.
     *
     * @param address object which will be updated
     */
    @Override
    public void update(Address address) {
        addressRepository.update(address);
    }


    /**
     * Main function for deleting existing address from database.
     *
     * @param address object which will be deleted from database
     */
    @Override
    public void remove(Address address) {
        addressRepository.delete(address);
    }


    /**
     * Debugging purpose.
     */
    @Override
    public void show() {
        List<Address> addressList = addressRepository.getData();

            for(Address address: addressList) {
                System.out.println(address.toString());
            }
    }

}
