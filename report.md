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

Ces objectifs ont tous été atteints. Nous avons aussi changé le passage des feux de vert à rouge afin d'avoir une meilleure simulation d'un cas réel et externaliser le dessin de la carte pour que ce soit plus en harmonie avec le rôle de chaque classe.

## Implémentation

Le projet a été réalisé en 4 tâches principales, l'externalisation du dessin de la map, la synchronisation des feux de circulation, la synchronisation des voitures dans une intersection et finalement le parcours des voitures sur la map avec l'augmentation du nombre de thread maximum.

### Externalisation du dessin de la map

Le dessin de la carte était initialement dans `CarMover`, et se faisait après qu'une voiture avançait, et donc par chaque thread. Nous avons décidé d'enlever le repaint de cet endroit afin que le dessin de la carte se fasse indépendamment des voitures.

Nous avons donc créé un timer qui appelle `MovableMap.drawingTimer` et qui s'active 60 fois par secondes (Taux de rafraîchissement d'un écran standard). La classe `Timer` utilisée vient du package `java.util` et se lance donc dans un thread séparé, comme cela, lorsque le programme principal est surchargé, le dessin se fera quand même.

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

Il faut bien faire attention aux méthodes `run` et `go` de notre classe `Road`. En effet, la méthode `run` s'occupe d'indiquer à tous les véhicules (condition, signalAll) que les feux d'une route ont changés (changement de couleur des feux toutes les 3 secondes par un nouveau thread, à l'aide d'un "executor scheduleAtFixedRate"). Tandis que la méthode `go` est executée par un des threads d'une des voitures (CarMover) et elle s'occupe de savoir si le véhicule peut continuer son chemin car le feu est vert ou alors entrer dans la file d'attente de l'intersection car le feu est rouge. Lorsque la voiture est en file, le thread qui représente cette voiture est mis en pause et sera réveillé 3 secondes plus tard pour savoir si elle peut de nouveau continuer son chemin ou non.

La classe `CircularBuffer` gère la liste des feux de signalisations d'une `Road` de manière circulaire. Elle permet également le changement de feu qui fait dans le sens horaire d'une montre. Toutes les 3 secondes, un seul feu est mis au vert pour chaque intersection.

### Synchronisation des voitures dans une intersection

Afin de synchroniser l'arrivée des voitures correctement à une intersection, nous avons fait une liste de routes connectées à l'intersection. Ça permet également de ne pas faire de synchronisation lorsqu'il y a moins de 3 routes connectées, car il n'y aura pas de feu rouge. Cette liste permet également de savoir quel feu de signalisation est lié à quelle route. L'index de la liste des feux est le même que l'index de la liste des routes connectées.

Ensuite quand une voiture arrive à l'intersection, elle va vérifier que le feu soit vert. S'il est vert la voiture va passer et s'il est rouge, elle va entrer dans une file d'attente propre à chaque feu. Les intersections ont donc une file d'attente par feu, comme ce le serait en réalité.


Au niveau de la programmation, lorsqu'une voiture arrive à une intersection avec la fonction `go` de `Road`, il faut qu'elle passe en paramètre la route d'où elle provient et elle-même afin d'entrer dans la file d'attente.

```java
public void go(Road from, CarMover mover)
```

Chaque `CarMover` est un Thread et représente une voiture. La synchronisation multithread est faite avec un `ReentrantLock` et une `Condition`. Lorsque le feu est rouge les `CarMover` vont attendre avec un `Condition.wait` et seront signalés lorsque le feu change d'état dans l'objet `CircularBuffer` de chaque route


### Parcours des voitures sur la map et augmentation du nombre de thread maximum

Concernant l'implémentation aléatoire des chemins, nous avons utilisé les routes connectées afin de savoir depuis quelle route arrive le véhicule et de pouvoir déterminer la suite de son chemin. De plus, le choix du chemin se fait aléatoirement si on se trouve dans le cas d'une intersection (T ou +). Le chemin est généré avant le lancement du véhicule. Par conséquent, les véhicules connaissent déjà le trajet complet avant de commencer leur parcours.

## Tests

### Feux

Pour les feux nous avons testé que les feux changent correctement en vérifiant à la fois l'interface graphique et en faisant des `System.out.println()` dans la console.

Nous avons également fait attention que les feux soient bien liés à la bonne route en exécutant le programme et en observant que les voitures s'arrêtaient bien au bon feu et redémarrant bien quand leur feu était vert.

### Voiture à une intersection

Pour la vérification des intersections les tests ont principalement été testé avec la console et après déroulement de l'algorithme.

Les points suivants ont été testés :

- Attente si le feu est rouge
- Attente si la voiture n'est pas la première dans la queue
- Réveil lorsque le feu change ou lorsqu'une voiture
- Aucune attente s'il n'y a pas de feu

Et aucun de ces points pose problème et ont été testé avec succès.

### Chemins aléatoire

Le test du générateur de chemins aléatoire a été fait visuellement. En effet, les seuls gros problèmes qui peuvent subvenir sont les deux cas suivants :

- téléportation d'une voiture sur une route,
- demi-tour sur une route ou une intersection.

Il est donc très facile de voir si notre véhicules suit bien la trajectoire attendue ou si des choses bizarres se passent, telles que les deux cas cités ci-dessus.

## Bugs connus

Lorsqu'on a plus de 20 voitures sur la carte, les voitures arrêtent de fonctionner correctement car le pool de Thread est uniquement prévu pour 20 thread maximum.

## Conclusion

Pour ce laboratoire, nous avons utilisé les outils de concurrence que nous offre Java (les `conditions` ainsi qu'un `executor`). Le programme est fonctionnel, les voitures respectent les différents feux et tout cela sans que le programme entre en interblocage dû à des problèmes de concurrence. Il faut également noter que les véhicules prennent des chemins totalement aléatoires (max de 20 voitures). Par conséquent, nous pensons que le travail réalisé respecte les exigences demandées.
