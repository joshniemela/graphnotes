(ns core
  (:require [asami.core :as d])
  (:require [clojure.java.io :as io]))
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


; Use home directory for data
(def data-path (str (System/getProperty "user.home") "/.graphnotes"))


(def db-uri "asami:mem://graphnotes")


(defn start-database
  "Start a database at path and return a connection"
  [path db-uri]

  ; Check if database exists
  (let [conn (d/connect db-uri)
        name (str path "/asami.db")]
    (if (.exists (io/file name))
      ; If it exists, slurp it
      (do (println "Starting existing database at" name)
        @(d/import-data conn (slurp name)))

      (println "Creating new database at" path))

      (d/db conn)))

(defn close-database [conn path]
  "Gracefully close the database, and save it to disk"
  (println "\nClosing database...")
  (Thread/sleep 1000)
  (let [data (d/export-str conn)
        name (str path "/asami.db")]
    (io/make-parents name)
    (spit (str name) data))
  (println "Saved database to" path))

(defn -main []  
  (def db (start-database data-path db-uri))
  (def conn (d/connect db-uri))
  ; Add users
  (println (get-user-pass "admin" conn))
  ; Get password for user every second
  (d/transact conn users)

  (.addShutdownHook 
    (Runtime/getRuntime) 
    (Thread. 
    (fn [] (close-database conn data-path))))
)

