# Quizzz!

This README will need to contain a description of your project, how to run it, how to set up the development environment, and who worked on it.
This information can be added throughout the course, except for the names of the group members.
Add your own name (do not add the names for others!) to the section below.

## Description of project
Quizzz is a intense, high-paced, quiz-esque game aimed to educate people about energy usages of different activities. 
It supports singleplayer and multiplayer.

## Group members

| Profile Picture                                                                                         | Name                  | Email                             |
|---------------------------------------------------------------------------------------------------------|-----------------------|-----------------------------------|
| ![](https://secure.gravatar.com/avatar/9568e7770ae1e2274f2f07854c8c16c3?s=50&d=identicon)               | Henry Page            | H.Page@student.tudelft.nl         |
| ![](https://eu.ui-avatars.com/api/?name=OOPP&length=4&size=50&color=DDD&background=777&font-size=0.325) | Vlad Tudor Stefanescu | V.T.Stefanescu@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/d7a06f5c69ccf4f9f1f782f91c982cc6?s=50&d=identicon)               | Matyas Kollert        | M.Kollert@student.tudelft.nl      |
| ![](https://eu.ui-avatars.com/api/?name=OOPP&length=4&size=50&color=DDD&background=777&font-size=0.325) | Andrei Dascalu        | A.Dascalu@student.tudelft.nl      |
| ![](https://eu.ui-avatars.com/api/?name=OOPP&length=4&size=50&color=DDD&background=777&font-size=0.325) | Tom Huisman           | t.m.huisman@student.tudelft.nl    |

## Installation & Launch
The following are installation & launch instructions for Quizzz!. Pre-requisite knowledge about the utilisation
of git and bash is assumed. 
- All referenced commands are for a windows based environment. Append `./` to the start of every gradle command if you are using a linux distribution.

### Clone this Repository
To run or contribute to this project, you must first clone this repository
#### SSH
```
git clone git@gitlab.ewi.tudelft.nl:cse1105/2021-2022/team-repositories/oopp-group-63/repository-template.git
```
- The most common errors that occur here are related to authentication errors. Fixes for them can be found [here](https://docs.gitlab.com/ee/user/ssh.html)

#### HTTP (Not Recommended)
```
git clone https://gitlab.ewi.tudelft.nl/cse1105/2021-2022/team-repositories/oopp-group-63/repository-template.git
```

 
### Verifying the Build Integrity
Before proceeding to the next step, ensure that you have a stable build of the product, 
by typing the folllowing into your console (cmd/bash):
```
gradlew build
```
If the build is stable, `BUILD SUCCESSFUL` should be seen on the console, if this is not the case,
wait for a stable release, or go back to a previous stable release.
### Loading the Activities in from the Main Activity Repository

This is a very crucial step, as the game will not serve it's function
properly without any activities.

#### Downloading the Activities
The compressed activity archive can be downloaded from  [here](https://gitlab.ewi.tudelft.nl/cse1105/2021-2022/activity-bank/-/jobs/2444739/artifacts/raw/20220311-oopp-activity-bank.zip)

Unzip the archive, rename the directory as "activitybank" and put it in the root directory of the project.

#### Loading the Activities from the JSON file
Within the `ActivityRepositoryLoader` class, (which can be found at `server/src/main/java/server/database/ActivityRepositoryLoader.java`)
- Uncomment the `@Configuration` annotation 
- Uncomment `import org.springframework.context.annotation.Configuration;`

After starting the server for the first time (**_[explained in next section](#starting-the-application)_**), the activities should be loaded in. 
Comment it right afterwards to ensure that activities don't get reloaded/overwritten.

### Starting the Application
#### Starting the Server

To start the server type the following into your console:
```
gradlew bootRun
```
#### Starting the Client
To start the client, type the following into your console:
```
gradlew run
```


You should be all set to play the game! Have Fun!



## How to contribute to it

## Copyright / License (opt.)
