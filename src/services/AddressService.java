package services;

import domain.Address;
import general.BasicServices;
import repository.AddressRepository;
import java.util.List;

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

    @Override
    public List<Address> getAll() {
        return addressRepository.getData();
    }

    @Override
    public Address get(int index) {
        return addressRepository.get(index);
    }

    public int getLastIndex() {
        return addressRepository.getLastIndex();
    }

    @Override
    public void add(Address address) {
        addressRepository.insert(address);
    }

    @Override
    public void update(Address address) {
        addressRepository.update(address);
    }

    @Override
    public void remove(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public void show() {
        List<Address> addressList = addressRepository.getData();

            for(Address address: addressList) {
                System.out.println(address.toString());
            }
    }

}
