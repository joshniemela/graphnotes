(ns core
  (:require [asami.core :as d]))
  ;(:gen-class)) might be needed for java


(def users [{:user/username "admin"
             :user/password "bcrypt+sha512$eb7f717717f66d3b535c1fc3875d1bde$12$9a494e70cefbddab76f8cf6d0842d2fd6d1fa9142bdcc8d3"}])



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


(def db-uri "asami:mem://graphnotes")
(defn -main []
  ; Create stateful connection to database
  (def conn (d/connect db-uri))
  ; Add users
  (d/transact conn users)
  ; Get password for user every second
  (let [data (d/export-str conn)]
    ; Save as file
    (spit "data.edn" data))
    ; exit
    (System/exit 0))