# Mein IndoorGML-Projekt

Dieses Repository enthaelt Beispielklassen zur Visualisierung von IndoorGML-Dateien mit der JMonkeyEngine.
Die Klassen konvertieren uebergebene CellSpaces, Transitions und States in entsprechende Meshes bzw. Geometrien.

## Beispielanwendungen

* **IndoorGMLExample** – zeigt einfache CellSpaces in einer Ebene.
* **ElevatedCellSpacesExample** – verteilt CellSpaces auf unterschiedlichen Hoehen, sodass eine echte 3D-Ansicht entsteht. Die Navigation erfolgt ueber die JME-FlyCam.
* **ChaseCameraExample** – demonstriert eine Orbit-Steuerung mit der JME-ChaseCamera, um das 3D-Modell per Maus zu drehen und zu zoomen.
* **TwoCellSpacesExample** – baut zwei CellSpaces aus Polygonen auf, berechnet deren Schwerpunkte und verbindet sie mit einer Transition. Die Szene wird automatisch skaliert und zentriert. Die Navigation erfolgt ueber eine ChaseCamera.
