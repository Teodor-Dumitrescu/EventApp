package domain;

public class Client {

    private int clientId; //primary key from database
    private String username;
    private String password;
    private String name;
    private int age;
    private String gender;
    private int addressId; //foreign key

    public Client(int clientId, String username, String password, String name, int age, String gender, int addressId) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.addressId = addressId;
    }

    public Client(String username, String password, String name, int age, String gender, int addressId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.addressId = addressId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id[" + clientId + "]:" + " " + username + "," + password + "," + name + "," + age + "," + gender + "," + addressId;
    }

    public void print(){
        System.out.print(clientId + " " + username + "," + name + "," + addressId);
    }
}
