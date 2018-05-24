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

Pour ce laboratoire, l'objectif était de reprendre un projet existant de simulation de voiture dans une ville. Dans l'état initial du projet, les voitures se déplaçaient d'un point A à un point B sans se soucier des feux de signalisation.

Il fallait donc gérer le parcours d'une voiture sur la carte, en faisant attention à l'état des feux. Idéalement il fallait aussi réaliser un parcours aléatoire.

Ces objectifs ont tous été atteints. Nous avons aussi changé le passage des feux de vert à rouge afin d'avoir un meilleur simulation d'un cas réel et externalisé le dessin de la carte pour que ce soit plus en harmonie avec le rôle de chaque classe.

## Implémentation

Le projet a été réalisé en 4 tâches principales, l'externalisation du dessin de la map, la synchronisation des feux de circulation, la synchronisation des voitures dans une intersection et finalement le parcours des voitures sur la mapavec l'augmentation du nombre de thread maximum.

### Externalisation du dessin de la map

Le dessin de la carte était initialement dans `CarMover`, et ce faisait après chaque fois qu'une voiture avançait, et donc par chaque thread. Nous avons décidé d'enlever le repaint de cet endroit afin que le dessin de la carte se fasse indépendamment des voitures.

Nous avons donc créer un timer qui appele `MovableMap.repaint` 60 fois par secondes (Taux de rafraîchissement d'un écran standard). La classe `Timer` utilisée vient du package `java.util` et se lance donc dans un thread séparé, comme cela, lorsque le programme principal est surchargé, le dessin se fera quand même.

```java
this.drawingTimer = new Timer();

TimerTask task = new TimerTask()
{
  @Override
  public void run()
  {
    repaint();
  }
};

this.drawingTimer.scheduleAtFixedRate(task, 0, DRAWING_DELTA);
```

### Synchronisation des feux de circulation

La classe "CircularBuffer" a pour but de changer la couleur du prochain feu dans une intersection. Le changement de feu se fait dans le sens horaire d'une montre. Toutes les 3 secondes, un seul feu est mis au vert pour chaque intersection.

Il faut bien faire attention aux méthodes "run" et "go" de notre classe "Road". En effet, la méthode "run" s'occupe d'indiquer à tous les véhicules (condition, signalAll) que les feux d'une routes ont changé (changement de couleur des feux toutes les 3 secondes par un nouveau thread, à l'aide d'un "executor scheduleAtFixedRate"). Tandis que la méthode "go", elle est executée par un des thread d'une des voitures (CarMover) et elle s'occupe de savoir si le véhicule peut continuer son chemin car le feu est vert ou alors entrer dans la file d'attente de l'intersection car le feu est rouge. Lorsque la voiture est en file, le thread qui représente cette voiture est mis en pause et sera réveillé 3 secondes plus tard pour savoir si elle peut denouveau continuer son chemin ou non.

### Synchronisation des voitures dans une intersection

### Parcours des voitures sur la map et augmentation du nombre de thread maximum

Concernant l'implémentation aléatoire des chemins, nous avons utilisé les routes connectées afin de savoir depuis quelle route arrive le véhicule et de pouvoir déterminer la suite de son chemin. De plus, le choix du chemin se fait aléatoirement si on se trouve dans le cas d'une intersection (T ou +). Le chemin est généré avant le lancement du véhicules. Par conséquent, les véhicules connaissent déjà le trajet complet avant de commencer leur parcours.

## Conclusion
