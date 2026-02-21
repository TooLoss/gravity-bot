
/* Global variables */

#let authors = "Abid Mohamed, El Amrany Anas, El Haddadi Bilele, El Hauat Chada, El Qabbab Oussama, Hosayn Mohamed Anis, Kibou Yassine, Masbah Louay, Samari Yassine"
#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  author: authors,
  title: "Sujets projet long - CD3"
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
  numbering: "1.",
)

#show heading : set block(below: 1em, )

#page(margin: 1.7in, header: "", footer: "")[
  #align(center + horizon)[
    #text(weight: "bold", size: header_size)[Gravity-Bot]

    #v(-0.5cm)

    #text(size: subheader_size, style: "italic")[Fonctionnalités]

    #v(2cm)

    #text(weight: "semibold", size: 1.2em)[Groupe CD3]

    #v(0.5cm)

    #grid(
      columns: 1,
      gutter: 0.8em,
      authors
    )
  ]
  #v(2cm)
]

#page[
  #outline()
]

#page[
  = Objectif

  Le projet consiste à développer un jeu vidéo de stratégie et d’arcade qui
  récompense la maîtrise de la physique ainsi que l’adaptabilité du joueur.

  Le projet consiste à développer un jeu vidéo de stratégie et d’arcade qui
  récompense la maîtrise de la physique ainsi que l’adaptabilité du joueur.
  Le jeu se déroule au tour par tour sur un plateau ne contenant, au départ,
  qu'un satellite doté d'une vitesse initiale. Le joueur doit modifier la
  trajectoire de ce dernier en plaçant divers éléments pour le guider vers
  l'objectif, tout en évitant les obstacles pour prévenir toute collision.

  Dans un contexte de guerre industrielle, le joueur affronte son principal
  rival issu d'une société concurrente. Tous deux cherchent à résoudre la
  même énigme, et le premier à trouver la solution remporte le contrat. Mais
  attention : les éléments déjà placés sur le plateau ne peuvent pas être
  supprimés facilement.

  Les contrats deviennent de plus en plus complexes et intègrent toujours
  plus de contraintes. La gestion des ressources devient alors cruciale.
]

#page[
  = Description des fonctionnalités

  - *Physique des trajectoires* : Le mouvement du satellite est régi par des lois physiques réelles. Chaque élément placé sur le plateau génère une force spécifique (gravité, propulsion, déviation) qui modifie dynamiquement sa course.

  - *Système de tour par tour* : Le joueur commence systématiquement la partie. À la fin de chaque tour, une simulation est lancée pour vérifier si les conditions du contrat sont remplies. Comme les éléments du joueur et de l'IA sont placés sur le même plateau, il est possible d'interagir avec les objets adverses pour gêner la progression du rival. Toutefois, il y a un temps limité pour valider le contrat.

  - *Placement des éléments* : Un élément est un objet interactif permettant de modifier la trajectoire du satellite. À chaque tour, le budget du joueur augmente, lui offrant la possibilité de placer de nouveaux composants ou d'en détruire pour ajuster sa stratégie.

  - *Editeur de niveaux* : Éditeur de niveaux : Un mode création permet a joueur de personnaliser son propre contrat (disposition des obstacles, budget, objectifs) avant de tenter de le résoudre.

  - *Difficulté de l'IA* : L'intelligence artificielle propose trois niveaux de difficulté (la Start-up, la PME et le Conglomérat). Le niveau Conglomérat bénéficie d'une précision de calcul maximale à chaque tour et adopte un comportement agressif pour entraver le joueur. Ces propriétés (précision et agressivité) sont réduites pour les niveaux inférieurs. L'IA cherche aussi à s'adapter à la stratégie existante du joueur.

  - *Equilibrage* : Les prix des éléments et de leur destruction sont variables afin d'éviter les situations de blocage permanent. Ce système financier incite les joueurs à optimiser leurs choix et empêche les égalités prolongées qui pourraient nuire au rythme de la partie.
]

#page[
  = Interface utilisateur
]

#page[
  = Cas d'usage
]

#page[
  = Annexe

  == Difficulté d'implémentation de l'IA

  Développer un algorithme capable de simuler l'agressivité de l'IA (bloquer le
  joueur) tout en restant dans les règles du jeu demande une attention
  particulière sur la recherche de chemin et la prédiction de collision. Si
  cela est bien implémenté, l'IA s'adaptera naturellement à la stratégie du
  joueur pour proposer un défi constant. Le point dur réside dans la capacité
  de l'IA à anticiper les trajectoires futures après la pose d'un nouvel
  élément.

  == Difficulté de gestion de l'économie et des éléments

  L'équilibrage entre le gain passif d'argent à chaque tour et le coût des
  actions est complexe. Il faut ajuster les prix pour que la destruction d'un
  élément soit une décision tactique lourde et non une action triviale. Le
  risque majeur est de créer une économie trop permissive qui permettrait de
  saturer le plateau, rendant la simulation physique illisible.
]
