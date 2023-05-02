(ns graphnotes.db
  (:require [neo4clj.client :as client]))

(defn connect-db [opts]
  (client/connect 
    (:address opts)
    (:username opts) 
    (:password opts) 
    ;{:encryption :required} TODO: cannot be used with bolt connection, fix?
    ))

(defn get-all [conn]
  (client/find-nodes conn {:ref-id "n"}))

(defn get-by-label [conn label]
  (client/find-nodes conn {:ref-id "n"
                           :labels [label]}))