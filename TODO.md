# TODO (Descending priority)
* Front-end website (Tarik, Josh)
- Landing page, login form, "take me to public shit idk (skip auth)"
- sign-up page
- graph page: recent, (query search, full-text search, #hops): dropdown, popular tags.

* Database (Josh)
* API for calling the database
* Signing/Authentication (Tarik (?))
* Concurrency for DB (Josh)


Example of data on meta-data
[displayed-name]<[]-[link-name]
[displayed-name]<[]>[link-name]
[displayed-name]-[]>[link-name]

Dijkstras
[dijkstras]-[operates-on]>[graphs]
[dijkstras]<[variation]>[bfs]

## Schema


## API things
* Add node / Add label
* Add relations
* Add field / Set property
* Upload file to property
* Create delete request / Hide 

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

