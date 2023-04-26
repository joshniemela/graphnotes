# TODO

## Which DB?
Neo4j (currently using) : has potential issues with permissions
SurrealDB
ArrangoDB
DGraph
Asami (db in clojure)

## Schema


## API things
* Add node / Add label
* Add relations
* Add field / Set property
* Upload file to property
* Create delete request / Hide 

## CLI / TUI(?)

### STEP BY STEP TUI
#### Add node
    * Prompt name (full-text search)
    * Prompt label(s) 
    * Prompt properties / Add note reference
    * Prompt relation(s)(?)

#### Add relation(s)
    * Prompt from => to
    * Type
    * label(s?) if neccesary

#### Delete node
* Prompt name (DETACH DELETE or mark for deletion)

#### Delete relation
* Prompt from to

#### Modify node
* prompt name
* prompt property to modify or labels
* prompt new data

#### Modify relation
* prompt from to
* prompt property to modify or labels
* prompt new data

