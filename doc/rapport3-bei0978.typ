/* Global variables */

#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  title: "Rapport individuel itération 3 - Bilèle EL HADDADI"
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


= Rapport individuel 3 - Bilèle EL HADDADI, Groupe CD3

#v(.5cm)

== Conception MVC

Séparation de l'interface et du controleur.

== ChargeurMap

Implémentation de `ChargeurMap` qui lit un fichier pour crée une map. Lecture
des valeurs initiales de la map et des obstacles déjà présents.

== Affichage des acteurs

Toutes les frames, la map appel `afficher(Graphics g)` pour tous les acteurs
présents. Les acteurs gèrent eux même leur affichage.

Les affichages d'obstacles ont été faits.

== Amélioration et génération javadoc

Le javadoc à été complété et généré.

== Obstales

Les obstacles possèdent maintenat une direction. L'utilisateur peut rester
appuyer et lacher le clique pour donner une direction à tous les obstacles.
Utile pour les obstacles : Propulseur et Téléporteur.

Correction et amélioration des classes `Propulseur` et `Teleporteur`.

Ajout de variantes pour les obstacles :
- Aimant faible et aimant puissant.

- Teleporteur long et court.

== Rapport et manuel utilisateur

Ecriture du rapport et du manuel utilisateur.
