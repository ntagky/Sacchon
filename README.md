# Sacchon 👩‍⚕️

### Diabetes Management Web Application 🩺

Sacchon is a medical application designed to help patients manage their insulin levels by tracking their medical data and receiving advice from licensed doctors. The application consists of a user interface for patients and doctors to submit and view medical data, as well as an administration console with user management, monitoring, and reporting capabilities.
    
##  Installation ⚙️

Back-end was developed using [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) while front-end requires to have [Node.js](https://nodejs.org/en/download/) and the [Angular CLI](https://angular.io/cli) installed. The database used need the [MSSQL](https://www.microsoft.com/en-us/sql-server/sql-server-downloads) server to run. 

### Steps 🛠️
* **Database** Follow the instructions found [here](https://github.com/codehub-learn/MS-SQL-Server-)
* **Back-end** Use a compatible IDE (e.g. Intellij) and run [back-end/Sacchon](https://github.com/ntagky/Sacchon/tree/main/back-end/Sacchon)
* **Front-end** Use [front-end/Sacchon-app](https://github.com/ntagky/Sacchon/tree/main/front-end/Sacchon-app) & see the Patient Interface executing the following commands on your terminal:
```bash
npm install

ng serve -o
``` 

## Technologies 🚀

* Java & Spring Boot ☕
* Microsoft SQL 📊
* Swagger 📖
* Figma 🎨
* Angular 🅰️
* Trello & Git 📑
* Python 🐍

## Database 📊

We used a database consisting of two schemas, *develop* and *production* in order to achieve a parallel capability between testing and live production. 

![image](https://drive.google.com/uc?export=view&id=1Jj4wz4KlL8p0kRNkdlWDg6F3UDbvQ5XP)

The instances used and their relations are shown in the figure below. 

![image](https://drive.google.com/uc?export=view&id=1iIChoIak3ldB4uql6km3FNiNZftXq5ni)

## Extra Features 🌟

#### Automated Test Data 🤖
Creates dummy object depicted the real world. Being used in the development process, up to 1250 users can be created with limitless data based on the specific feature developers want to test on each time. Glucose levels are generated with the aid of [Standard Deviation](https://en.wikipedia.org/wiki/Standard_deviation) aiming to have data as real as possible. Everything is created using a random function which can be set to a constant seed so that it can generate same random numbers on multiple executions for a better debugging process. 
#### Login Functionality 🔑
Implement the basic, and essential elements of login and password functionality. Users can register, login and sign out supporting a more scalable project. Concerning about the sensitive information, users below 18 years of age can not create an account. Accounts with the same email are not allowed.
#### AI Diabetes Prediction 🤓
An [MLP network](https://en.wikipedia.org/wiki/Multilayer_perceptron) was trained to enable interested parties that might be too afraid to take the first step to go to the doctor, to calculate whether they might have diabetes or are in a risk group. Data used for training were originally from the US National Institute of Diabetes and Digestive and Kidney Diseases. The model predicts with more tha 95% accuracy on test data.
#### Print Consultation 🖨️
Give the opportunity to print the active consultation aiming to be used from people with sight deficiency or even older people who want their prescriptions printed on paper. 
#### Contact with Doctors 📞
Doctors' details are stored on each consultation ensuring that patients can contact with their doctors even if theirs doctors leave our systems.
#### Email Notifications ✉️
Emails are send automatically using the [SMT Protocol](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol) when users receive a new or an updated consultation. Also, tasks such as birthday or inactivity checks are being run on a daily basis taking advantage of some of our system's threads to scan the database.

### Application Screenshots 📸
Curious about our UI? We got you!
Check [here](https://github.com/ntagky/Sacchon/tree/main/front-end/README.md) some of our highlights. 

# Citations 🔗 

<pre>
@software{Sacchon,
  title={A Diabetes Management Web Application},
  author={Giannokosta, Georgia and Tzoulias, Christos and Ntagkonikos, Alexandros},
  month={2},
  year={2023}
}
</pre>
