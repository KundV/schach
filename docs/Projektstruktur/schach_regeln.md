!!!warning Entwurf<br>
Diese Konzepte sind noch nicht final und können sich ändern
!!!

## REGELEN

DOWN UNDER RELGEN

Das Projekt besteht aus 2 Modulen:

| Name        | Funktion                                |
|-------------|-----------------------------------------|
| chess.core  | Logik des Schachspiels                  |
| chess.ui    | Spielanwendung in Swing geschrieben     |

Die Module haben folgende Abhängigkeiten:

```mermaid
%%{init: { 'theme': 'forest' } }%%
graph TD
    chess.ui --> chess.core
```

Wie man sieht, hat das Logik-Modul **keine** Referenz auf die UI-Module (gegenseitige Referenzen währen auch nicht
möglich), denn sie wird strikt von der UI getrennt. Es soll dadurch auch von einer GUI-losen Serveranwendung
konsumierbar sein.

Als Buildsystem wird IntelliJ IDEA verwendet

[!ref Artikel über Buildsysteme](https://blog.jetbrains.com/upsource/2015/09/09/mysterious-build-system-setting/)

