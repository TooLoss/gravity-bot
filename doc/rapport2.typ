
/* Global variables */

#let authors = "Abid Mohamed, El Amrany Anas, El Haddadi Bilele, El Hauat Chada, El Qabbab Oussama, Hosayn Mohamed Anis, Kibou Yassine, Masbah Louay, Samari Yassine"
#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  author: authors,
  title: "Sujets projet long - CD3",
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

    #text(size: subheader_size, style: "italic")[Rapport Itération 2]

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

= Boucle de jeu

Nous avons implémenté la boucle de jeu. Chaque tour, un joueur peut jouer et
placer des obstacles. Au tour suivant, c'est à l'autre joueur de les placer.

L'interface affiche le numéro de tour, le nom du joueur, le budget. Le joueur
peut placer des obstacles et le cout sera déduit de son budget. Le budget est un
attribu de la classe `Joueur`. Le prix de l'obstacle est un attribu de la
classe `Obstacle`.

La boucle de jeu fonctionne à l'aide de 2 classes.

*Game* : Contient la logique de tour. Il compte le nombre de tour, lance la
simulation du tour et vérifie si le joueur à gagner. Dans le cas contraire, il
change le joueurCourant. \
Game ne gère pas l'affichage. C'est une classe du package interface
(`FenetreJeu`) qui lit à chaque frame les données de Game. \
Il est pas encore possible de gagner, le jeu se termine sans recommencer.

*Map* : Contient les informations situé dans la map. Les obstacles (dans un
HashSet), la position initiale et le vitesse initiale du projectile, la position
de la cible. Lorsque `Game` lance la simulation, le projectile retourne à son
état initiale. \
A chaque frame, la map vérifie que le projectile collisionne avec un obstacle,
si c'est le cas, il exectue `eventAction` de l'obstacle. L'obstacle peut
intéragir avec le projectile avec la poignée de ce dernier. \
La map vérifie aussi à chaque instant que le projectile ne sort pas des limites
de la map ou si il a pas atteind la cible. Si c'est le cas, il arrête la
simulation et return `true` ou `false` si le tour est gagnant ou perdant. \
La map est un `JPanel` et affiche des carrés aux positions du projectile, des
obstacles et de la cible. C'est une mesure temporaire pour visualiser le jeu car
les `Acteur` ne gère pas encore leur propre affichage. \
Pour l'instant, la map est initialisé avec des paramètres par défaut. A terme,
il faudrait qu'elle soit construit à l'aide d'un fichier et place les obstacles
déjà présent sur la map. \

= Placement des obstacles

= UI du menu principal

