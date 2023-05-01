(ns core
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [org.httpkit.server :refer [run-server]]
            [neo4clj.client :as client]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-u" "--username USERNAME" "Username to use"
    :default (System/getenv "GRAPHNOTES_USERNAME")
  ]
   ["-p" "--password PASSWORD" "Password to use"
    :default (System/getenv "GRAPHNOTES_PASSWORD")
   ]
   ["-a" "--address ADDRESS" "Address to use"
    :default (System/getenv "GRAPHNOTES_ADDRESS")
   ]
   ["-h" "--help" "Show help"]])


(def help-message "Usage: graphnotes [options]
Options:
  -u, --username USERNAME    Username to use, defaults to GRAPHNOTES_USERNAME
  -p, --password PASSWORD    Password to use, defaults to GRAPHNOTES_PASSWORD
  -a, --address ADDRESS      Address to use, defaults to GRAPHNOTES_ADDRESS
  -h, --help                 Show help")

(defn assert-login [opts]
  (try
    (assert (and (:username opts) (:password opts) (:address opts)))
    (catch AssertionError e
      (do 
        (println "Missing one or more required arguments:")
        (doseq [opt [:username :password :address]]
        (when-not (get opts opt)
          (println (str "\t" (name opt) " is required, set it as an environment variable or pass it as an argument"))))
        (println help-message)
        (System/exit 1)))))

(defn check-if-help [opts]
  (when (:help opts)
    (do
      (println help-message)
      (System/exit 0))))

(defn connect [opts]
  (client/connect 
    (:address opts)
    (:username opts) 
    (:password opts) 
    {:encryption :required}))

(defn -main [& args]
  (let [args (parse-opts args cli-options)]
    (let [opts (get args :options)]
      (check-if-help opts)
      (assert-login opts)
      (let [conn (client/connect (:address opts) (:username opts) (:password opts))]
        (client/create-node! conn {:labels [:course] :props {:name "test2131"}})))))

        
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
