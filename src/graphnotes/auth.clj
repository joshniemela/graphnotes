(ns auth
  (:require [buddy.hashers :as hs])
  (:require [asami.core :as d]))


(defn get-user-pass [username conn]
  "Get the password for a user"
  ; Check if user exists and return password if it exists
  (->> (d/q `[:find ?pass
          :where
          [?u :user/username ?user]
          [(= ?user ~username)]
          [?u :user/password ?pass]] (d/db conn))

          ; Query returns a list of lists which should only
          ; contain one password, so we take the first one of the first list
          first 
          first))

(def users [{:user/username "admin"
             :user/password "bcrypt+sha512$eb7f717717f66d3b535c1fc3875d1bde$12$9a494e70cefbddab76f8cf6d0842d2fd6d1fa9142bdcc8d3"}])

(def db-uri "asami:mem://graphnotes")
(d/create-database db-uri)
(def conn (d/connect db-uri))

@(d/transact conn {:tx-data users})



(defn authenticate [username password conn]
  "Return true if the user exists and the password is correct"
  ; User contains the username and password hash
  (let [hashed-password (get-user-pass username conn)]
    (hs/check password hashed-password)))
  