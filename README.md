# What is MileIT?
MileIT (Mile IT) is a standard J2EE web application (as a backend) running on standard J2EE application server using MariaDB as a database to manage you family's, your company's car milage data. User interface is built on Bootstrap. MileIT can be accessed using a regular, modern web browser and if you have a need to automate some of the tasks, REST API is what you are looking for.

Mile IT can support a multi-user / multi-car purpose where every user has its own car set, own refuel locations as well as having a separate account to track important events for a car.

MileIT is designed to be free forever, so if you have an applicable software environment to host, you are good to go.

# Screenshots
- [Login screen](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00001.png)
- [Home page](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00002.png)
- [Cars list](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00003.png)
- [Edit car](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00004.png)
- [Refuels list](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00005.png)
- [Locations list](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00006.png)
- [Edit location](https://github.com/burgatshow/mileit/blob/master/MileIT/MileIt_Screenshot_00007.png)

# Current features
MileIT currently has the following features:

- It can work with multiple accounts
- It can work with multiple cars / account
- It can manage and use multiple different refuelling stations
- It can manage and use multiple different payment methods
- It can be customized for language and currency (but it does not do the math for you between currencies)
- It can display some nice graphs about your statistics
- It can manage your cars' important data and it can manage your various types of maintenance events

# Future features
- A fully functional REST API for better integration (I'll provide Apple Shortcuts integration later)
- E-mail / Pushover / Pushbullet integration for maintenances / reminders
- More languages (I hope for some community help)
- Better mobile web appearance

# What needs to be get done to use
To get start with MileIT, you will need the following software components:

- An [OpenLiberty](https://openliberty.io) J2EE application server
- A [MariaDB](https://mariadb.com) open source RDBMS software
- A computer / NAS to run the components listed above
- [My installation guide](https://github.com/burgatshow/mileit/wiki/Complete-installation-guide-on-Windows-using-Liberty). :)

Stay tuned, more will come.
