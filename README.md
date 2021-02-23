# Übungsprojekt Space Station :rocket:

Dieses Repository enthält das Übungsprojekt für die Clean Code Kompakt Schulung.

## Story
Du bist gerade auf der Raumstation "CCK" eingetroffen.
Da die Besatzung der Raumstation immer wieder "seltsames Verhalten" der Bordsoftware feststellt, wurdest du als Software-Expert:in gerufen, um die Bordsoftware zu analysieren, sie in einen wartbaren Zustand zu versetzen und dabei ggf. Fehler sichtbar zu machen. 
Die Probleme haben in letzter Zeit stark zugenommen - es wird also höchste Zeit für deinen Einsatz!

Du beginnst damit dir einen Überblick zu verschaffen. Nachdem du dir einen Branch vom git master abgezweigt hast,
verschaffst du dir erst mal einen Überblick über die Funktionen der Raumstation, denn die Dokumentation schein auf kyrillisch verfasst zu sein und ursprünglich von einer anderen Station mit dem Namen "MIR" zu kommen.

## Setup
Um nicht gleich am Produktionscode zu schrauben, startest du dir eine lokale Version der Anwendung.
Die Anwendung ist ein Java Spring Backend. Zur Unterstützung bei der Entwicklung gibt es ein 
virtuelles Cockpit, das stets den aktuellen Zustand der Anwendung zeigt.

### Setup der Entwicklungsumgebung fürs Backend
1. Installiere Git
2. Installiere eine Entwicklungsumgebung wie IntelliJ oder Eclipse
3. Installiere dir ein JDK mindestens in der Version 8 (ggf. mit einer aktuellen IDE bereits erledigt)
4. Installiere Maven, wenn es noch nicht bei deiner Entwicklungsumgebung dabei ist.
5. Klone dir das Git-Repository mit `git clone https://portal.intern.viadee.de/gitlab/viadee/clean-code-kompakt-space-station.git`

### Setup der Raumstation
1. Navigiere mit `cd backend` über die Console in das Backend-Modul
2. Installiere die Abhängigkeiten des Projektes und baue das Projekt mit `mvn clean package`.
3. Starte die Anwendung SpaceStationApplication über deine Entwicklungsumgebung.

### Setup des Cockpits
1. Node.js installieren via [Node.js Website](https://nodejs.org/en/)
2. Navigiere mit `cd frontend` über die Console in das Frontend-Modul
3. Installiere die Abhängigkeiten des Projektes mit `npm i`.
4. Starte die Anwendung mit `npm start`.
5. Öffne den Browser mit der URL [localhost:4200](localhost:4200)

Das Cockpit der Raumstation sollte geladen werden und sekündlich die aktuellen Werte der Raumstationsmodule anzeigen.
Wenn das Cockpit nicht mit dem Backend reden kann, wird ein _Connection lost_-Fehler angezeigt.

## Hinweise zur Bearbeitung
- Du kannst gerne das Remote-Repository nutzen um deinen Arbeitstand zu sichern.
Achte aber bitte darauf auf keinem bestehenden Branch zu commiten, sondern leg dir deinen eignen Branch an.
- Wir löschen regelmäßig alle Branches der Übungsteilnehmer:innen, um das Repository kompakt zu halten.
Solltest du deinen Arbeitsstand längerfristig behalten wollen, ziehe dir bitte einen Fork.
- Ihr bekommt von uns Hinweise, wie ihr euch in den Code am besten einarbeiten könnt.

Wir hoffen die Übungen machen euch Spaß und wünschen euch viel Erfolg beim Retten der Raumstation!

Viele Grüße

Lena Groß, Björn Meschede, Christoph Meyer und Michael Landreh 
