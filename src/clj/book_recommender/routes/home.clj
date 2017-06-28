(ns book-recommender.routes.home
  (:require [book-recommender.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [book-recommender.db.db :as db]
            [book-recommender.validator.validator :as validator]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn login-page
  ([] (layout/render "login.html"))
  ([error-message]
   (layout/render
     "login.html"
     {:error-message error-message})))

(defn register-page
  ([]
  (layout/render "register.html"))
  ([error-message]
    (layout/render "register.html" {:error-message error-message})))

(defn dashboard-page [user]
  (layout/render "dashboard.html" {:logged-in-user user}))

(defn handle-login [username password]
  (if (not= 0 (count (validator/validate-login-form username password)))
    (login-page (validator/validate-login-form username password))
    (let [user (db/search-for-user username)]
    (if (empty? user)
      (login-page "There is no user with provided username!")
      (if (not= (get-in user [:password]) password)
        (login-page "Given password doesn't match provided user!")
        (dashboard-page user))))))

(defn successfull-register [username password firstname lastname]
  (db/insert-user username password firstname lastname)
  (dashboard-page {:username username :password password :firstname firstname :lastname lastname}))

(defn handle-register [firstname lastname username password repeatpwd]
  (if (not= 0 (count (validator/validate-register-form username password firstname lastname repeatpwd)))
    (register-page (validator/validate-register-form username password firstname lastname repeatpwd))
    (let [user (db/search-for-user username)]
    (if (empty? user)
      (successfull-register username password firstname lastname)
      (register-page "User with provided username already exists. Try with different username.")))))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/login" [] (login-page))
  (GET "/register" [] (register-page))
  (GET "/dashboard" [user] (dashboard-page user))
  (POST "/login" [username password]
    (handle-login username password))
  (POST "/register" [firstname lastname username password repeatpwd]
    (handle-register firstname lastname username password repeatpwd)))

