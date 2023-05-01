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

