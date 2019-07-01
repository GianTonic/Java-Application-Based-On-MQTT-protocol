# Java-Application-Based-On-MQTT-protocol

ARCHITETTURA MQTT:
L’architettura MQTT è progettata per supportare migliaia di client, collegati in locale e/o in remoto ad uno o più Server comunicanti. Quest‘ ultimi si occuperanno del processamento e dell’ instradamento dei messaggi in arrivo dai Client,  semplificando la comunicazione one-to-many (uno-a- molti). 
MQTT garantisce affidabilità e consegna dei messaggi affidandosi ai sottostanti protocolli TCP (Transfer Control Protocol) e IP (Internet Protocol). TCP fornisce un servizio affidabile, implementato tramite notifiche di avvenuta ricezione del pacchetto (ACK) da parte del destinatario. Il protocollo IP invece, tramite il servizio best-effort (massimo sforzo) offre un certo livello di garanzia nella consegna dei messaggi .
Tali caratteristiche si rilevano ideali per sfruttare al meglio la comunicazione tra i tanti dispositivi telemetrici, mobili, e sistemi operativi protagonisti della comunicazione  IoT. 
Un semplice scenario MQTT consiste in un insieme di processi client, conosciuti anche come Publisher e Subscriber, che comunicano con un processo server locale (o remoto), chiamato Broker. Quest’ultimo rimane ciclicamente in attesa di connessioni, ponendosi in ascolto su una specifica porta. Essa è determinata dal tipo di protocollo che si sta utilizzando a livello applicazione: il protocollo MQTT utilizza la porta 1883 per una connessione TCP/IP standard e la porta 8883 per sfruttare il protocollo in modo sicuro tramite SSL (Secure Sockets Layer). 

--------------------------------------------------------
DESCRIZIONE IMPLEMENTAZIONE:
Per l’implementazione del protocollo MQTT si è utilizzata la libreria Java di “Eclipse Paho”. 
Per quanto riguarda l’implementazione delle due applicazioni che simulano il comportamento di Publisher e Subscriber si è utilizzato il linguaggio Java. 
Tali applicazioni comunicano tramite il protocollo MQTT con due server virtualmente connessi tramite bridge (ponte). Un server è stato installato in locale, mentre l’altro è connesso in remoto.
Il bridge è stato settato sul broker locale per condividere i dati con l’altro server in entrambe le direzioni della comunicazione. Quest’ultimo è connesso in remoto e dispone di un indirizzo IP fisso per essere raggiungibile da qualsiasi posizione. In questo modo è stata simulata un’architettura capace  di condividere su internet dati generati localmente, e di ricevere in locale (per esempio in sede aziendale) messaggi generati su internet da dispositivi mobili.
I dati raccolti dal Subscriber verranno raccolti in file in formato .csv in modo da poter essere elaborati da fogli di calcolo.

------------------------------------------------------
BROKER LOCALE UTILIZZATO:
-MOSQUITTO
Per l'installazione in macchina Linux, distribuzione Ubuntu:

sudo apt-add-repository ppa:mosquitto-dev/mosquitto-ppa
sudo apt-get update
sudo apt-get install mosquitto
sudo apt-get install mosquitto-clients

Per terminare mosquitto nel caso caso in cui è gia in run:
sudo service mosquitto stop

------------------------------------------------------
BROKER REMOTO UTILIZZATO:
-test.mosquitto.org
------------------------------------------------------

SETTAGGIO BRIDGE TRA BROKER LOCALE/REMOTO:
Nella directory d' installazione,sul file mosquitto.conf:

connection test
address test.mosquitto.org:1883
topic root/temperature in 0 
topic root/temperatureout out 0 
try_private false
bridge_attempt_unsubscribe true

-----------------------------------------------------

-CLIENT PUBLISHER

Il client Publisher implementa due thread (definiti come attività di un processo) che accedono ad una risorsa condivisa di tipo coda individuata dalla classe “Container”. Quest’ ultima implementa i metodi per aggiungere e prelevare elementi dalla struttura. Un thread è definito nella classe “Parameters”  ed ha la funzione di generare ciclicamente valori random compresi tra 0 e 45 per simulare grandezze di temperatura. Subito dopo che il valore è stato generato viene caricato in coda.  Alla fine di questa operazione il thread corrente rilascia la risorsa condivisa, notificando l’evento all’ altro thread. 
L’altro thread è definito nella classe Publisher ed ha la funzione di  inviare ciclicamente i messaggi. In particolare preleva il primo elemento della coda, lo rende disponibile per la trasmissione, e lo rimuove. In questo modo si ottiene una capienza potenzialmente infinita della coda.
L’utilizzo dei due thread con le modalità descritte, permette di sincronizzare la generazione dei dati con l’invio di essi stessi.
Nella classe Publisher sono presenti le API (“ Application Programming Interface ”) messe a disposizione dalla libreria Paho. Nello specifico sono stati utilizzati  i metodi per:
- creare l’istanza del client
- settare la versione del protocollo MQTT 
- generare topic
- generare payload in base al dato prelevato
- pubblicare messaggio
- associare l’interfaccia “MqttCallback ” all’istanza del client.
Quest’ ultima ha una struttura simile a una classe, ma contiene metodi senza corpo, definibili dalla classe che implementa l’interfaccia.
A tale scopo è stata utilizzata la classe “Callback“ per implementare l’ interfaccia “MqttCallback”. Essa abilita l’applicazione ad essere notificata quando si verificano eventi asincroni come ad esempio un improvvisa disconnessione. Nel caso del client Publisher, quando si tenta di inviare un messaggio verrà restituito un “token” per tenere traccia della consegna del messaggio. Appena il messaggio arriva a destinazione verrà chiamato il metodo per la gestione del token appartenente all ’interfaccia Callback.



--------------------------------------------

- CLIENT SUBSCRIBER

Il client Subscriber implementa i metodi necessari per la connessione al broker remoto, settare i flag della connessione, e abbonare l’istanza del client al topic concordato in precedenza. Tali metodi risiedono nella classe “Subscriber”.
Anche in questo caso si è fatto l’uso della classe “Callback”  per implementare l’interfaccia “Mqttcallback”. In particolare  quando arrivano i dati dal server remoto, l’ MQTT client invoca il metodo per la gestione dei messaggi definito nella classe “Callback”. I parametri passati durante la chiamata sono il topic e il messaggio MQTT. 
Tale metodo è stato definito/implementato per salvare i dati in arrivo su un file con estensione “csv”. In questo modo è possibile aprire il file come un foglio di calcolo e rappresentare i dati ricevuti tramite grafico.


