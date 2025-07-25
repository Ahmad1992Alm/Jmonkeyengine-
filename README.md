# Mein IndoorGML-Projekt

Dieses Repository enthaelt Beispielklassen zur Visualisierung von IndoorGML-Dateien mit der JMonkeyEngine.
Die Klassen konvertieren uebergebene CellSpaces, Transitions und States in entsprechende Meshes bzw. Geometrien.

## Beispielanwendungen

* **IndoorGMLExample** – zeigt einfache CellSpaces in einer Ebene.
* **ElevatedCellSpacesExample** – verteilt CellSpaces auf unterschiedlichen Hoehen, sodass eine echte 3D-Ansicht entsteht. Die Navigation erfolgt ueber die JME-FlyCam.
* **ChaseCameraExample** – demonstriert eine Orbit-Steuerung mit der JME-ChaseCamera, um das 3D-Modell per Maus zu drehen und zu zoomen.
* **TwoCellSpacesExample** – baut zwei CellSpaces aus Polygonen auf, berechnet deren Schwerpunkte und verbindet sie mit einer Transition. Die Szene wird automatisch skaliert und zentriert. Jeder CellSpace besitzt eine eigene Farbe und wird halbtransparent dargestellt. Die Navigation erfolgt ueber eine ChaseCamera.
* **MultiCellSpacesExample** – visualisiert eine beliebige Anzahl von CellSpaces, berechnet zu jedem den Schwerpunkt und verbindet die States ringfoermig. Vor der Darstellung wird die Szene skaliert und zentriert. Die CellSpaces erhalten abwechselnde Farben und sind halbtransparent. Die Navigation erfolgt ueber eine ChaseCamera.
* **ModelInteractionExample** – nutzt das Datenmodell, erzeugt automatisch mehrere CellSpaces, States und Transitions. Objekte lassen sich per Maus auswählen, werden dabei hervorgehoben und können mit der Entf-Taste entfernt werden.
* **CreateModel** – Hilfsklasse, die ein kleines Beispielmodell erzeugt und die Geometrie vor dem Anzeigen skaliert sowie zentriert.

## Datenmodell

Die Klasse `IndoorGMLModel` verwaltet CellSpaces, States und Transitions jeweils in eigenen Listen.
Jeder CellSpace und jede Geometrie besitzen eine eindeutige ID. Beim Hinzufügen eines
CellSpace wird automatisch ein passender State erstellt und mitverwaltet. Beim Entfernen
eines CellSpace oder State löscht das Modell auch alle zugehörigen Transitions.
