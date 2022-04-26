### REGELN

Schach wird nach folgenden Regeln gespielt

| Name     | Funktion                                              |
|----------|-------------------------------------------------------|
| Springer | Kann über andere Figuren springen                     |
|          | 2 horizontal (vor, zurück), 1 vertikal (links,rechts) |
|          | 1 vertikal                                            |

Die Module haben folgende Abhängigkeiten:

```mermaid
%%{init: { 'theme': 'forest' } }%%
graph TD
    chess.ui --> che

Wie man sieht, hat das Logik-Modul  Referenz auf die UI-Module (gegenseitige Referenzen währen auch nicht
möglich), denn sie wird strikt von der UI getrennt. Es soll dadurch auch von einer GUI-losen Serveranwendung
konsumierbar sein.

Als Buildsystem wird IntelliJ IDEA verwendet


