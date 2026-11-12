# JTrash - Gioco di Carte in Java
Implementazione desktop modulare del gioco di carte JTrash, sviluppata in Java con interfaccia grafica Java Swing e architettura MVC (Model-View-Controller).

## Panoramica del progetto
L'applicazione consente di sfidare da 1 a 3 avversari gestiti dall'intelligenza artificiale attraverso un'interfaccia grafica interattiva.
L'esperienza utente è organizzata in tre schermate principali - Menu, Profilo e Tavolo di Gioco - navigabili tramite pulsanti posti nella parte inferiore della finestra.

## Funzionalità Principali
● Gestione profilo e persistenza dati:
  all'avvio del gioco, l'utente inserisce il proprio nickname e viene mostrato il profilo utente, con possibilità di cambiare anche l'avatar. Viene anche creato un file locale che segna i punteggi delle varie partite.
● Partite contro i bot:
  è possibile di configurare il numero di partecipanti sfidando 1, 2 o 3 Bot. Mentre l'utente interagisce tramite GUI, i Bot eseguono autonomamente le proprie mosse pescando le carte seguendo le regole, fino allo scarto. 
● Regole:
  gestione di carte numeriche e carte speciali nell'interfaccia interattiva
● Feedback per l'utente:
  l'interfaccia grafica guidare l'utente con finestre di notifica e suoni 

## Architettura e Design Pattern
Ho deciso di usare l'MVC pattern
dove il Model contiene l'intera logica del programma, con le classi che gestiscono il giocatore e le carte
la view gestisce la GUI in base al numero di giocatori
e il Controller che orchestra la partita, verificando le carte rispettino le regole del gioco.
Viene usato anche il pattern Observer-Observable
