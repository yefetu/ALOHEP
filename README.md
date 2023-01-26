# Welcome to the ALOHEP Readme!!

## Alohep installation for Windows

Requirement: JRE(Java Runtime Enviroment) 7 or higher  
First you will go to releases 
![image](https://user-images.githubusercontent.com/102833131/213984651-2e7cebfe-6096-4ab6-8279-ef8a54ebb867.png)

Secondly you will download AloHEP.zip
![image](https://user-images.githubusercontent.com/102833131/213984943-bd7073f3-b6ee-4447-8359-cc647c620bc0.png)

Thirdly you will extract zip 

Finally you will select Alohep.jar
![image](https://user-images.githubusercontent.com/102833131/213985244-7959dedf-10cb-4f92-9b35-7ac6f5c3d00d.png)


## Alohep userguide 
The AloHEP software, developed using the Java programming language, is composed of the "save" folder where accelerator-related save files are stored and the executable "AloHEP.jar" file that contains the software codes. In the save file, accelerators are classified and distributed into subfolders according to the particle type used. If a subfolder for the same particle-containing accelerators is desired, a "-" can be added after the particle name and desired naming can be done. JSON files belonging to each accelerator are located in the particle subfolders. These JSON files contain accelerator-related parameters and can be edited using text editors. The record file also includes the "settings.JSON" file containing the simulation parameters. These simulation parameters can be edited both in this file and through the AloHEP interface. The AloHEP.jar file must be located in the same folder as the save folder to be able to run. When the "AloHEP.jar" is run, the main panel of the AloHEP software opens (Figure 1).

![image](https://user-images.githubusercontent.com/102833131/213978632-c10b790e-43cf-41b6-baa7-f3cacdaaa679.png)

Figure 1 AloHEP Userinterface

The main panel consists of three sub-panels. The top two panels are accelerator panels where information about the accelerators in which particle collisions are to be performed in the simulation is entered, and the bottom panel is the settings panel where the parameters of the simulation are entered. When selecting the accelerator to be used in the collision simulation, the particle type used is selected first. After the particle type is selected, the accelerators in the bottom of the panel are automatically updated accordingly, so the accelerator can be selected from the ones listed in the bottom of the panel. If accelerator parameters not found in the AloHEP software's save file are desired to be used, the accelerator type "Custom" is selected. After this selection, modifications can be made based on the last selected accelerator parameters, and the edited parameters can be added to the AloHEP software's save file with the desired name using the "save" button at the top of the panel. After selecting the accelerators in the AloHEP software, the number of macro-particles in the simulation, the simulation resolution, and other simulation parameters can be adjusted from the settings panel at the bottom of the interface, and after selecting the desired effects in the simulation, the simulation can be started by pressing the "calculate" button. The AloHEP software has a graphic panel that shows the progress of macro-particles step by step and appears when the simulation starts. The changes in the shape of the packets can be tracked through this interface.

![image](https://user-images.githubusercontent.com/102833131/213979261-b313f598-8ed5-42b0-a934-eb36ee889cd5.png)

Figure 2. Alohep Graphic Interface

While the graphic panel is running, the axis on which the collision is being observed can be changed by pressing the x, y and z keys on the keyboard. The selected axis can be zoomed in  using the mouse wheel. This way, the change in shape of the bunch can be easily tracked during the simulation. Additionally, by pressing the r key on the keyboard, the boundaries of the collision region, slices and cells can be seen on the graphic panel during the simulation. At the end of the simulation process, the graphic panel closes and the result panel opens. Here, the results obtained for the center of mass energy and luminosity of the collision; the cross-sections of the bunches, the beam-beam tuneshift and the disruption parameters are displayed.

![image](https://user-images.githubusercontent.com/102833131/213979993-2e0b15a5-b228-4281-aa76-072ccd0ee5c1.png)

Figure 3. Alohep Result Panel

 The luminosity value is calculated in two different ways, nominal and effective. The nominal luminosity value is obtained analytically using Equation 1, while the effective luminosity value is obtained from the results of the simulation. Additionally, by comparing the two luminosity values, factors that increase/decrease the luminosity due to the effects added in the simulation are obtained.

![image](https://user-images.githubusercontent.com/102833131/213977517-c75a03a4-9241-45ef-aecf-7b7e2c56745a.png)                         (Equation 1)
