(ns cli
  (:require [clojure.tools.cli :refer [parse-opts]]))


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

(defn show-help [opts]
  (when (:help opts)
    (do
      (println help-message)
      (System/exit 0))))


(defn get-opts [args]
  (get (parse-opts args cli-options) :options))
