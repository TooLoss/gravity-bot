
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

  Le jeu se déroule au tour par tour sur un plateau ne contenant, au départ,
  qu'un satellite doté d'une vitesse initiale. Le joueur doit modifier la
  trajectoire de ce dernier en plaçant divers éléments pour le guider vers
  l'objectif, tout en évitant les obstacles pour prévenir toute collision.

  Dans un contexte de guerre industrielle, le joueur affronte son principal
  rival issu d'une société concurrente. Tous deux cherchent à résoudre la
  même énigme, et le premier à trouver la solution remporte le contrat.

  Les contrats deviennent de plus en plus complexes et intègrent toujours
  plus de contraintes. La gestion des ressources devient alors cruciale.
]

#page[
  = Description des fonctionnalités

  - *Physique des trajectoires* : Le mouvement du satellite est régi par des lois physiques réelles. Chaque élément placé sur le plateau génère une force spécifique (gravité, propulsion, déviation) qui modifie dynamiquement sa course.

  - *Système de tour par tour* : Le joueur commence systématiquement la partie. À la fin de chaque tour, une simulation est lancée pour vérifier si les conditions du contrat sont remplies. Comme les éléments du joueur et de l'IA sont placés sur le même plateau, il est possible d'interagir avec les objets adverses pour gêner la progression du rival. Toutefois, il y a un temps limité pour valider le contrat.

  - *Différents modes de jeux* : Le mode Arcade propose des contrats générés aléatoirement pour renouveler le défi. Le mode Éditeur permet au joueur de concevoir ses propres contrats et de les tester dans les mêmes conditions que le mode Arcade.

  - *Placement des éléments* : Un élément est un objet interactif permettant de modifier la trajectoire du satellite. À chaque tour, le budget du joueur augmente, lui offrant la possibilité de placer de nouveaux composants ou d'en détruire pour ajuster sa stratégie.

  - *Editeur de niveaux* : Un mode création permet au joueur de personnaliser son propre contrat (disposition des obstacles, budget, objectifs) avant de tenter de le résoudre.

  - *Difficulté de l'IA* : L'intelligence artificielle propose trois niveaux de difficulté (la Start-up, la PME et le Conglomérat). Le niveau Conglomérat bénéficie d'une précision de calcul maximale à chaque tour et adopte un comportement agressif pour entraver le joueur. Ces propriétés (précision et agressivité) sont réduites pour les niveaux inférieurs. L'IA cherche aussi à s'adapter à la stratégie existante du joueur.

  - *Equilibrage* : Les prix des éléments et de leur destruction sont variables afin d'éviter les situations de blocage permanent. Ce système financier incite les joueurs à optimiser leurs choix et empêche les égalités prolongées qui pourraient nuire au rythme de la partie.

  - *Paramètres du jeu* : Un menu de configuration permettra d'ajuster les options d'accessibilité. Ces réglages visent à adapter l'interface et les contrôles pour garantir une expérience fluide à tous les utilisateurs.

  - *Contre-mesure à l'anti-jeu* : Comme le joueur est obligé de placer un élément à chaque tour, des pénalités budgétaires importantes seront appliquées si l'objet posé n'influence pas la trajectoire du satellite. Cette règle empêche de passer son tour artificiellement pour accumuler de l'argent sans participer à la résolution du contrat.

  - *Prévention des blocages* : Si un élément placé dégrade de manière critique la trajectoire du satellite (ex: collision immédiate provoquée volontairement), il sera automatiquement supprimé. Cette règle permet d'empêcher les blocages abusifs et garantit que chaque tour contribue à la résolution de l'énigme.
]

#page[
  = Interface utilisateur

  Le style visuel repose sur des illustrations. 
  
  L'interface d'accueil propose trois options principales : Arcade, Éditeur et
  Paramètres. Cette structure simple et efficace permet au joueur d'accéder
  rapidement au mode de jeu souhaité ou aux réglages d'accessibilité.

  Les contrôles se font à la souris majoritairement.

  Le plateau central occupe la majeure partie de l'écran pour une visibilité
  totale. On y retrouve une barre latérale pour choisir ses éléments, ainsi
  qu'un bandeau en haut pour suivre son budget et le tour actuel.

  Au départ, le satellite possède une direction et une vitesse initiale fixes.
  Chaque élément placé sur le plateau possède un champ de force spécifique qui
  illustre visuellement comment la trajectoire va être déformée. Ce système
  permet d'anticiper l'influence des objets sur le mouvement avant de lancer la
  simulation.
]

#page[
  = Cas d'usage

  == Cas 1 : Tentative d'accumulation passive

  Un joueur décide de ne pas modifier la trajectoire pour économiser son budget
  et attendre que l'IA débloque la situation. Il place un élément dans un coin
  inutile du plateau. Le système détecte que cet objet n'exerce aucune force
  sur le satellite : une pénalité budgétaire est immédiatement appliquée. Le
  joueur comprend qu'il doit investir ses ressources de manière utile pour
  progresser sans perdre d'argent.

  == Cas 2 : Blocage abusif par l'IA ou le joueur

  Le joueur tente de placer un obstacle massif directement sur le satellite
  pour provoquer une collision immédiate et figer la partie. La règle de
  prévention des blocages s'active : l'élément est jugé "critique" pour la
  survie du satellite et est automatiquement supprimé. Le joueur perd son tour
  et son argent.
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
