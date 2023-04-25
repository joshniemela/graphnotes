# SCHEMA
This contains possible structures for the graph

## Nodes
Nodes can have 1 or more fields, not all nodes neccesarily have to follow the schema rigorously. Fields denoted with (?) are optional.

## Labels
Labels are overarching categories that a node can have, a node can have multiple labels.

### Book
* Name
* Link(?)
* Authors
* Editions(?)
* Date of release(?)
#### Relations
USED_IN, example: CLFS:Book=[Used_in]=>DMFS:Course
SOURCE, example: Dijkstra's Algorithm=[SOURCE{pages:blabla}]=>CLRS

### Course
* Name
* Lecturers
* ID(?), KU courses have an ID
* Block(?)
* Table group [A,B,C]
#### Relations
CURRICULUM, example: DMFS =[Curriculum]=> Dijkstra's algorithm ; SHOULD THIS BE COURSE => TOPIC OR TOPIC => COURSE?
TEACHES, example: Jakob Nordström=[TEACHES]=>DMFS

### Element
generic thing
* Name
* Note(?)





