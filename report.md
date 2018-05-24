---
title: Laboratoire n°4 Traffic
lang: fr
author:
- Fleury Malik <malik.fleury@he-arc.ch>
- Bulloni Lucas <lucas.bulloni@he-arc.ch>
date: \today
pagesize: A4
toc: true
toc-depth: 5
numbersections: true
---

# Laboratoire n° 4 - Gestion de Traffic

## Introduction

## Implémentation

Les routes comportent toute un thread. Ce thread va s'occuper de réveiller tous les threads des voitures (CarMover) qui sont bloqués sur la condition du feu dans la direction de venue.
Il faut bien faire attention aux méthodes "run" et "go" de notre classe "Road". En effet, la méthode "run" est lancée par le thread qui s'occupe de la gestion de feux des routes ainsi que la gestion de (thread lancé toutes les 3 secondes). Tandis que la méthode "go", elle est executée par un des threads des voitures (CarMover).


Concernant l'implémentation aléatoire des chemins, nous avons utilisé les routes connectées afin de savoir depuis quelle route arrive le véhicule et de pouvoir déterminer la suite de son chemin. De plus, le choix du chemin se fait aléatoirement si on se trouve dans le cas d'une intersection (T ou +).

## Tests

## Conclusion
