# scoreme-assessment


This repository contains my assessment  submitted to Scoreme for the role of Software Engineer (Java).

## Steps to run app locally

## Clone the repo
* Open your terminal.

* Change the current working directory to the location where you want to clone the repository.

* Run the following command to clone the repository:
```bash
git clone https://github.com/nmnarora600/scoreme-assessment.git
```


## Installing the Required Dependencies

After cloning the repo run run following commands to install required node modules.

* check in to scoreme-assessment
```bash
cd scoreme-assessment
```
* install node modules
```bash
mvn clean install
```



## Run in Development Mode

After following above steps just open the folder in cmd, powershell etc.
```bash
cd Path/to/the/repo/scoreme-assessment
```
* Run the spring-boot app to start api locally

* Open Postman and send a POST request to api to get results in Outputs folder

```bash
http://localhost:8080/segment
```

## Testing in Development Mode

To test the working of api enter this command in terminal
```bash
mvn test
```


##### Example Request body

### Posting Data through '/segment'
![Thumb](https://github.com/nmnarora600/scoreme-assessment/blob/main/image/git.png)

