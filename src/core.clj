(ns core
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
  
(defn add-elem [conn name]
  (client/create-node! conn {
    :ref-id "n"
    :labels [:Element]
    :props {:name name}
  }))

(defn add-elems [conn names]
  (doseq [name names]
    (add-elem conn name)))



(defn add-curr-edge [conn from to]
  (client/create-rel! conn {
    :ref-id "r"
    :type "Curriculum"
    :from {:labels [:Course] :props {:name from}}
    :to {:labels [:Element] :props {:name to}}
  }))

(defn add-curr-nodes [conn course names]
  (doseq [name names]
    (add-elem conn name))
  (doseq [name names]
    (add-curr-edge conn course name)))

;; Macro should add transaction to all calls
;;(transaction conn
;;  (client/create-node! {:ref-id "p" :labels [:person] :props {:first-name "Neo"}})
;;  (client/create-node! {:ref-id "p" :labels [:person] :props {:first-name "Morpheus"}}))
;; =>
;; (client/with-transaction conn transac
;;  (client/create-node! trasac {:ref-id "p" :labels [:person] :props {:first-name "Neo"}})
;;  (client/create-node! transac {:ref-id "p" :labels [:person] :props {:first-name "Morpheus"}}))

(defn insertSecond [sexpr x]
  ; insert at second position
  (cons (first sexpr) (cons x (rest sexpr))))

(defmacro transaction [conn & body]
  ; Add client/with-transaction to body
  (let [x (gensym)]
    `(client/with-transaction ~conn ~x
      ; insert x as first argument to all body calls
      ~@(map #(insertSecond % x) body))))
      
  


(defn -main [& args]
  (let [args (parse-opts args cli-options)]
    (let [opts (get args :options)]
      (check-if-help opts)
      (assert-login opts)
      (let [connection (connect opts)]
        ;; Somehow get exception happening inside of transaction
        (add-curr-nodes connection "TestCourse" ["TestElem1" "TestElem2"])
        
        
        (client/disconnect connection)))))
    
