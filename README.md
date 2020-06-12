# EventApp
Project done by me and my classmate Marius Dariescu for our Software Development Methods course. (In progress...)

## Extracted from section 7 in Tehnical Description - Wiki

### **7. Launch the application**

In the _helpers_ folder, there are two _jar_ elements and two _SQL_ files. The application can be launched in two ways, local and remote.

#### **7.1. Local running**

By creating a local database and running _mds_local.jar_.

##### **7.1.1. Creating a local database**

Because the application uses a _MySQL_ database, the following steps are required:
- A MySQL server must be installed on the computer that will run the application
- Create a database named _database_mds_
- Create a _root_ user without a password who can access the newly created database.
- Run _create_tables.sql_ in SQL client which will create the initial tables.
- Run _initial_data.sql_ in SQL client which loads some test data with which the application can be tested.

#### **7.1.2. Running the application locally**

If steps **7.1.1** have been completed you can run _mds_local.jar_. In order to do this, it is necessary to have _java_ installed on your personal computer (including _Java SDK_).

### **7.2. Run remote**

The only difference from the local run is that the remote run is linked to an existing database, which means that you can download and run _mds_remote.jar_, without doing **7.1.1**. It is also necessary to install java as in 
**7.1.2.**

### **7.3. Changing credentials**

If for some reason there were errors in creating the database with the requested name or the creation of the requested user, the source code can be downloaded and the credentials in the _DatabaseConnection_ can be modified in order to be able to connect locally. It is also necessary to use a build tool to be able to compile the source code and run the application.

### **7.4. Differences between local vs remote**

The remote run variant contains a lag of up to 4 - 5 seconds, which makes testing the application unsuitable because the database is stored on an external server, which makes it difficult to retrieve data. Locally the execution is executed instantly but a previous configuration of the database is necessary in order to be able to execute the application. There are no other differences between these two types.
