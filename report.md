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


Il faut bien faire attention aux méthodes "run" et "go" de notre classe "Road". En effet, la méthode "run" s'occupe d'indiquer à tous les véhicules (condition, signalAll) que les feux d'une routes ont changé (changement de couleur des feux toutes les 3 secondes par un nouveau thread, à l'aide d'un "executor scheduleAtFixedRate"). Tandis que la méthode "go", elle est executée par un des thread d'une des voitures (CarMover) et elle s'occupe de savoir si le véhicule peut continuer son chemin car le feu est vert ou alors entrer dans la file d'attente de l'intersection car le feu est rouge. Lorsque la voiture est en file, le thread qui représente cette voiture est mis en pause et sera réveillé 3 secondes plus tard pour savoir si elle peut denouveau continuer son chemin ou non.

La classe "CircularBuffer" a pour but de changer la couleur du prochain feu dans une intersection. Le changement de feu se fait dans le sens horaire d'une montre. Toutes les 3 secondes, un seul feu est mis au vert pour chaque intersection.

### Synchronisation des voitures dans une intersection

Afin de synchroniser l'arrivée des voitures correctement à une intersection, nous avons fait une liste de route connectées à l'intersection. Ça permet également de ne pas faire de synchronisation lorsqu'il y a moins de 3 routes connectées, car il n'y aura pas de feu rouge. Cette liste permetégalement de savoir quel feu de signalisation est lié à quelle route. L'index de la liste des feux est le même que l'index de la liste des routes connectées.

Ensuite quand une voiture arrive à l'intersection, elle va vérifier que le soit vert. S'il est vert la voiture va passer et s'il est rouge, elle va entrée dans une file d'attente propre à chaque feu. Les intersections ont donc une file d'attente par feu, comme ce le serait en réalité.


Au niveau de la programmation, lorsqu'une voiture arrive à une intersection avec la fonction `go` de `Road`, il faut qu'elle passe en paramètre la route d'ou elle provient et elle même afin d'entrer dans la file d'attente.

```java
public void go(Road from, CarMover mover)
```

Chaque `CarMover` est un Thread et représente une voiture. La synchronisation multithread est fait avec un `ReentrantLock` et une `Condition`. Lorsque le feu est rouge les `CarMover` vont attendre avec un `Condition.wait` et seront signalé lorsque le feu change d'état dans l'objet `CircularBuffer` de chaque route


### Parcours des voitures sur la map et augmentation du nombre de thread maximum

Concernant l'implémentation aléatoire des chemins, nous avons utilisé les routes connectées afin de savoir depuis quelle route arrive le véhicule et de pouvoir déterminer la suite de son chemin. De plus, le choix du chemin se fait aléatoirement si on se trouve dans le cas d'une intersection (T ou +). Le chemin est généré avant le lancement du véhicules. Par conséquent, les véhicules connaissent déjà le trajet complet avant de commencer leur parcours.

## Tests

## Conclusion
