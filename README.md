# PARCIAL DEL CICLO 2 DE METODOS Y ESTRUCTURA DE DATOS
Examen parcial.

# **EXPLICACION DEL PROYECTO**
:sparkles: Es una aplicacion de registro de materias que se cargan por medio de los archivos CSV:

 1. alumnos.csv
 2. materias.csv
 
 Sin antes haber cargado estos 2 ultimos el sistema no funcionara
.
la responsable de esto es la funciona loadCSV
![enter image description here](https://i.imgur.com/6yo7xde.png)
# **EXPLICACION FUNCIONAMIENTO**

la funcion de guardar la toda la informacion
![enter image description here](https://i.imgur.com/AFFhD21.png)![enter image description here](https://i.imgur.com/z4xG32C.png)

consta de 2 clases:

1-) Alumnos:

![enter image description here](https://i.imgur.com/CJYX2w5.png)
2-) materiasInscribir:

![enter image description here](https://i.imgur.com/gHzU5EF.png)

ahora bien la interfaz luce de la siguiente forma:

![enter image description here](https://i.imgur.com/hYYvfTH.png)

como antes dice tiene que cargar los 2 archivos CSV para poder continuar que se encuentran en la ruta

**/src/CSV/Alumnos.csv**

![enter image description here](https://i.imgur.com/KMuYr3k.png)
Una vez cargados en el combobox deberian de aparecer los nombres de los alumnos que se encuentran el CSV

![enter image description here](https://i.imgur.com/VaTQWNO.png)

Una vez adentro debera de tomar las notas de la primera tabla para que se agreguen en la segunda tabla que son las materias que se han escrito

![enter image description here](https://i.imgur.com/wbph3XD.png)

para poder exportar solo debe de presionar el boton de exportar

![enter image description here](https://i.imgur.com/cvuqKPf.png)
ahi apareceran todas las formas para poder exportar la información

**EXCEL**

![enter image description here](https://i.imgur.com/HsqEOsy.png)
**HTML**

![enter image description here](https://i.imgur.com/nnVPQbf.png)

# **PLAN DE CONTINGENCIA**

Si no puede abrir el proyecto, puede abrir el jar generado

en la ruta de:
**out/artifacts/Parcial_ciclo02/Parcial_ciclo02.jar**

![enter image description here](https://i.imgur.com/0XsRHYM.png)
ahora con una consola ejecutar el comando:

    java -jar Parcial_ciclo02.jar
![enter image description here](https://i.imgur.com/6zqj59p.png)
y debera de mostrar la app

![enter image description here](https://i.imgur.com/VaTQWNO.png)
# **JDK**
java JDK 8

# **IDE**
IntelliJ ultimate 2019.3

# **librerias**

-Jembox : https://www.gemboxsoftware.com/spreadsheet-java/free-version

-animateFx: https://github.com/Typhon0/AnimateFX


