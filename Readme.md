# Tris-SM

**Autori:** Elisei Mailat & Timotei Sorea  
**Classe:** 5AI  

Tris-SM è un gioco del Tris (Tic-Tac-Toe) sviluppato in Java con architettura Client-Server.  
Il sistema permette di giocare:
- **PvP (Player vs Player):** due utenti connessi in rete si sfidano.
- **PvC (Player vs Computer):** il giocatore sfida l’intelligenza artificiale del server, basata sull’algoritmo Minimax.

---

## Requisiti

- Java JDK 11 o superiore
- Eclipse IDE (con supporto Maven)
- Sistema operativo con supporto per Java e rete (Windows, macOS, Linux)

---

## Istruzioni per avviare l’applicazione

1. **Importare i progetti in Eclipse**
   - Aprire Eclipse.
   - Andare su `File > Import... > Existing Maven Projects`.
   - Selezionare la cartella del progetto **Server** e cliccare su `Finish`.
   - Ripetere l’operazione per il progetto **Client**.

2. **Avviare il server**
   - Nel progetto **Server**, aprire la classe `ServerMain.java`.
   - Eseguire il `main()` della classe.
   - Il server si avvierà sulla porta **5500** e rimarrà in attesa di connessioni.

3. **Avviare il client**
   - Nel progetto **Client**, aprire la classe `ClientMain.java`.
   - Eseguire il `main()` della classe.
   - Il client si connetterà al server all’indirizzo `127.0.0.1:5500`.

4. **Giocare**
   - Seguire le istruzioni visualizzate nella console:
     - Scegliere la modalità di gioco: `0` per PvP, `1` per PvC.
     - Inserire le mosse nel formato `r c` (riga e colonna da 0 a 2).
     - Digitare `EXIT` per abbandonare la partita.

---

## Note aggiuntive

- Assicurarsi di avviare sempre prima il **Server** e poi il **Client**.
- È possibile avviare più client contemporaneamente per testare la modalità PvP.
- La modalità PvC utilizza un algoritmo Minimax per garantire mosse ottimali del computer.

---

