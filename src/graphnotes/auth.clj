(ns graphnotes.auth
  (:require [buddy.auth]
            [buddy.hashers :as hashers]
            [ring.util.response :refer [response redirect]]))

(def userstore (atom {}))

(defn create-user [user]
  (let [password (:password user)
        username (:username user)]
    (-> user
        (assoc :password-hash (hashers/derive password {:alg :bcrypt+sha512}))
        (dissoc :password)
        (->> (swap! userstore assoc username)))))

(comment (create-user {:username "tikiyeon" :password "1234"})
         (create-user {:username "josh" :password "1234"})
         userstore)

(defn get-user [username]
  (get @userstore username))

(defn get-user-by-username-and-passwod [username password]
  (let [user (get-user username)]
    (when (hashers/check password (:password-hash user))
      user)))

(comment
  (get-user "tikiyeon")
  (get-user-by-username-and-passwod "tikiyeon" "1234"))

(defn post-login [{{username "username" password "password"} :form-params
                   session :session :as req}]
  (if-let [user (get-user-by-username-and-passwod username password)]
    (assoc (redirect "/")
           :session (assoc session :identity (:id user)))
    (redirect "/logout/")))

(defn post-logout [{session :session}]
  (assoc (redirect "/logout/")
         :session (dissoc session :identity)))

(comment
  (post-login {:username "tikiyeon" :password "1234"})
  (post-login {:username "josh" :password "1234"})
  (post-logout {}))

(defn is-authenticated [{user :user :as req}]
  (not (nil? user)))

(defn wrap-user [handler]
  (fn [{username :identity :as req}]
    (handler (assoc req :user (get-user username)))))

(comment
  (is-authenticated {:user "tikiyeon"})
  (is-authenticated {:user "josh"})
  (wrap-user post-login)
  (wrap-user post-logout))