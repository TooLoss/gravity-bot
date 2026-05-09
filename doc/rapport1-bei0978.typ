/* Global variables */

#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  title: "Rapport individuel Bilèle EL HADDADI"
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
  first-line-indent: (amount: 2em, all: true),
  justify: true,
)

#set heading(
  numbering: none,
)

#show heading : set block(below: 1em, )


= Rapport individuel - Bilèle EL HADDADI, Groupe CD3

*Développement du noyau moteur (Terminé)*

Implémentation de l'architecture de base via la classe Acteur et ses dérivées (Obstacle, Projectile). Cette structure gère la physique du satellite ainsi que les interactions dynamiques avec les éléments du plateau de jeu.

*Système de gestion des collisions (Terminé)*

Spécification et finalisation de l'interface de collision. Le système supporte désormais différentes primitives de détection, notamment les collisions de type sphérique et rectangulaire, permettant de définir précisément les zones de blocage entre acteurs.

*Gestionnaire de jeu et boucle principale (En cours)*

Développement des classes Map et Game. Cette étape concerne la mise en place de la game loop pour assurer l'actualisation fluide du projectile à chaque frame et la gestion globale de l'environnement de jeu.
