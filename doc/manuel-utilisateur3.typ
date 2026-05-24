/* Global variables */

#let authors = "Abid Mohamed, El Amrany Anas, El Haddadi Bilele, El Hauat Chada, El Qabbab Oussama, Hosayn Mohamed Anis, Kibou Yassine, Masbah Louay, Samari Yassine"
#let text_size = 12pt
#let header_size = 25pt
#let subheader_size = 20pt


#set document(
  author: authors,
  title: "Manuel Utilisateur Projet Long CD03",
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
  first-line-indent: (amount: 0em, all: true),
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

    #text(size: subheader_size, style: "italic")[Manuel utilisateur 3]

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

= Introduction et Objectif du jeu

Gravity-Bot est un jeu d'arcade et de réflexion multijoueur en 2D. Deux joueurs
s'affrontent au tour par tour sur une carte parsemée d'obstacles et d'un
objectif final.

*Le but du jeu* : Modifier la trajectoire d'un satellite (projectile) en plaçant
stratégiquement des obstacles gravitationnels ou physiques sur la carte afin de
l'amener vers l'objectif. Le vainqueur est le dernier joueur qui parvient à
planifier une trajectoire correcte ou à bloquer la progression idéale de son
adversaire.

*Règle d'or* : À chaque nouveau tour, les obstacles posés par votre adversaire
au tour précédent restent sur la carte. Le budget de chaque joueur est limité !

= Lancement de l'application

L'application se lance via son interface graphique Java Swing.

Prérequis :
Java Runtime Environment (JRE) ou JDK installé (Version 11 ou supérieure recommandée).

Commande de lancement depuis le dossier contenant le fichier exécutable :

```java
java -jar Gravity-Bot.jar
```

= Guide de l'interface graphique

== Le Menu Principal
Dès l'ouverture, un menu au design soigné vous propose trois options :

_Jouer_ : Lance le mode confrontation à deux joueurs sur une carte existante.

_Paramètres_ : Modifie le volume du son (soon).

_Crédits_ : Affiche les crédits.

_Éditeur_ : Permet de concevoir vos propres cartes personnalisées.

_Quitter_ : Ferme l'application.

== Le Mode Jeu 

L'écran de jeu se divise en deux zones principales : la Map 2D (zone centrale
de simulation) et le Panneau d'action (sélection des obstacles et suivi du
budget).

Déroulement d'un tour :

1. Sélection de la Map : Choisissez la configuration de terrain de votre choix dans la liste.

2. Phase de placement (Tour du Joueur) :

-- Consultez votre budget restant sur le panneau latéral.

-- Sélectionnez un type d'obstacle (Exemple : Réflecteur ou Zone de gravité).

3. Action à la souris : Cliquez sur la Map 2D à l'endroit désiré, maintenez le
   clic enfoncé pour orienter la direction.

4. Lancement de la simulation : Cliquez sur le bouton "Lancer simulation". Le
   satellite s'élance alors dans l'espace de jeu.

Note technique : Pendant toute la durée de la simulation, l'interface
utilisateur est temporairement verrouillée jusqu'à la résolution complète de la
trajectoire.

== L'Éditeur de Niveau

L'éditeur permet de concevoir des défis sur mesure et de tester directement
l'implémentation physique des différents acteurs du jeu.

La Grille magnétique : Le placement des obstacles s'effectue sur une grille
pour garantir un alignement parfait et propre de tous les éléments physiques.

Sauvegarde : Une fois la map créée, elle est enregistrée automatiquement dans
un format texte brut directement lisible par la classe ChargeurMap, ce qui la
rend disponible pour le mode de jeu principal.

== Description des Obstacles

Les obstacles sont divisés en 2 technologies :

- Legacy Tech : ancienne version moins chère mais moins performante.

- Néon : technologie nouvelle avec des résultats remarquables.

Le choix de la technologie impacte votre budget.

Les obstacles sont les suivants :

- Aimants : repoussent ou attirent le satellite.

- Propulseur : envoie le satellite dans une direction.

- Téléporteur : change la position du satellite.
