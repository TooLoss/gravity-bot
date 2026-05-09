/* Global variables */

#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  title: "Rapport individuel itération 2Bilèle EL HADDADI"
)

#set text(
  lang: "fr",
  size: text_size,
  fill: rgb("#212427"),
  fallback: false,
  font: "Libertinus Serif"
)

#set page(
  paper: "a4",
  margin: (x: 2.5cm, y: 3cm),
  header: block(width: 100%)[#align(right)[#text(size:9pt, "Groupe CD3")]],
  footer: context [
    #align(right)[
      #counter(page).display("1") / #counter(page).final().at(0)
    ]
  ]
)

#set par(
  first-line-indent: (amount: 0em, all: true),
  justify: true,
)

#set heading(
  numbering: none,
)

#show par: set block(inset: (left: 20pt))

#show heading : set block(below: 1em, )


= Rapport individuel 2 - Bilèle EL HADDADI, Groupe CD3

#v(.5cm)

== Développement de la classe utils Vecteur

Création de la classe vecteur et de nombreuses opérations de classe. Vecteur est
utilisé pour désigner un point ou un vecteur. Vecteur à des coordonnées x et y.

== Boucle principale

Finalisation des classes `Map` et `Game`. Map contient les données des
obstacles, des valeurs initiales de la simulation. Game fait jouer les joueurs
un tour par l'autre.

Chaque frame, le projectile de `Map` est update en fonction de deltaT. La
simulation est bloquante (implémentation non bloquante Louay Masbah).

Mise à jour le l'interface `FenetreJeu` avec les données de ces classes.
Affichage de la postion des Acteurs dans `Map` avec des carrés.

Création de la classe `Joueur` qui contient le budget et le nom du joueur.

Ajout de la fonctionnalité budget et de prix des obstacles.

Ajout de la classe cible. Si le projectile l'atteind, c'est la fin de la partie.

Il est possible de jouer entre 2 joueurs humains.

== Placement des obstacles

Les obstacles enregistrés dans la sidebar de `FenetreJeu` peuvent être placés
sur la map avec un click. La `Map` est un `JPanel` et est cliquable.

== Obstacles

Obstacle aimant implémenté.
