Der Ablauf des Schachspiels lässt sich so darstellen:

```mermaid
flowchart TB
    Start --> Z
    Z[Berechne Züge]
    Z --> K{Kann Spieler\nZiehen?}
    K -->|Ja| Warte(Warte auf gültigen Zug)
    K -->|Nein| S{Wird König\nbedroht?}
    S -->|Ja| SM[Schachmatt]
    S -->|Nein| P[Patt]
    Warte --> Z
```

## Zugberechnung

Bei der Zugberechnung gibt es beim Schach unterschiedliche Ebenen der Berechnung

=== 1. Unmittelbare Züge<br>
Zunächst werden alle unmittelbare Züge der Figuren anhand der allgemeinen Zugmuster berechnet.<br>
Hierbei wird folgendes beachtet:

* Felder die von eigenen Figuren besetzt werden
* Felder die hinter einer gegnerischen Figur stehen und somit den Zug der Figur blockieren
* Sonderregeln wie En Passant, Rochade...

=== 2. Vom Gegner gefährdete Figuren<br>
Beim Schach sind Züge ungültig, die zur Bedrohung des eigenen Königs führen.<br>
Somit ist ein Zug ungültig, wenn er zu einem Zustand führt, der diese Eigenschaften erfüllt:<br>

* Der Gegner ist am Zug
* Der Gegner bedroht den König des Spielers
    * Hierbei müssen lediglich die unmittelbaren Züge (Ebene 1) des Gegners geprüft werden

===

```mermaid
flowchart TB
    U(Unmittelbare Züge berechnen)
    U -->|Züge testen| Simulation
    
    subgraph Simulation
        direction TB    
        U2(Unmittelbare Züge berechnen) --> F
        F{König bedroht?} -->|Ja| Ung
        F -->|Nein| Gültig
        Ung(Ungültig)

    end

```