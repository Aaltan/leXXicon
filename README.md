# leXXicon - Words Puzzle Game per Android

Questo progetto è la mia personale versione di 'Scarabeo' per i dispositivi Android.  
Il gioco è stato sviluppato utilizzando uno dei primi porting di 'Processing' per Android (https://processing.org).  
A causa della mia cronica mancanza di tempo, è ancora largamente incompleta... comunque la modalità 'Infinite' è giocabile.  
Attualmente gli unici dizionari inclusi sono l'Italiano ed una 'bozza' di Bergamasco, è comunque semplicissimo aggiungere/modificare i dizionari esistenti, basta aggiungerli nella directory **assets** ed indicarli nel metodo **loadDictionary** della classe **CrossVariables**:  

&nbsp;&nbsp;`public static void loadDictionary(Context aCtx, int aDict) {`  
&nbsp;&nbsp;&nbsp;&nbsp;`USER_COUNTRY = aCtx.getResources().getConfiguration().locale.getDisplayCountry();`  
&nbsp;&nbsp;&nbsp;&nbsp;`String dictFile = "";`  
&nbsp;&nbsp;&nbsp;&nbsp;`switch (aDict) {`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`case CrossVariables.DICT_BERGAMO:`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`dictFile = "Bergamasco.txt";`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`break;`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`case CrossVariables.DICT_ITALIAN:`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`dictFile = "Italiano.txt";`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`break;`  
&nbsp;&nbsp;&nbsp;&nbsp;`}`  
&nbsp;&nbsp;&nbsp;&nbsp;`[...]`  
&nbsp;&nbsp;`}` 

Ogni aiuto/idea per completare il gioco è benvenuto/a.
Buon Divertimento.

_**Aaltan -AKA- Marco Cognolato**_


# leXXicon - Words Puzzle Game for Android devices

This project is my personal version of 'Scrabble' for Android devices.  
The game was developed using one of the first portings of 'Processing' for Android (https://processing.org).  
Cause my chronic lack of time, it is still largely incomplete... anyway 'Infinite Mode' is playable.  
Actually only Italian and Bergamasco dictionary are included, anyway it's simple to add/modify them, just put the dictionary you want to use in directory **assets**, then modify method **loadDictionary** of **CrossVariables**: 


&nbsp;&nbsp;`public static void loadDictionary(Context aCtx, int aDict) {`  
&nbsp;&nbsp;&nbsp;&nbsp;`USER_COUNTRY = aCtx.getResources().getConfiguration().locale.getDisplayCountry();`  
&nbsp;&nbsp;&nbsp;&nbsp;`String dictFile = "";`  
&nbsp;&nbsp;&nbsp;&nbsp;`switch (aDict) {`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`case CrossVariables.DICT_BERGAMO:`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`dictFile = "Bergamasco.txt";`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`break;`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`case CrossVariables.DICT_ITALIAN:`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`dictFile = "Italiano.txt";`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`break;`  
&nbsp;&nbsp;&nbsp;&nbsp;`}`  
&nbsp;&nbsp;&nbsp;&nbsp;`[...]`  
&nbsp;&nbsp;`}` 

Any help/ideas to complete the game are welcome.
Enjoy.

_**Aaltan -AKA- Marco Cognolato**_

[[https://github.com/Aaltan/leXXicon/blob/master/wikistuff/leXXicon_Menu.png|alt=Menu]]
