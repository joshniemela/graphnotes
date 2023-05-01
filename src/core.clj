(ns core
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [org.httpkit.server :refer [run-server]]
            [neo4clj.client :as client]
            [cli :refer [show-help assert-login get-opts]])
  (:gen-class))





(defn connect-db [opts]
  (client/connect 
    (:address opts)
    (:username opts) 
    (:password opts) 
    ;{:encryption :required} TODO: cannot be used with bolt connection, fix?
    ))

(defn -main [& args]
  (let [opts (get-opts args)]
    (show-help opts)
    (assert-login opts)
    (let [conn (connect-db opts)]
      (client/create-node! conn {:labels [:course] :props {:name "test2131"}}))))

        
;(defn close-database [conn path]
;  "Gracefully close the database, and save it to disk"
;  (println "\nClosing database...")
;  (Thread/sleep 1000)
;  (let [data (d/export-str conn)
;        name (str path "/asami.db")]
;    (io/make-parents name)
;    (spit (str name) data))
;  (println "Saved database to" path))

;(defn -main []  
;  (def db (start-database data-path db-uri))
;  (def conn (d/connect db-uri))
;  ; Add users
;  (println (get-user-pass "admin" conn))
;  ; Get password for user every second
;  (d/transact conn users)
;
;  (.addShutdownHook 
;    (Runtime/getRuntime) 
;    (Thread. 
;    (fn [] (close-database conn data-path))))
;)
