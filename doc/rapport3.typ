/* Global variables */

#let authors = "Abid Mohamed, El Amrany Anas, El Haddadi Bilele, El Hauat Chada, El Qabbab Oussama, Hosayn Mohamed Anis, Kibou Yassine, Masbah Louay, Samari Yassine"
#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  author: authors,
  title: "Rapport 3 Projet Long CD03",
)

#set text(
  lang: "fr",
  size: text_size,
  fill: rgb("#212427"),
  fallback: false,
  font: "New Computer Modern",
)

#set page(
  paper: "a4",
  margin: (x: 2.5cm, y: 3cm),
  header: block(width: 100%)[#align(right)[#text(size: 9pt, "Groupe CD3")]],
  footer: context [
    #align(right)[
      #counter(page).display("1") / #counter(page).final().at(0)
    ]
  ],
)

#set par(
  first-line-indent: (amount: 2em, all: true),
  justify: true,
)

#set heading(
  numbering: none,
)

#show heading: set block(below: 1em)

#page(margin: 1.7in, header: "", footer: "")[
  #align(center + horizon)[
    #text(weight: "bold", size: header_size)[Gravity-Bot]

    #v(-0.5cm)

    #text(size: subheader_size, style: "italic")[Rapport 3]

    #v(2cm)

    #text(weight: "semibold", size: 1.2em)[Groupe CD3]

    #v(0.5cm)

    #grid(
      columns: 1,
      gutter: 0.8em,
      authors,
    )
  ]
  #v(2cm)
]

#page[
  #outline()
]

= Introduction

== Sujet

Notre projet long porte sur le développement d'un jeu multijoueur d'arcade en
Java et Swing. L'objectif est d'affronter son adversaire sur une map 2D. La map
contient des obstacles, un satellite et un objectif à atteindre. Grâce à son
budget, le joueur peut placer des obstacles pour modifier la trajectoire du
satellite. Le gagnant est le dernier qui obtient une trajectoire corrects. Les
obstacles de l'adversaires sont conservées lors du tour.

== Fonctionnalités

- En tant que joueur, je peux choisir la map sur laquelle je vais affronter mon adversaire.
- En tant que joueur, je peux placer des obstacles sur la Map 2D affiché sur mon écran.

= Paquetages
