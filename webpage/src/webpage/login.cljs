(ns webpage.login
  (:require
   [re-frame.core :as re-frame]
   [webpage.subs :as subs]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div {:class "signup-wrapper"}
     [:h1 "Gratam graph notas! ex" @name]
     [:form]
     ]))
