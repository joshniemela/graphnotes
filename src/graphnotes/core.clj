(ns graphnotes.core
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [org.httpkit.server :refer [run-server]]
            [neo4clj.client :as client]

            [graphnotes.routes :refer [ping-routes crud-routes]]
            [graphnotes.cli :refer [show-help assert-login get-opts]]
            [graphnotes.db :refer :all])
  (:gen-class))


(defn app [db-conn]
  (ring/ring-handler
   (ring/router
    [["/api"
      ping-routes
      (crud-routes db-conn)
      ]]
    {:data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :middleware [parameters/parameters-middleware
                         muuntaja/format-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-request-middleware
                         rrc/coerce-response-middleware]}})))


(defn -main [& args]
  (let [opts (get-opts args)]
    (show-help opts)
    (assert-login opts)
    (println "Starting server on port 8080")
    (let [conn (connect-db opts)] 
      (run-server (app conn) {:port 8080})
      (client/create-node! conn {:labels [:course] :props {:name "test2131"}})
      (println (get-by-label conn :course)))))
