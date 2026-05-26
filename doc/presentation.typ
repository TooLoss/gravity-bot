#import "@preview/diatypst:0.9.3": *

#show: slides.with(
  title: "Projet long - Groupe CD03", // Required
  subtitle: "Présentation",
  authors: ("Groupe CD03"),

  // Optional (for more see docs at https://mdwm.org/diatypst/)
  ratio: 16/9,
  layout: "medium",
  title-color: blue.darken(20%),
  toc: true,
)

#let focussize = 1.3em;
#let textsize = 1.1em;

#set text(
  lang: "fr",
  size: textsize,
  fill: rgb("#212427"),
  fallback: false,
  font: "New Computer Modern",
)

= Présentation technique

== Paquetages

Différents paquetages (MVC)

#text(size: focussize)[
- *core* : modèles.
- *interface* : vue et controlleurs.
- *utils* : classes globales.
]

== Paquetage core

=== UML architecture

#figure(
  image("./uml-core-obstacles.svg", width: 80%)
)

#figure(
  image("./uml-core-map.svg", width: 63%)
)

=== Architecture map

```txt
positionInitiale = 50.0 50.0
budgetInitiale = 1000
vitesseInitiale = 0.0 10.0
ciblePosition = 700.0 500.0

OBSTACLE = MUR 100.0 0.0 200.0 600.0
OBSTACLE = SPHERE 50.0 300.0 15.0 0.0
OBSTACLE = SPHERE 65.0 380.0 8.0 0.0
OBSTACLE = SPHERE 35.0 380.0 8.0 0.0
OBSTACLE = SPHERE 10.0 450.0 15.0 0.0
OBSTACLE = SPHERE 90.0 450.0 15.0 0.0
...
```

```java
public class DonneesMap{
    public Vecteur positionInitiale;
    public Vecteur vitesseInitiale;
    public Vecteur ciblePosition;
    public int budget;
    public List<ObstacleData> obstacles;
}
```

```java
private static final Map<String, ObstacleCreation> registre = Map.of(
  "MUR", data -> new ObstacleMur(data.position, data.fin),
  "SPHERE", data -> new ObstacleCirculaire(position, data.fin.getNorme())
);
```

Évolutivité +

=== Affichage acteurs

Utilisation `Graphics`

```java
public void afficher(Graphics g) { }
```

```java
for (Obstacle obs : obstacles) {
  obs.afficher(g);
}
```

#v(11em)

=== Collisions

```java
public interface Collision {
    boolean estEnCollision(Acteur acteur);
}
```

```java
for (Obstacle obs : obstacles) {
  if (obs.estDansObstacle(this.projectile)) {
    obs.eventAction(this.projectile);
  }
}
```

== Paquetage interface

=== Contrôleurs / Interface

/ *Contrôleur jeu*: #text()[
- Clique hold pour direction
- Lancer la simulation
]

/ *Contrôleur map*: #text()[
- Placement sur grille
]

- *MenuPrincipal*
- *SelectionMap*
- *FenetreJeu* et *ModeEditeur*

=== Enregistrer map

```java
public class AfficherGrille extends JPanel { }
```

- Liste `Obstacles`
- Liste paramètres `Rayons`, `PointDebut`, `PointFin`

*Difficultés*

- Données redondantes
- Évolutivité

```java
// Mur ou Cercle
private ArrayList<Boolean> estUnMur = new ArrayList<>();
```

= Gestion de projet

== Organisation de l'équipe

2 équipes

#text(size: focussize)[
- *Équipe Interface* : Anas, Chada, Yassine K., Louay.
- *Équipe Core* : Mohamed A., Bilele, Oussama, Mohamed Anis, Louay, Yassine S.
]

/ *Point fort*: #text()[
  - Anticipation UML
  - GitHub Project
]

== Outils

#text(size: focussize)[
- GitHub Project + User Stories.
- Priorisation backlog 
- Assignation.
]

*Difficultés*

- Conflits gits
- Visibilité

== Méthode agile

Scrum Master

#text(size: focussize)[
- Rétropective sur sprints → conformité 
- Sprints préparés
]

== Utilisation de l'IA

Noté sur les rapports, transparence.

#text(size: focussize)[
- *Intégration graphique* : 
-- Utilisation de `Graphics` \
-- Design des menues.
]
