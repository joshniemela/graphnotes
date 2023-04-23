(ns app.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [neo4clj.client :as client])
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
  (if (:help opts)
    (do
      (println help-message)
      (System/exit 0))))

(defn connect [opts]
  (client/connect 
    (str "bolt://" (:address opts) ":7687") 
    (:username opts) 
    (:password opts) 
    {:encryption :required}))

(defn query-courses
  "Query the Neo4j server for courses"
  [connection]
  (client/find-nodes connection {
    :ref-id "p"
    :labels [:Course]
  }))
    
(defn -main [& args]
  (let [args (parse-opts args cli-options)]
    (let [opts (get args :options)]
      (check-if-help opts)
      (assert-login opts)
      (let [connection (connect opts)]
        (println (try
          (query-courses connection)
          (catch Exception e
            (println "Error: " (.getMessage e))
            (System/exit 1))))
        (client/disconnect connection)))))
    